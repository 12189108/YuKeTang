package Support;
import java.io.*;
import java.net.*;

public class MDownloadSupport
{
	private boolean Stop=false;
	public interface DownloadListener{
		public void onCancle();
		public void onFail();
		public void onProgress(long currentLocaltion);
		public void onStart();
		public void onComplete();
	}
	private class DownloadInformation{
		
	}
	//
	private class DownloadThread extends Thread
	{
		private File savefile;
		private URL downurl;
		private int block;
		private int ThreadID=-1;
		private long downlength;
		private boolean finish=false;
		public DownloadThread(URL downurl,File saveFile,int block,long downlength,int ThreadID){
			this.downurl=downurl;
			this.savefile=saveFile;
			this.block=block;
			this.ThreadID=ThreadID;
			this.downlength=downlength;
		}
		@Override
		public void run()
		{
			// TODO: Implement this method
			super.run();
			if(downlength<block){
				try
				{
					long startPos=block*(ThreadID-1)+downlength;
					long endPos=block*(ThreadID-1);
					HttpURLConnection conn=(HttpURLConnection) downurl.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Accept-Encoding", "identity");
					conn.setRequestProperty("Charset","UTF-8");
					conn.setRequestProperty("Referer",downurl.toString());
					conn.setRequestProperty("Connection","Keep-Alive");
					conn.setRequestProperty("Range","bytes="+startPos+"-"+endPos);
					InputStream input=conn.getInputStream();
					byte[] buffer=new byte[1024];
					int offset=0;
					RandomAccessFile threadfile=new RandomAccessFile(savefile,"rwd");
					threadfile.seek(startPos);
					while(!Stop&&(offset=input.read(buffer,0,1024))!=-1){
						threadfile.write(buffer,0,offset);
						downlength+=offset;
					}
					threadfile.close();
					input.close();
					finish=true;
				}
				catch (Throwable e)
				{}
			}
		}
		public boolean isFinish(){
			return finish;
		}
		public long getDownLength(){
			return downlength;
		}
	}
	//
}
