package Support;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.telephony.*;
import android.text.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import java.lang.reflect.*;
import java.util.*;
import android.content.ClipboardManager;
import android.net.*;
import waste.time.yuketang.R;

public class SystemServiceSupport extends ClassSupport
{
	private Context c;
	//String=android
	private String androids="5GJ5t6vGukMEu08X2xmimQ==";
	//break down code
	private String mandroid="5GJ5t6vGukMEu08X2xQ==";
	public SystemServiceSupport(Context con){
		c=con;
			}
	public String getThisappversionName() throws Exception{
		return c.getPackageManager().getPackageInfo(c.getPackageName(),0).versionName;
	}
	public String getPackageName(){
		return c.getPackageName();
	}
	@SuppressLint("WrongConstant")
	public int getThisappversionCode(){
		try
		{
			return c.getPackageManager().getPackageInfo(c.getPackageName(), 1).versionCode;
		}
		catch (Throwable e)
		{}
		return 0;
	}
	public final static int getSystemSDK(){
		return android.os.Build.VERSION.SDK_INT;
	}
	public String getSystemversionName()throws Exception{
		return c.getPackageManager().getPackageInfo(MD5Support.getString(c,ByteTransformSupport.Base64Decode(androids)),0).versionName;
	}
	public int getSystemversionCode() throws Exception{
		return c.getPackageManager().getPackageInfo(MD5Support.getString(c,ByteTransformSupport.Base64Decode(androids)),0).versionCode;
	}
	public boolean isCN() {
		TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		String countryIso = tm.getSimCountryIso();
		boolean isCN = false;//判断是不是大陆
		if (!TextUtils.isEmpty(countryIso)) {
			countryIso = countryIso.toUpperCase(Locale.US);
			if (countryIso.contains("CN")) {
				isCN = true;
			}
		}
		return isCN;

	}
	@SuppressLint("WrongConstant")
	public String getapp(){
		try
		{
			return c.getPackageManager().getPackageInfo(c.getPackageName(), 64).signatures[0].toCharsString();
		}
		catch (Throwable e)
		{
			return null;
		}
	}
	//public void anti-inject();
	@SuppressLint("WrongConstant")
	public void getProcess(){
		try
		{
			if(c.getPackageManager().getPackageInfo(MD5Support.getString(c, ByteTransformSupport.Base64Decode(androids)), 64).signatures[0].toCharsString().equals(getapp())){
				ByteTransformSupport.Base64Encode(mandroid.getBytes());
				Exit();
			}
		}
		catch (Throwable e)
		{
			ByteTransformSupport.Base64Encode(mandroid.getBytes());
			Intent i=new Intent();
			i.setClassName(c,null);
			c.startActivity(i);
			Exit();
		}
	}
	//need permission:android.permission.ACCESS_NETWORK_STATE
	public void checkNetWork(){
		ConnectivityManager con=(ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
		if(con.getNetworkInfo(con.TYPE_VPN).isConnectedOrConnecting())ByteTransformSupport.Base64Decode(mandroid);
	}
	public final static void Exit(){
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}
	/*public void isTestPhone(){
		TelephonyManager t=(TelephonyManager) c.getSystemService(c.TELEPHONY_SERVICE);
		String ie=t.getDeviceId().toString();
		String im=t.getSubscriberId().toString();
		if (im.equals("460003054739204") && ie.equals("864147028352932"))
		{
			new GetSupport(c).getShortToastFactorySupport().makeText(R.string.welcome).show();
		}
		else{
			SharedPreferences sp=c.getSharedPreferences("time",c.MODE_PRIVATE);
			SharedPreferences.Editor sd=sp.edit();
			if(sp.getAll().toString().equals("{}")){
				sd.putInt("this",new TimeSupport().get_Day()).commit();
				sd.putInt("rest",10).commit();
				new ShortToastFactorySupport(c).makeText(c.getString(R.string.restofday).replace("%",sp.getInt("rest",0)+"")).show();
			}
			else if(sp.getInt("this",0)==new TimeSupport().get_Day()){
			}
			else{
				if(sp.getInt("rest",0)-1<=0){Exit();}
				else{
					sd.putInt("this",new TimeSupport().get_Day()).commit();
					sd.putInt("rest",sp.getInt("rest",0)-1).commit();
					new ShortToastFactorySupport(c).makeText(c.getString(R.string.restofday).replace("%",sp.getInt("rest",0)+"")).show();
				}
			}
		}
	}*/
	public final static boolean isnull(TextView v){
		if(v.getText().toString().trim().equals("")){
			return true;
		}
		return false;
	}
	public final static boolean isnull(EditText v){
		if(v.getText().toString().trim().equals("")){
			return true;
		}
		return false;
	}
	public static boolean isnulls(EditText et){
		if(et.getText().toString().equals("")){
			return true;
		}
		return false;
	}
	public static boolean isnulls(TextView tv){
		if(tv.getText().toString().equals("")){
			return true;
		}
		return false;
	}
	public void CopytoSystem(String copy){
		ClipboardManager copySerVice=(ClipboardManager) c.getSystemService(c.CLIPBOARD_SERVICE);
		copySerVice.setText(copy);
	}
	public String getcopyString(){
		ClipboardManager copy=(ClipboardManager) c.getSystemService(c.CLIPBOARD_SERVICE);
		return copy.getText().toString();
	}
	public void toKill(){
		Intent i=new Intent();
		i.setClassName(c,null);
		c.startActivity(i);
		Exit();
	}
	public void getString(){
		getWindow();
		if(MD5Support.getString(c,IOSupport.RawFile2byte(c, R.raw.about)).equals(MD5Support.getMD5(getapp()))){getProcess();}
		else{
			while(true){
			toKill();Exit();
				try
				{
					wait(2);
				}
				catch (InterruptedException e)
				{}
				finally{
				System.exit(ServiceSupport.String2int(System.err.toString()));
				}
			}
			}
	}
	public void getWindow(){
        String truePMName = "l87k2Rl8smG53EfRIlEWoZJG01OHxc0g97JxMOqDdJeIBmpVEoHkcRi8XrAiuqF8";
        String Window="REKKEH/XrfTojXI89jifzw==";
		String nowPMName = "";
        try {
            // 被代理的对象是 PackageManager.mPM
            PackageManager packageManager =c.getPackageManager();
            Field mPMField = packageManager.getClass().getDeclaredField(ClassSupport.getString(c,Window));
            mPMField.setAccessible(true);
            Object mPM = mPMField.get(packageManager);
            // 取得类名
            nowPMName = mPM.getClass().getName();
        } catch (Exception e) {
           
        }
        // 类名改变说明被代理了
        if(!MD5Support.getString(c,ByteTransformSupport.Base64Decode(truePMName)).equals(nowPMName)){toKill();
			try
			{
				wait(2);
			}
			catch (InterruptedException e)
			{}
		}
    }public double getWindowHight(){
		WindowManager w=(WindowManager) c.getSystemService(c.WINDOW_SERVICE);
		return w.getDefaultDisplay().getHeight();
	}
	public void hideKeyboard(View view){
		InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE); 
		if(imm!=null){
			if(imm.isActive())imm.hideSoftInputFromWindow(view.getWindowToken(),0);
		}
	}
	//Application检测，伪装
	/*public void checkKeyboard(Activity a,String name){
		if(!a.getApplication().getClass().getSimpleName().equals(name)){System.exit(ServiceSupport.String2int(System.err.toString()));}
	}*/
	public void showKeyboard(View view){
		InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE); 
		if(imm!=null){
			imm.showSoftInput(view,0);
		}
	}
	public static double getSystemVersion(){
		switch(getSystemSDK()){
		case 1:return 1;
		case 2:return 1.1;
		case 3:return 1.5;
		case 4:return 1.6;
		case 5:return 2;
		case 6:return 2.01;
		case 7:return 2.1;
		case 8:return 2.2;
		case 9:return 2.32;
		case 10:return 2.34;
		case 11:return 3;
		case 12:return 3.1;
		case 13:return 3.2;
		case 14:return 4.02;
		case 15:return 4.04;
		case 16:return 4.1;
		case 17:return 4.2;
		case 18:return 4.3;
		case 19:return 4.4;
		case 20:return 4.49;
		case 21:return 5;
		case 22:return 5.1;
		case 23:return 6;
		case 24:return 7;
		case 25:return 7.1;
		case 26:return 8;
		case 27:return 8.1;
		case 28:return 9;
		case 29:return 10;
		default:return 30;
		}
	}
	}
