package Support;
import Support.*;
import android.util.*;
import java.net.*;
import java.util.*;
import org.json.*;
import java.io.*;
public class TimeSupport
{
	//private Context c
	public TimeSupport(){
		//c=con;
	}
	private long time=-1;
	private String Time=null;
	public final static int get_Day(){
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	public final static int get_Hour(){
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	public final static int get_Year(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	public final static int get_Month(){
		return Calendar.getInstance().get(Calendar.MONTH)+1;
	}
	public final static int get_Mintute(){
		return Calendar.getInstance().get(Calendar.MINUTE);
	}
	public final static int get_SECOND(){
		return Calendar.getInstance().get(Calendar.SECOND);
	}
	public final static int get_MILLISECOND(){
		return Calendar.getInstance().get(Calendar.MILLISECOND);
	}
	public final static String get_ALLTIME(){
	return get_Year()+"年"+month()+day()+"   "+hour()+min()+second();
	}
	public static String month(){
		return get_Month()<10?"0"+get_Month()+"月":get_Month()+"月";
	}
	public static String day(){
		return get_Day()<10?"0"+get_Day()+"日":get_Day()+"日";
	}
	public static String hour(){
		return get_Hour()<10?"0"+get_Hour()+":":get_Hour()+":";
	}
	public static String min(){
		return get_Mintute()<10?"0"+get_Mintute()+":":get_Mintute()+":";
	}
	public static String second(){
		return get_SECOND()<10?"0"+get_SECOND()+"":get_SECOND()+"";
	}
	//需声明网络权限
	//每次调用应重新声明TimeSupport,否则得出的可能是上一次获取的数据
	public long getNetworkTimeStamp(){
	new Thread(new Runnable(){

		@Override
		public void run()
		{
			try{
				System.setProperty("http.agent","Forbidden");
				String recode = null;
				URL url=new URL("http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp");
				HttpURLConnection uc=(HttpURLConnection) url.openConnection();
				uc.setRequestProperty("Accept-Encoding", "identity");
				uc.setRequestMethod("GET");
				uc.connect();
				if (uc.getResponseCode() == 200)
				{
				ByteArrayOutputStream boas=new ByteArrayOutputStream();
				int read=-1;
				InputStream in=uc.getInputStream();
				while((read=in.read())!=-1)boas.write(read);
				byte[] data=boas.toByteArray();
				boas.flush();
				boas.close();
				if(uc.getContentEncoding()!=null)recode=new String(data,uc.getContentEncoding());
				else recode=new String(data);
				JSONObject json=new JSONObject(recode);
				time=json.getJSONObject("data").getLong("data");
			}else time=-1;
		}
		catch (Throwable e)
		{
			Log.e(this.toString(),"timeget_error",e);
			time=-1;
		}
		}
	}).start();
	return time;
	}
	//需声明网络权限
	public String getNetWorkTimeStamp(final int ReturnType){
		//ReturnType参数为1和2
		//参数为1时返回(example):2020-03-16 00:08:24
		//参数为2时返回(example):20200316000824
		//当参数为其他东西时默认为1
		Thread t=new Thread(new Runnable(){
		@Override
		public void run()
		{
			String etime=null;
			while(true){
			try{
				//HttpGet get=new HttpGet("http://quan.suning.com/getSysTime.do");
				System.setProperty("http.agent","Forbidden");
				URL url=new URL("http://quan.suning.com/getSysTime.do");
				HttpURLConnection uc=(HttpURLConnection) url.openConnection();
				uc.setRequestProperty("Accept-Encoding", "identity");
				//uc.setRequestProperty("User-Agent","Forbidden");
				uc.setRequestMethod("GET");
				uc.connect();
				if (uc.getResponseCode() == 200)
				{
					ByteArrayOutputStream boas=new ByteArrayOutputStream();
					int read=-1;
					InputStream in=uc.getInputStream();
					while((read=in.read())!=-1)boas.write(read);
					byte[] data=boas.toByteArray();
					boas.flush();
					boas.close();
					if(uc.getContentEncoding()!=null)etime=new String(data,uc.getContentEncoding());
					else etime=new String(data);
					JSONObject json=new JSONObject(etime);
				IOSupport.writeString("sdcard/e.log",json.toString()+"\n"+json.getString("sysTime2")+"\n"+json.getString("sysTime1"),IOSupport.GS_UTF_8);
				if(ReturnType==1){Time=json.getString("sysTime2");}
				else if(ReturnType==2)Time=json.getString("sysTime1");
				else Time=json.getString("sysTime2");
				}else Time=null;
			}
			catch (Throwable e)
			{
				try
				{
					IOSupport.writeString("sdcard/err.log", e.toString(), IOSupport.GS_UTF_8);
				}
				catch (Exception y)
				{}
				Log.e(this.toString(), "TimeGet_error", e);
				Time=null;
			}
		}
		}});
		t.start();
		return Time;
	}
	}

