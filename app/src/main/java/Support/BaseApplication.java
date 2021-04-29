package Support;
import android.app.*;
import android.content.*;
import java.lang.reflect.*;

public class BaseApplication extends Application
{
public BaseApplication(){
	try
	{
		new SystemServiceSupport(new ContextUntils().getContext()).getString();
	}
	catch (Throwable e)
	{}
}
	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		try
		{
			new SystemServiceSupport(new ContextUntils().getContext()).getString();
		}
		catch (Throwable e)
		{}
	}
	public class ContextUntils{
		public Context getContext() throws Throwable{
			Class activityThreadClass = Class.forName("android.app.ActivityThread");
			Method currentActivityThreadMethod =
                activityThreadClass.getDeclaredMethod("currentActivityThread");
			currentActivityThreadMethod.setAccessible(true);
			Object mainThreadObj = currentActivityThreadMethod.invoke(null);

			// 反射获取 mainThread 实例中的 mBoundApplication 字段
			Field mBoundApplicationField = activityThreadClass.getDeclaredField("mBoundApplication");
			mBoundApplicationField.setAccessible(true);
			Object mBoundApplicationObj = mBoundApplicationField.get(mainThreadObj);

			// 获取 mBoundApplication 的 packageInfo 变量
			if (mBoundApplicationObj == null);
			Class mBoundApplicationClass = mBoundApplicationObj.getClass();
			Field infoField = mBoundApplicationClass.getDeclaredField("info");
			infoField.setAccessible(true);
			Object packageInfoObj = infoField.get(mBoundApplicationObj);

			// 反射调用 ContextImpl.createAppContext(ActivityThread mainThread, LoadedApk packageInfo)
			if (mainThreadObj == null) throw new NullPointerException();
			if (packageInfoObj == null) throw new NullPointerException();
			Method createAppContextMethod = Class.forName("android.app.ContextImpl").getDeclaredMethod(
                "createAppContext", 
                mainThreadObj.getClass(), 
                packageInfoObj.getClass());
			createAppContextMethod.setAccessible(true);
			return (Context) createAppContextMethod.invoke(null, mainThreadObj, packageInfoObj);

			}
	}
}
