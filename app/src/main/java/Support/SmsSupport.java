package Support;
import android.content.*;
import android.telephony.*;
import android.app.*;
import android.os.*;

public class SmsSupport
{
	private Context con;
	private Handler b;
	private SmsSupport.OnSendListener send;
	private final String SMS_ACTION="SENT_SMS_ACTION";
	private final String re="DELIVERED_SMS_ACTION";
	public final static int Error=-11;
	private a ab;
	public OooO0O0 o;
	public Oo0oOoo O;
	public Oo0oOo0o o0;
	private int a;
	public SmsSupport(Context con){
		this.con=con;
		b=new b(con.getMainLooper());
		
	}
	public void sendTextMessage(String number,String what){
		Intent i=new Intent(SMS_ACTION);
		PendingIntent p=PendingIntent.getBroadcast(con, 0, i, 0);
		Intent ii=new Intent(re);
		PendingIntent pp=PendingIntent.getBroadcast(con, 0, ii, 0);
		con.registerReceiver(new a(),new IntentFilter(re));
		SmsManager s=SmsManager.getDefault();
		s.sendTextMessage(number,null,what,pp,p);}
	public void setSendResultListener(OnSendListener s)
   	{
		this.send=s;
	}
	public void setSendResult(OooO0O0 o){
		this.o=o;
	}
	public void setSendResult(Oo0oOoo O){
		this.O=O;
	}
	public void setSendResult(Oo0oOo0o o0){
		this.o0=o0;
	}
	private class a extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context p1, Intent p2)
		{
			a=getResultCode();
			b.sendEmptyMessage(0);
			// TODO: Implement this method
		}
	}
	private class b extends Handler
	{
		public b(Looper l){
			super(l);
		}
		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			send.onSendResult(con,a);
			if(a==-1){
				o0.o(con);
				o.O(con);
			}
			else{o.o(con);
			O.o(con);
			}
		}
		
	}
	public static abstract interface OnSendListener{
		public abstract void onSendResult(Context con,int result);
	}
	public static abstract interface OooO0O0{
		public abstract void o(Context c);
		public abstract void O(Context c);
	}
	public static abstract interface Oo0oOoo{
		public abstract void o(Context c);
	}
	public static abstract interface Oo0oOo0o{
		public abstract void o(Context c);
	}
	public BroadcastReceiver o(){
		return ab;
	}
	}
