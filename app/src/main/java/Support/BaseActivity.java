package Support;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.io.*;
import android.view.View.OnClickListener;
import waste.time.yuketang.R;

public class BaseActivity extends Activity
{
	private LinearLayout LinearLayouts,LinearLayout0,LinearLayout1;
	private ImageView ImageView;
	private GridView GridView;
	private TextView TextView; 
	public DialogFactorySupport DialogFactorySupport;
	public DeviceSupport DeviceSupport;
	public ShortToastFactorySupport ShortToastFactorySupport;
	public DialogSupport DialogSupport;
	public LongToastFactorySupport LongToastFactorySupport;
	public IOSupport IOSupport;
	public IOHelperSupport IOHelperSupport;
	public SystemSupport SystemSupport;
	public SystemServiceSupport SystemServiceSupport;
	public SharedPreferencesSupport SharedPreferencesSupport;
	public int WRAP_CONTENT,MATCH_PARENT;
	public Context Context;
	public View v;
	public LogSupport LogSupport;
	public void initActivity(Context context){
		setTheme(android.R.style.Theme_Material_Light_NoActionBar);
		v=LayoutInflater.from(this).inflate(R.layout.tab,null);
		setContentView(v);
		LinearLayouts=v.findViewById(R.id.tabLinearLayout);
		LinearLayout0= v.findViewById(R.id.tabLinearLayout2);
		LinearLayout1= v.findViewById(R.id.tabLinearLayout0);
		ImageView=v.findViewById(R.id.tabImageView1);
		TextView=v.findViewById(R.id.tabTextView1);
		GridView=findViewById(R.id.tabGridView);
		LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)(new SystemServiceSupport(this).getWindowHight()*0.073125));
		LinearLayout1.setLayoutParams(ll);
		new SystemServiceSupport(context).getString();
		setTabImage(getPackageIcon());
		setTabTitle(getAppName());
		DialogFactorySupport=new DialogFactorySupport(context);
		//NetSecurity();
		ShortToastFactorySupport=new ShortToastFactorySupport(context);
		DialogSupport=new DialogSupport(context);
		LongToastFactorySupport=new LongToastFactorySupport(context);
		IOSupport=new IOSupport(context);
		SystemServiceSupport=new SystemServiceSupport(context);
		LogSupport=new LogSupport(context);
		//SystemServiceSupport.checkKeyboard(this,SystemServiceSupport.getString(this,R.string.LTitle));
		IOHelperSupport=new IOHelperSupport(context);
		SystemSupport=new SystemSupport(context);
		DeviceSupport=new DeviceSupport(context);
		SystemServiceSupport.getProcess();
		SharedPreferencesSupport=new SharedPreferencesSupport(context);
		WRAP_CONTENT=LinearLayout.LayoutParams.WRAP_CONTENT;
		MATCH_PARENT=LinearLayout.LayoutParams.MATCH_PARENT;
		//SystemServiceSupport.checkNetWork();
		//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITAT)getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		if(SystemServiceSupport.getSystemVersion()>=5){
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
			getWindow().setStatusBarColor(getResources().getColor(R.color.bg));
		}
		this.Context=context;
		initfile();
	}
	public View setContentView(int layoutResID,int w,int h)
	{
		View V=LayoutInflater.from(this).inflate(layoutResID, null);
		LinearLayout0.addView(V,new LinearLayout.LayoutParams(w,h));
		return V;
	}

	public View setContentView(View view,int w,int h)
	{
		LinearLayout0.addView(view,new LinearLayout.LayoutParams(w,h));
		return view;
		// TODO: Implement this method
	}
	@Override
	public void setContentView(int layoutResID)
	{
		if(LinearLayout1!=null){setContentView(layoutResID,MATCH_PARENT,MATCH_PARENT);
		SystemServiceSupport.getWindow();
		}
		else super.setContentView(layoutResID);
	}

	public void setTabImage(Drawable d){ImageView.setImageDrawable(d);}
	public void setTabImage(Bitmap d){ImageView.setImageBitmap(d);}
	public void setTabImage(int d){ImageView.setImageResource(d);}
	public void setTabTitle(String title){TextView.setText(title);}
	public void setTabTitle(int title){TextView.setText(title);}
	private Drawable getPackageIcon()
	{try
		{
			PackageManager pm=getPackageManager();
			ApplicationInfo app=pm.getApplicationInfo(getPackageName(), 0);
			return app.loadIcon(pm);
		}
		catch (Throwable e)
		{throw new NullPointerException();}
	}

	public String getstring(int resId)
	{
		// TODO: Implement this method
		if(Context!=null) return MD5Support.getString(this,ByteTransformSupport.Base64Decode(getString(resId)));
		else return getString(resId);
	}
	
	public int getVersionCode(){
		int ver=-1;
		try
		{
			ver = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
		}
		catch (PackageManager.NameNotFoundException e)
		{}
		return ver;
	}
	public String getVersionName(){
		String ver=null;
		try
		{
			ver=getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		}
		catch (PackageManager.NameNotFoundException e)
		{}
		return ver;
	}
	private void NetSecurity(){
		//if(!(System.getProperty("http.proxyHost")==null&&System.getProperty("http.proxyPort")==null))SystemServiceSupport.toKill();
		//LongToastFactorySupport.makeText(System.getProperty("http.proxyHost")+System.getProperty("http.proxyPort")).show();
	}
		public Bitmap Capture(){
			hideKeyboard();
			LinearLayouts.setDrawingCacheEnabled(true);
			LinearLayouts.setDrawingCacheBackgroundColor(0xfffffff);
			LinearLayouts.buildDrawingCache(true);
			Bitmap b=Bitmap.createBitmap(LinearLayouts.getDrawingCache());
			LinearLayouts.setDrawingCacheEnabled(false);
			return b;
		}
	protected String getAppName(){
		PackageManager pm=getPackageManager();
		try
		{
			ApplicationInfo app=pm.getApplicationInfo(getPackageName(), 0);
			return app.loadLabel(pm).toString();
		}
		catch (Throwable e)
		{
			throw new NullPointerException();
		}
	}
	public void setTabImageClickListener(OnClickListener cilick){
		if(cilick!=null)ImageView.setOnClickListener(cilick);
	}
	public void setTabImageLongClickListener(OnLongClickListener lclick){
		if(lclick!=null)ImageView.setOnLongClickListener(lclick);
	}
	public void setOrientation(int mode){
		//横屏
		if(mode==0)setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//竖屏
		else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK&&Context!=null){
			DialogSupport.SimpleExitDialog(false);
			return true;
		}
		// TODO: Implement this method
		return false;
	}

	public View getView(int res){
		return LayoutInflater.from(Context).inflate(res,null);
	}
	private void initfile(){
		if(SharedPreferencesSupport.isnull("file")){
			String o0ooOo0Ooo0=MD5Support.getStringRandoms();
			SharedPreferencesSupport.putString("file","file",o0ooOo0Ooo0);
			IOSupport.WriteFile(Context,MD5Support.toString(Context,SystemServiceSupport.getapp()),o0ooOo0Ooo0);
		}
	}
	protected void ReStart(int retime){
		Intent intent=getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
		PendingIntent rintent=PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
		AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(alarmManager.RTC,System.currentTimeMillis()+retime,rintent);
		System.exit(0);
	}
	public void installApk(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
	//作死专用，清除所有数据
	protected void clearAllData(){
		try
		{
			Runtime.getRuntime().exec("pm clear " + getPackageName());
		}
		catch (IOException e)
		{}
	}
	protected void makeToast(int str){
		if(Context!=null)LongToastFactorySupport.makeText(getstring(str)).show();
	}
	protected void makeToast(int str,int drawable){
		if(Context!=null)LongToastFactorySupport.makeText(getstring(str),drawable).show();
	}
	protected void hideKeyboard()
	{
		if(Context!=null){SystemServiceSupport.hideKeyboard(v);}
	}
	protected void showKeyboard(){
		if(Context!=null)SystemServiceSupport.showKeyboard(v);
	}
	public static void toAppDetail(Context context) {
		Intent mIntent = new Intent();  
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		if (Build.VERSION.SDK_INT >= 9) {  
			mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");  
			mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));  
		} else if (Build.VERSION.SDK_INT <= 8) {  
			mIntent.setAction(Intent.ACTION_VIEW);  
			mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");  
			mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());  
		}  
		context.startActivity(mIntent); 
	}
	public boolean checkPermission(String Permission){
		if(Build.VERSION.SDK_INT>=23){
			return checkPermission(Permission,android.os.Process.myPid(),android.os.Process.myUid())==PackageManager.PERMISSION_GRANTED;
		}
		else return false;
	}
	public void requestPermissions(String[] Permissions){
		requestPermissions(Permissions,1);
	}
	/*public void OutIds(){
		try{
		JSONObject js=new JSONObject();
		js.put("R.layout.dialog",R.layout.dialog);
		js.put("R.layout.dialog_edit",R.layout.dialog_edit);
		js.put("R.layout.dialog_list",R.layout.dialog_list);
		js.put("R.layout.dialog_list_adper",R.layout.dialog_list_adper);
		js.put("R.layout.dialog_list_drawable",R.layout.dialog_list_drawable);
		js.put("R.layout.dialog_message",R.layout.dialog_message);
		js.put("R.layout.dialog_progress",R.layout.dialog_progress);
		js.put("R.layout.tab",R.layout.tab);
		js.put("R.layout.to_top",R.layout.to_top);
		js.put("R.layout.toast",R.layout.toast);
		js.put("R.drawable.back",R.drawable.back);
		js.put("R.drawable.bn_back",R.drawable.bn_back);
		}catch(Throwable t){}
	}*/
	public static interface onMenuClickListener{
		public void onMenuClickListener(int menu);
	}
	public void setTabMenuClickListener(final onMenuClickListener menu){
		if (menu != null)GridView.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
					{
						menu.onMenuClickListener(p3);
						// TODO: Implement this method
					}
				});
	}
	
}
