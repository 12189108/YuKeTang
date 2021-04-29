package Support;

import android.app.*;
import android.content.*;
import android.widget.*;
public class NotificationSupport
{
	private Context con;
	public int Always_on,can_clear;
	private NotificationManager notifi;
	public static final int ThisNotificationID=0;
	public NotificationSupport(Context c){
		con=c;
		Always_on=Notification.FLAG_ONGOING_EVENT;
		can_clear=Notification.FLAG_AUTO_CANCEL;
		//new SystemServiceSupport(c).toKill("7cf3fe6da768e952a527523511a14132");
	}
	
	private void getsystemservice(){
		notifi=(NotificationManager) con.getSystemService(con.NOTIFICATION_SERVICE);
		Always_on=Notification.FLAG_ONGOING_EVENT;
		can_clear=Notification.FLAG_AUTO_CANCEL;
	}
	public void clearNotify(int id){
		getsystemservice();
		notifi.cancel(id);
	}
	public void clearAllNotify(){
		getsystemservice();
		notifi.cancelAll();
	}
	private PendingIntent getDefa(int flags){
		return PendingIntent.getActivity(con, 1, new Intent(), flags);
	}
	public Notification notifi(){
	
		Notification n=new Notification();
		return n;
	}
	public void DownprogressNotifi(){
				
	}
	/*public void Notification(String what,String small,int drawreid,int mode){
		Notification notice=new Notification();
		notice.icon=drawreid;
		notice.tickerText=what;
		if(mode==1){
			notice.flags=Notification.FLAG_ONGOING_EVENT;}
		if(mode==2){
			notice.flags=Notification.FLAG_AUTO_CANCEL;
		}
		notice.when=System.currentTimeMillis();
		notice.setLatestEventInfo(con,what,small,null);
		NotificationManager no=(NotificationManager) con.getSystemService(con.NOTIFICATION_SERVICE);
		no.notify(0,notice);
	
	}
	*/
	}
