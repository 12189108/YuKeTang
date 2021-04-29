package Support;
import android.app.*;
import android.content.*;
import android.os.*;
public class GetSupport
{
	private static Context cc;
	public GetSupport(Context c){
		cc=c;
	}

	public ToastSupport getToastSupport(){
		return new ToastSupport(cc);
	}
	public IOSupport getIOSupport(){
		return new IOSupport(cc);
	}
	public SystemServiceSupport getSystemServiceSupport(){
		return new SystemServiceSupport(cc);
	}
	public static final TimeSupport getTimeSupport(){
		return new TimeSupport();
	}
	public NotificationSupport NotificationSupport(){
		return new NotificationSupport(cc);
	}
	public DialogSupport getDialogSupport(){
		return new DialogSupport(cc);
	}
	public NotificationSupport getNotificationSupport(){
		return new NotificationSupport(cc);
	}
	public static final AnimSupport getAnimSupport(){
		return new AnimSupport();
	}
	public MD5Support getMD5Support(){
		return new MD5Support();
	}
	public SharedPreferencesSupport getSharedPreferencesSupport(){
		return new SharedPreferencesSupport(cc);
	}
	public ShortToastFactorySupport getShortToastFactorySupport(){
		return new ShortToastFactorySupport(cc);
	}
	public LongToastFactorySupport getLongToastFactorySupport(){
		return new LongToastFactorySupport(cc);
	}
	public HightToastSupport getHightToastSupport(){
		return new HightToastSupport(cc);
	}
	public DialogFactorySupport getDialogFactorySupport(){
		return new DialogFactorySupport(cc);
	}
	public ServiceSupport getServiceSupport(){
		return new ServiceSupport();
	}
	public SmsSupport getSmsSupport(){
		return new SmsSupport(cc);
	}
}
