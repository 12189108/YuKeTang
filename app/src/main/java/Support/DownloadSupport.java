package Support;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.math.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadSupport
{
	private String downloadurl;
	private String targetpath;
	private Context mContext;
	private long mfilelength;
	private boolean init=false,stop=false;
	private File targetdata;
	private long[] startPosition,endPosition,existThreadId;
	private DownloadListener mDownloadListener;
	private int ThreadNum=4,ThreadStarted=-1;
	private int loadedThread=0;
	private long currrentDownloaded=0,block;
	private RandomAccessFile raf;
	public DownloadSupport(Context mContext, String downurl, String targetpath){
		this.downloadurl=downurl;
		this.targetpath=targetpath;
		this.mContext=mContext;
	}
	public void setDownloadListener(DownloadListener mDownloadListener){
		if(mDownloadListener!=null){
			this.mDownloadListener=mDownloadListener;
		}
	}
	/*
	*注意判断网络状态！
	*注意不要放在主线程运行！！！
	*偶尔网络不稳定可重复调用2-3次
	*/
	public void initTask(){
		new Config().start();
	}
	private void startDownload(){
		if(mDownloadListener!=null)new ProcessLintener().start();
		stop=false;
		for(int i=0;i<existThreadId.length;i++){
			new DownloadThread((int)existThreadId[i]).start();
		}
	}
	public void stopDownload(){
		stop=true;
	}
	private void initConfig(){
		try {
			startPosition=new long[ThreadNum];
			endPosition=new long[ThreadNum];
			File content=new File(targetpath);
			if(!content.getParentFile().exists())content.getParentFile().mkdirs();
			if(!content.exists())content.createNewFile();
			File datadir=mContext.getFilesDir();
			if(!datadir.exists())datadir.mkdirs();
			targetdata = new File(datadir.getAbsolutePath() + "/" + content.getName());
			if(!targetdata.exists())targetdata.mkdir();
			File msg=new File(targetdata.getAbsolutePath()+"/init.data");
			long undownloaded=0;
			if(msg.exists()) {
				File[] files = targetdata.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						if(!name.startsWith("init"))return true;
						return false;
					}
				});
				int len = files.length;
				existThreadId=new long[len];
				for (int i = 0; i < len; i++) {
					File data =files[i];
					JSONObject contentjs = new JSONObject(getMsg(data));
					long ThreadId=Long.parseLong(data.getName().substring(0,data.getName().indexOf(".")));
					existThreadId[i]=ThreadId;
					startPosition[(int)ThreadId]=contentjs.getLong("startPosition");
					endPosition[(int)ThreadId]=contentjs.getLong("endPosition");
					undownloaded+=endPosition[(int)ThreadId]-startPosition[(int)ThreadId];
				}
				ThreadStarted=Integer.parseInt(getMsg(msg));
			}else{
				msg.createNewFile();
				ThreadStarted=3;
				undownloaded=mfilelength;
				WriteMsg(msg,3+"");
				existThreadId=new long[4];
				for(int i=0;i<4;i++){
					existThreadId[i]=i;
					JSONObject data = new JSONObject();
					data.put("startPosition",block*i);
					startPosition[i]=i*block;
					if(i!=ThreadNum-1) {
						data.put("endPosition",block*(i+1)-1);
						endPosition[i]=block*(i+1)-1;
					}
					else {
						data.put("endPosition",mfilelength);
						endPosition[i]=mfilelength;
					}
					WriteMsg(new File(targetdata.getAbsolutePath() + "/" + i + ".data"),data.toString());
				}
			}
			raf=new RandomAccessFile(targetpath,"rwd");
			raf.setLength(mfilelength);
			raf.close();
			init=true;
			currrentDownloaded=mfilelength-undownloaded;
			new ProcessLintener().start();
			startDownload();
		}catch (Throwable e){
			if(mDownloadListener!=null)mDownloadListener.onInitFailed(e);
		}
	}
	private void removeData(){
		try {
			String tmp="rm -rf "+mContext.getFilesDir().getAbsolutePath()+"/"+new File(targetpath).getName();
			Runtime.getRuntime().exec(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String getMsg(File content){
		return new String(ByteTransformSupport.File2Byte(content));
	}
	private void WriteMsg(File target,String content){
		try {
			RandomAccessFile inforaf=new RandomAccessFile(target,"rw");
			inforaf.write(content.getBytes());
			inforaf.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	private void updateProcess(int ThreadID,long NextstartPosition){
		File data = new File(targetdata.getAbsolutePath() + "/" + ThreadID + ".data");
		try {
			JSONObject Threaddata=new JSONObject(getMsg(data));
			Threaddata.put("startPosition",NextstartPosition);
			WriteMsg(data,Threaddata.toString());
		} catch (JSONException e) {

		}
	}
	private class Config extends Thread{

		public Config(){
			super();
			Looper.getMainLooper();
		}
		@Override
		public void run() {
			super.run();
			try {
				URL Url = new URL(downloadurl);
				HttpURLConnection uc = (HttpURLConnection) Url.openConnection();
				uc.setUseCaches(false);
				uc.setInstanceFollowRedirects(true);
				uc.setRequestProperty("Accept-Encoding", "identity");
				uc.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36");
				uc.setRequestMethod("GET");
				uc.connect();
				new SystemServiceSupport(mContext).CopytoSystem(uc.getResponseCode()+"");
				String s=uc.getHeaderFields().toString();
				//new SystemServiceSupport(mContext).CopytoSystem(uc.getHeaderFields().toString());
				mfilelength =Long.parseLong(uc.getHeaderField("Content-Length"));
				if(mDownloadListener!=null)mDownloadListener.onReceiveFileLength(mfilelength);
				uc.disconnect();
				if((int)(mfilelength/(1024*1024))>4)ThreadNum= (int) (mfilelength/(1024*1024))+1;
				block=(long)mfilelength/ThreadNum;
				initConfig();
			}catch (Throwable e){
				if(mDownloadListener!=null)mDownloadListener.onInitFailed(e);
			}
		}
	}
	private class DownloadThread extends Thread{
		private int ThreadID;
		private RandomAccessFile raff;
		private HttpURLConnection uc;
		public DownloadThread(int ThreadId){
			this.ThreadID=ThreadId;
		}
		@Override
		public void run() {
			super.run();
			if(!init){
				stop=true;
				if(mDownloadListener!=null)mDownloadListener.onDownloadFailed(new Throwable("Init Failed or Init not star！"));
				interrupt();
			}
			if(startPosition[ThreadID]>=endPosition[ThreadID]){
				loadedThread-=1;
				new DownloadHandler().sendEmptyMessage(200);
				interrupt();
			}
			else{
				try {
				URL Url = new URL(downloadurl);
				uc = (HttpURLConnection) Url.openConnection();
				uc.setUseCaches(false);
				uc.setInstanceFollowRedirects(true);
				uc.setRequestProperty("Accept-Encoding", "identity");
				uc.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36");
				uc.setRequestProperty("Range", "bytes="+startPosition[ThreadID]+"-"+endPosition[ThreadID]);
				uc.setRequestMethod("GET");
				uc.setConnectTimeout(5000);
				uc.connect();
				raff=new RandomAccessFile(targetpath,"rw");
				raff.seek(startPosition[ThreadID]);
				byte[] buffer=new byte[1024*1024];
				int len=-1;
				long total=0;
				InputStream is = uc.getInputStream();
				while ((len=is.read(buffer))!=-1&&!stop) {
					raff.write(buffer,0,len);
					total+=len;
					currrentDownloaded+=len;
					//将每次更新的数据同步到底层硬盘
					updateProcess(ThreadID,startPosition[ThreadID]+total);
					if(stop){
						loadedThread-=1;
						interrupt();
						raff.close();
						uc.disconnect();
					}
				}
				if(ThreadID+1==ThreadNum&&loadedThread==1){
					removeData();
					currrentDownloaded=mfilelength;
				}
				}
				catch (InterruptedIOException exit){

				}
				catch (Throwable e){
						if (mDownloadListener != null) mDownloadListener.onDownloadFailed(e);
						stop = true;
				}finally {
					loadedThread-=1;
					Message msg = new Message();
					msg.what=200;
					msg.arg1=ThreadID;
					new DownloadHandler().sendMessage(msg);
					interrupt();
					if(uc!=null)uc.disconnect();
					if(raff!=null) {
						try {
							raff.close();
						} catch (IOException e) {
						}
					}
				}
			}
		}
	}
	private class ProcessLintener extends Thread{
		@Override
		public void run() {
			super.run();
			try {
				while(!stop) {
					File[] files = targetdata.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							if(!name.startsWith("init"))return true;
							return false;
						}
					});
					File tmpfile=new File(targetdata.getAbsolutePath()+"/init.data");
					long con=0;
					if(tmpfile.exists())con=Long.parseLong(getMsg(tmpfile));
					else con=mfilelength;
					if (currrentDownloaded>=mfilelength||(files.length==0&&con==ThreadNum-1)) {
						currrentDownloaded=mfilelength;
						removeData();
						stop=true;
					}
					mDownloadListener.onDownload(currrentDownloaded);
					if (stop || currrentDownloaded>=mfilelength)interrupt();
					sleep(250);
				}
			} catch (InterruptedException e) {

			}
		}
	}
	private class DownloadHandler extends Handler{
		public DownloadHandler(){
			super(mContext.getMainLooper());
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 200:
					if(!stop&&loadedThread<=4&&(ThreadStarted+1)<ThreadNum){
						loadedThread+=1;
						ThreadStarted+=1;
						WriteMsg(new File(targetdata.getAbsolutePath()+"/init.data"),ThreadStarted+"");
						try {
							JSONObject data = new JSONObject();
							data.put("startPosition", block * ThreadStarted);
							startPosition[ThreadStarted]=block*ThreadStarted;
							if (ThreadStarted != ThreadNum-1) {
								data.put("endPosition", block * (ThreadStarted) - 1);
								endPosition[ThreadStarted] = block * (ThreadStarted + 1) - 1;
							} else {
								data.put("endPosition", mfilelength);
								endPosition[ThreadStarted] = mfilelength;
							}
							WriteMsg(new File(targetdata.getAbsolutePath() + "/" + ThreadStarted + ".data"),data.toString());
						}catch (Throwable e){ }
						new DownloadThread(ThreadStarted).start();
					}
					new File(targetdata.getAbsolutePath() + "/" + msg.arg1+ ".data").delete();
					break;
			}
		}
	}
	public interface DownloadListener{
		void onReceiveFileLength(long fileLength);
		void onInitFailed(Throwable e);
		void onDownload(long process);
		void onDownloadFailed(Throwable e);
	}
	public static double div(double d1,double d2,int scale){
		if(scale<0)throw new IllegalArgumentException("unknow scale");
		BigDecimal b1=new BigDecimal(d1);
		BigDecimal b2=new BigDecimal(d2);
		return b1.divide(b2,scale,b2.ROUND_HALF_UP).doubleValue();
	}
}
