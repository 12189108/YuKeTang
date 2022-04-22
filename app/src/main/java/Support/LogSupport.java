package Support;

import android.content.*;
import java.io.*;
import java.util.logging.*;
import android.content.pm.*;
import android.content.pm.PackageManager.*;

public class LogSupport
{
	private Context c;
	public String paths;
	public LogSupport(Context c){
		this.c=c;
		paths="sdcard/log/"+new SystemServiceSupport(c).getPackageName()+"/";
		prepareFileDir();
		}
	public void writelog(String logcontent,String logname){
		if(new File(paths+logname+".logdat").exists()){
			String s=new IOSupport(c).Read(paths+logname+".logdat")+"\n";
			new IOSupport(c).write(TimeSupport.get_ALLTIME()+"\n"+logcontent+"\n"+s,paths+logname+".logdat");
		}
		else{
			new IOSupport(c).write(TimeSupport.get_ALLTIME() + "\n" + logcontent, paths + logname + ".logdat");
		}
	}
	private String info(){
		PackageManager pm=c.getPackageManager();
		try
		{
			PackageInfo aif = pm.getPackageInfo(c.getPackageName(), 0);
			int lab=aif.applicationInfo.labelRes;
			return c.getResources().getString(lab);
		}
		catch (PackageManager.NameNotFoundException e)
		{}
		return null;
	}
	public String readlog(String path)
{
		return new IOSupport(c).Read(paths+path+".logdat");
	}
	public void prepareFileDir(){
	new File(paths).mkdirs();
	new IOSupport(c).write(info(),paths+"info");
	}
	public void outputlog(String name,String outputpath){
		File f=new File(paths+name+".logdat");
		if(f.isFile()){
		try{
			String log=new IOSupport(c).Read(paths + name + ".logdat");
			new IOSupport(c).writeString(outputpath,log,IOSupport.GS_UTF_8);
			writelog("logwrite:outputsuccess file name='"+name+"'","logoutputhistory");
		}
		catch (Exception e)
		{
		writelog("logerror:file:"+name+" can't be logfile", "logoutputerror");
		}}
		else{writelog("logerror:file:"+name+" not exists","logoutputerror");}
		}
	private Logger getLogger(Class<?> logcontext){
		Logger loger=Logger.getLogger(logcontext.getName());
		loger.setLevel(Level.ALL);
		return loger;
	}
	public void clearLog(){
		IOSupport.deleteDirs(paths);
		prepareFileDir();
	}
}
