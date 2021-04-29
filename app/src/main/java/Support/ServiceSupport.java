package Support;
import android.content.*;
import android.net.*;
import android.provider.*;
import java.util.regex.*;
import java.math.*;

public class ServiceSupport
{
	public final static String SmsPackageName="com.android.mms";
	public final static String SmsActivity=".ui.ConversationList";
	public final static String PeoplePackageName="com.android.contacts";
	public final static String PeopleActivity=".activities.PeopleActivity";
	public final static String CallPackageName="com.android.contacts";
	public final static String CallActivity=".DialtactsActivity";
	public final static String SmsType="vnd.android-dir/mms-sms";
	public final static Intent toSmsApp(){
		Intent i=new Intent();
		i.setClassName(SmsPackageName,SmsPackageName+SmsActivity);
		return i;
	}
	public final static Intent toPelopApp(){
		Intent i=new Intent();
		i.setClassName(PeoplePackageName,PeoplePackageName+PeopleActivity);
		return i;
	}
	public final static Intent toHome(){
		Intent i=new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		return i;
	}
	public final static Intent toCallHistory(){
		Intent i=new Intent();
		i.setAction(i.ACTION_CALL_BUTTON);
		return i;
	}
	public final static Intent toCall(){
		Intent i=new Intent(Intent.ACTION_DIAL);
		i.setClassName(CallPackageName,CallPackageName+CallActivity);
		return i;
	}
	public final static Intent toChosePeople(){
		Intent i=new Intent();
		i.setAction(i.ACTION_PICK);
		i.setData(Contacts.People.CONTENT_URI);
		return i;
	}
	public final static void WriteSms(final Context c,final String address,final String Message){
		new Thread(){

			@Override
			public void run()
			{ContentResolver co=c.getContentResolver();
				ContentValues value=new ContentValues();
				value.put("address",address);
				value.put("date",System.currentTimeMillis());
				value.put("type",1);
				value.put("body",Message);
				co.insert(Uri.parse("content://sms"),value);
				// TODO: Implement this method
			}
		}.start();
		}
	public final static Intent toOther(Context con,Class<?> c){
		return new Intent(con,c);
	}
	public static boolean isPhoneNumber(CharSequence number){
		String experss="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
		String express2="^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
		Pattern p=Pattern.compile(experss);
		Matcher m=p.matcher(number);
		Pattern p2=Pattern.compile(express2);
		Matcher m2=p2.matcher(number);
		return (m.matches()||m2.matches());
	}
	public static void startActivity(Context c,Class<?> classs){
		Intent i=new Intent(c,classs);
		i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
		c.startActivity(i);
	}
	public static String TenTo16(String Ten){
		return Integer.toHexString(Integer.parseInt(Ten));
	}
	public static String TenTo16(int ten){
		return Integer.toHexString(ten);
	}
	public static int String2int(String s){
		return Integer.parseInt(s);
	}
	public static String To16To10(String s){
		return new BigInteger(s,16).toString();}
	public static String To16To10(int s){
		return To16To10(s+"");
	}
	public static boolean String2boolean(String s){
		return s.equals("true")?true:false;
	}
	public static int getShowtimes(String input,String order){
		int a=input.length();
		int b=order.length();
		int k=0;
		for(int i=0;i+b<a;i++){
			String judge=input.substring(i, i + b);
			if(judge.equals(order))k=k+1;
		}
		return k; 
	}
	public static int getShowPosition(String input,String order,int n){
		int a=input.length();
		int b=order.length();
		int k=0;
		for(int i=0;i+b<a;i++){
			String judge=input.substring(i, i + b);
			if(judge.equals(order))k=k+1;
			if(k==n)return i;
		}
		return -1; 
	}
	public static int char_show_time(String ori,String spe){
		int time=0;
		String temp=ori;
		while(temp.contains(spe)){
			time++;
			temp=temp.replaceFirst(spe,"");
		}
		return time;
	}
}
