package Support;
import android.content.Context;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	private long[] startPosition,endPosition;
	private DownloadListener mDownloadListener;
	private int ThreadNum=4;
	private long currrentDownloaded=0;
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
	public void startDownload(){
		if(mDownloadListener!=null)new ProcessLintener().start();
		for(int i=0;i<ThreadNum;i++){
			new DownloadThread(i).start();
		}
	}
	public void stopDownload(){
		stop=true;
	}
	public void initConfig(){
		try {
			startPosition=new long[ThreadNum];
			endPosition=new long[ThreadNum];
			File content=new File(targetpath);
			if(!content.exists())content.createNewFile();
			File datadir=mContext.getFilesDir();
			if(!datadir.exists())datadir.mkdir();
			targetdata = new File(datadir.getAbsolutePath() + "/" + content.getName());
			if(!targetdata.exists())targetdata.mkdir();
			File msg=new File(targetdata.getAbsolutePath()+"/init.data");
			long undownloaded=0;
			if(msg.exists()) {
				for (int i = 0; i < ThreadNum; i++) {
					File data = new File(targetdata.getAbsolutePath() + "/" + i + ".data");
					JSONObject contentjs = new JSONObject(getMsg(data));
					startPosition[i]=contentjs.getLong("startPosition");
					endPosition[i]=contentjs.getLong("endPosition");
					undownloaded+=endPosition[i]-startPosition[i];
				}
			}else{
				msg.createNewFile();
				undownloaded=mfilelength;
				long block=mfilelength/ThreadNum;
				for(int i=0;i<ThreadNum;i++){
					JSONObject data = new JSONObject();
					data.put("startPosition",block*i);
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
			raf=new RandomAccessFile(targetpath,"rw");
			raf.setLength(mfilelength);
			init=true;
			currrentDownloaded=mfilelength-undownloaded;
		}catch (Throwable e){

		}
	}
	private void removeData(){
		try {
			Runtime.getRuntime().exec("rm -rf "+mContext.getFilesDir().getAbsolutePath()+"/"+new File(targetpath).getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String getMsg(File content){
		return MD5Support.getString(mContext,ByteTransformSupport.File2Byte(content));
	}
	private void WriteMsg(File target,String content){
		try {
			RandomAccessFile inforaf=new RandomAccessFile(target,"rwd");
			inforaf.write(MD5Support.toString(mContext,content));
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
			e.printStackTrace();
		}
	}
	private class Config extends Thread{
		@Override
		public void run() {
			super.run();
			try {
				URL Url = new URL(downloadurl);
				HttpURLConnection uc = (HttpURLConnection) Url.openConnection();
				uc.setUseCaches(false);
				uc.setInstanceFollowRedirects(true);
				uc.setRequestProperty("Accept-Encoding", "identity");
				uc.setRequestProperty("Content-Type","application/octet-stream;charset=utf-8");
				uc.setRequestMethod("GET");
				uc.connect();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
					mfilelength = uc.getContentLengthLong();
				}else mfilelength=uc.getContentLength();
				if(mDownloadListener!=null)mDownloadListener.onReceiveFileLength(mfilelength);
				uc.disconnect();
				if((int)(mfilelength/1024/1024)>4)ThreadNum= (int) (mfilelength/1024)+1;
				initConfig();
			}catch (Throwable e){
				if(mDownloadListener!=null)mDownloadListener.onInitFailed(e);
			}
		}
	}
	public  class DownloadThread extends Thread{
		private int ThreadID;
		public DownloadThread(int ThreadId){
			this.ThreadID=ThreadId;
		}
		@Override
		public void run() {
			super.run();
			if(!init){
				stop=true;
				if(mDownloadListener!=null)mDownloadListener.onDownloadFailed(new Throwable("Init Failed or Init not star！"));
				currentThread();
			}
			try {
				URL Url = new URL(downloadurl);
				HttpURLConnection uc = (HttpURLConnection) Url.openConnection();
				uc.setUseCaches(false);
				uc.setInstanceFollowRedirects(true);
				uc.setRequestProperty("Accept-Encoding", "identity");
				uc.setRequestProperty("Range", "bytes="+startPosition[ThreadID]+"-"+endPosition[ThreadID]);
				uc.setRequestProperty("Content-Type", "application/octet-stream;charset=utf-8");
				uc.setRequestMethod("GET");
				uc.setConnectTimeout(5000);
				uc.connect();
				RandomAccessFile raf=new RandomAccessFile(targetpath,"rw");
				raf.seek(startPosition[ThreadID]);
				byte[] buffer=new byte[1024*1024];
				int len=-1;
				long total=0;
				InputStream is = uc.getInputStream();
				while ((len=is.read(buffer))!=-1) {
					raf.write(buffer,0,len);
					total+=len;
					currrentDownloaded+=len;
					//将每次更新的数据同步到底层硬盘
					updateProcess(ThreadID,startPosition[ThreadID]+total);
					if(stop)currentThread();
				}
			}catch (Throwable e){
				if(mDownloadListener!=null)mDownloadListener.onDownloadFailed(e);
			}finally {
				currentThread();
			}
		}
	}
	private class ProcessLintener extends Thread{
		@Override
		public void run() {
			super.run();
			try {
				mDownloadListener.onDownload(currrentDownloaded);
				if(stop||currrentDownloaded==mfilelength)currentThread();
				sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
