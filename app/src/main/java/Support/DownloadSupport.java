package Support;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
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
	private String mUser_Agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36",targetpath;
	private Context mContext;
	private long mfilelength;
	private boolean init=false,stop=true;
	private File targetdata;
	private long[] startPosition,endPosition,existThreadId;
	private DownloadListener mDownloadListener;
	private int ThreadNum=4,ThreadStarted=-1;
	private int loadedThread=0;
	//最大同时下载线程数
	private long mMax_Downloading_ThreadNum=4;
	private long currrentDownloaded=0,block=0;
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

	public void initTask(){
		new Config().start();
	}
	public void cancle_download(boolean remove_downloaded_file){
		stop=true;
		removeData();
		if(remove_downloaded_file)new File(targetpath).delete();
	}
	//设置最大下载线程数
	public void setMax_Download_ThreadNum(long MaxThreadNum)throws IllegalArgumentException{
		if(!stop)throw new IllegalArgumentException("Download started, MaxThreadNum cannot be modified.");
		else if(MaxThreadNum>=4)mMax_Downloading_ThreadNum=MaxThreadNum;
		else throw new IllegalArgumentException("MaxThreadNum must be greater than 4.");
	}
	public void setUser_Agent(String User_Agent)throws IllegalArgumentException{
		if(!stop)throw new IllegalArgumentException("Download started, User_Agent cannot be modified.");
		else mUser_Agent=User_Agent;
	}
	public boolean isDownloadStarted(){
		return stop;
	}
	private void startDownload(){
		if(mDownloadListener!=null)new ProcessLintener().start();
		stop=false;
		for (long l : existThreadId) {
			new DownloadThread((int) l).start();
		}
	}
	public void stopDownload(){
		stop=true;
	}
	private void initConfig(){
		try {
			startPosition=new long[ThreadNum];
			endPosition=new long[ThreadNum];
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
				if(len>=mMax_Downloading_ThreadNum)existThreadId=new long[len];
				else existThreadId=new long[(int)mMax_Downloading_ThreadNum];
				for (int i = 0; i < len; i++) {
					File data =files[i];
					JSONObject contentjs = new JSONObject(getMsg(data));
					long ThreadId=Long.parseLong(data.getName().substring(0,data.getName().indexOf(".")));
					existThreadId[i]=ThreadId;
					startPosition[(int)ThreadId]=contentjs.getLong("startPosition")-1;
					endPosition[(int)ThreadId]=contentjs.getLong("endPosition");
					undownloaded+=endPosition[(int)ThreadId]-startPosition[(int)ThreadId];
				}
				if(len<4){
					for(int i=0;i<mMax_Downloading_ThreadNum-len;i++){
						ThreadStarted+=1;
						existThreadId[len+i]=ThreadStarted;
					}
					WriteMsg(msg,ThreadStarted+"");
				}
				ThreadStarted=Integer.parseInt(getMsg(msg));
				if (ThreadStarted!=ThreadNum-1){
					long end_block=mfilelength-(ThreadNum-1)*(block-1);
					undownloaded+=(ThreadNum-ThreadStarted-2)*(block-1)+end_block;
				}
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
				uc.setRequestProperty("User-Agent",mUser_Agent);
				uc.setRequestMethod("GET");
				uc.connect();
				new SystemServiceSupport(mContext).CopytoSystem(uc.getResponseCode()+"");
				String s=uc.getHeaderFields().toString();
				//new SystemServiceSupport(mContext).CopytoSystem(uc.getHeaderFields().toString());
				mfilelength =Long.parseLong(uc.getHeaderField("Content-Length"));
				if(mDownloadListener!=null)mDownloadListener.onReceiveFileLength(mfilelength);
				uc.disconnect();
				if((int)(mfilelength/(1024*1024))>mMax_Downloading_ThreadNum)ThreadNum= (int) (mfilelength/(1024*1024))+1;
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
		private boolean hasSendMessage=false;
		private long mStartPosition=0;
		private HttpURLConnection uc;
		public DownloadThread(int ThreadId){
			this.ThreadID=ThreadId;
			mStartPosition=startPosition[ThreadId];
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
				uc.setRequestProperty("User-Agent",mUser_Agent);
				uc.setRequestProperty("Range", "bytes="+mStartPosition+"-"+endPosition[ThreadID]);
				uc.setRequestMethod("GET");
				uc.setConnectTimeout(5000);
				uc.connect();
				raff=new RandomAccessFile(targetpath,"rw");
				raff.seek(startPosition[ThreadID]);
				byte[] buffer=new byte[1024*1024];
				int len=-1;
				long total=0;
				InputStream is = uc.getInputStream();
				while ((len=is.read(buffer))!=-1&&!hasSendMessage) {
					raff.write(buffer,0,len);
					total+=len;
					currrentDownloaded+=len;
					//将每次更新的数据同步到底层硬盘
					updateProcess(ThreadID,mStartPosition+total);
					if(mStartPosition+total-1>endPosition[ThreadID])Log.e("err","Thread:"+ThreadID+" download out of range,startPosition:"+(mStartPosition+total)+"endPosition:"+endPosition[ThreadID]);
					if(stop){
						loadedThread-=1;
						interrupt();
						raff.close();
						uc.disconnect();
					}
				}
				if(!hasSendMessage){
					Message msg = new Message();
					msg.what=200;
					msg.arg1=ThreadID;
					new DownloadHandler().sendMessage(msg);
					hasSendMessage=true;
					}
				}
				catch (InterruptedIOException exit){

				}
				catch (Throwable e){
						if (mDownloadListener != null) mDownloadListener.onDownloadFailed(e);
						stop = true;
				}finally {
					loadedThread-=1;
					if(!hasSendMessage){
						Message msg = new Message();
						msg.what=200;
						msg.arg1=-1;
						new DownloadHandler().sendMessage(msg);
					}
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
					if (files.length==0&&con==ThreadNum-1) {
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
					if(!stop&&loadedThread<=mMax_Downloading_ThreadNum&&(ThreadStarted+1)<ThreadNum){
						loadedThread+=1;
						ThreadStarted+=1;
						WriteMsg(new File(targetdata.getAbsolutePath()+"/init.data"),ThreadStarted+"");
						try {
							JSONObject data = new JSONObject();
							data.put("startPosition", block * ThreadStarted);
							startPosition[ThreadStarted]=block*ThreadStarted;
							if (ThreadStarted != ThreadNum-1) {
								data.put("endPosition", block * (ThreadStarted+1) - 1);
								endPosition[ThreadStarted] = block * (ThreadStarted + 1) - 1;
							} else {
								data.put("endPosition", mfilelength);
								endPosition[ThreadStarted] = mfilelength;
							}
							WriteMsg(new File(targetdata.getAbsolutePath() + "/" + ThreadStarted + ".data"),data.toString());
						}catch (Throwable e){ }
						new DownloadThread(ThreadStarted).start();
					}
					if(msg.arg1!=-1)new File(targetdata.getAbsolutePath() + "/" + msg.arg1+ ".data").delete();
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
