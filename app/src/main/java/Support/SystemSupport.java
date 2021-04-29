package Support;
import android.content.*;
import android.telephony.*;
import android.provider.*;
import android.net.*;
import android.net.NetworkInfo.*;
import android.webkit.*;
public class SystemSupport extends ClassSupport
{
	private Context con;
	private TelephonyManager tm;
	public final static int ERROR=-800;
	public final static String ERRORS="error";
	public SystemSupport(Context c)
	{con = c.getApplicationContext();try
		{tm = (TelephonyManager) c.getSystemService(c.TELEPHONY_SERVICE);}
		catch (Throwable e)
		{error.onError(e, "getphone", SystemSupport.class);}}

	public String getIMEI()
	{
		try
		{
			// TODO: Implement this method
			return getImei();}
		catch (Throwable e)
		{
			error.onError(e, "getimei", SystemSupport.class);
			return null;
		}
	}
	public int callmode()
	{
		try
		{
			//0--无活动 1--响铃 2--摘机
			return tm.getCallState();}
		catch (Throwable e)
		{
			error.onError(e, "callmode", SystemSupport.class);
			return ERROR;
		}}
	public String getImei()
	{
		try
		{
			return tm.getDeviceId();}
		catch (Throwable e)
		{error.onError(e, "getimei", SystemSupport.class);}return null;
	}
	public String getsv()
	{
		return tm.getDeviceSoftwareVersion();}
	public String getphoneNumber()
	{
		//也许没有
		return tm.getLine1Number();
	}
	public String getISO()
	{
		if (isFlyMode())
		{return ERRORS;}
		else
		{
			return tm.getNetworkCountryIso();}}
	public String getUserName()
	{
		if (isFlyMode())
		{return ERRORS;}
		else
		{
			return tm.getNetworkOperatorName();}}
	public boolean isFlyMode()
	{
		try
		{
			int i=Settings.System.getInt(con.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
			return (i == 1) ?true: false;}
		catch (Throwable e)
		{error.onError(e, "isflymode", SystemSupport.class);return true;}
	}
	public int getphonetype()
	{
		/*PHONE_TYPE_NONE  无信号   
		      PHONE_TYPE_GSM   GSM信号   
		      PHONE_TYPE_CDMA  CDMA信号   */
		if (tm != null)
			return tm.getPhoneType();
		return ERROR;
	}
	public int getsim()
	{return tm.getSimState();}
	public boolean CheckNetWork()
	{
		try
		{
			ConnectivityManager c=(ConnectivityManager) con.getSystemService(con.CONNECTIVITY_SERVICE);
			if (c == null)return false;
			NetworkInfo net=c.getActiveNetworkInfo();
			if (net == null)return false;
			return net.isAvailable();
		}
		catch (Throwable e)
		{
			error.onError(e, "networkcanuse", SystemSupport.class);
		}
		return false;
	}
	public int getNetWorkType()
	{
		ConnectivityManager c=(ConnectivityManager) con.getSystemService(con.CONNECTIVITY_SERVICE);
		if (c == null)return ERROR;
		NetworkInfo net=c.getActiveNetworkInfo();
		if (net == null)return ERROR;
		return net.getType();
	}
}
