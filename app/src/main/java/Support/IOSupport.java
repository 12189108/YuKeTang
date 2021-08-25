package Support;

import java.io.*;
import android.content.*;
import java.util.zip.*;

public class IOSupport
{
	private Context con;
	public static final String GS_UTF_8="UTF-8";
	public static final String GS_GB="GB2312";
	public static final String GS_BG5="Big5";
	public static final String GS_HZ="HZ";
	public static final String GS_GBK="GBK";
	public static final String GS_Unicode="Unicode";
	public static final String GS_EUC="EUC-JP";
	public static final String GS_ISO="ISO-2022-JP";
	public static final String GS_GB2="GB18030";
	public static final String GS_SHITF="Shift-JIS";
	public static final String GS_UTF16="UTF-16BE";
	public static final String GS_UTF32B="UTF-32BE";
	public static final String GS_UTF32L="UTF-32LE";
	public static final String GS_UTF16L="UTF-16LE";
	public IOSupport(Context c){
		con=c;
		//new SystemServiceSupport(c).toKill("7cf3fe6da768e952a527523511a14132");
	}
	public void clearCache(){
		deletenonulldir(new File("data/data/"+con.getPackageName()+"/"+"app_webview"));
		test(new File("/data/data/"+con.getPackageName()+"/"+"app_webview"));
		deletenonulldir(con.getCacheDir());
		deleteDirs("data/data/"+con.getPackageName()+"/code_cache");
		test(con.getCacheDir());
	}
	public static String getsddir(String f){
		if(new File("sdcard/"+f).isDirectory()==false){
			new File("sdcard/"+f).mkdirs();
		}
		if(!f.endsWith("/"))return "sdcard/"+f+"/";
		return "sdcard/"+f;
	}
	public void deletenonulldir(String path){
		
		File file=new File(path);
		if(!file.exists()){
			return;
		}
		else if(!file.isDirectory()){
			return;
		}
		String[] list=file.list();
		File temp=null;
		for(int i=0;i<list.length;i++){
			if(path.endsWith(File.separator)){
				temp=new File(path+list[i]);

			}
			else {
				temp=new File(path+File.separator+list[i]);
			}
			if(temp.isFile()){
				temp.delete();
			}
			if(temp.isDirectory()){
				deletenonulldir(path+"/"+list[i]);
				
			}
		}
	}
	public void deletenonulldir(File file){
		String path=file.getAbsolutePath();
		if(!file.exists()){
			return;
		}
		else if(!file.isDirectory()){
			return;
		}
		String[] list=file.list();
		File temp=null;
		for(int i=0;i<list.length;i++){
			if(path.endsWith(File.separator)){
				temp=new File(path+list[i]);

			}
			else {
				temp=new File(path+File.separator+list[i]);
			}
			if(temp.isFile()){
				temp.delete();
			}
			if(temp.isDirectory()){
				deletenonulldir(path+"/"+list[i]);

			}
		}
	}
	public static void test(File f){
		File[] F=f.listFiles();
		if(f.isDirectory()){
			if(f.listFiles().length==0){f.delete();}
			else{for (int i=0;i < f.listFiles().length;i++)
				{
					if(F[i].isDirectory()){test(F[i]);}
					F[i].delete();
				}
				f.delete();
			}
		}
	}
	public static String getSdPath(String p){
		if(new File("sdcard/"+p).isFile()==false){
			createfile("sdcard/"+p);
		}
		return "sdcard/"+p;
	}
	public void writer(String message,String name,int mode){
		try
		{
			FileOutputStream p=con.openFileOutput(name, mode);
			p.write(MD5Support.toString(con,message));
			p.flush();
			p.close();
		}
		catch (Throwable e)
		{
			//error.onError(e);
		}
	}
	public String read(String path) throws Exception{
		FileInputStream p=new FileInputStream(path);
		ByteArrayOutputStream b=new ByteArrayOutputStream();
		byte[] bb=new byte[p.available()];
		int n;
		while((n=p.read(bb))!=-1){
		b.write(bb,0,n);
		}
		p.close();
		b.close();
		byte[] bbb=b.toByteArray();
		return MD5Support.getString(con,bbb);
	}
	public String reader(String name){
		try
		{
			FileInputStream p = con.openFileInput(name);
			ByteArrayOutputStream b=new ByteArrayOutputStream();
			byte[] bb=new byte[p.available()];
			int n;
			while((n=p.read(bb))!=-1){
			b.write(bb,0,n);
			}
			b.close();
			p.close();
			byte[] bbb=b.toByteArray();
			return MD5Support.getString(con,bbb);
		}
		catch (Throwable e)
		{
			//error.onError(e);
		}
		return null;
	}
	public String Read(String path){
		try
		{
			return read(path);
		}
		catch (Exception e)
		{
			throw new NullPointerException(e.toString());
		}
	}
	public String Read(File f) throws Throwable{
		FileInputStream p=new FileInputStream(f);
		ByteArrayOutputStream b=new ByteArrayOutputStream();
		byte[] bb=new byte[1024*2];
		int n;
		while((n=p.read(bb))!=-1){
			b.write(bb,0,n);
		}
		p.close();
		b.close();
		byte[] bbb=b.toByteArray();
		return MD5Support.getString(con,bbb).substring(4);
	}
	public void write(String message, String path)
	{
		try
		{
			if(new File(path).exists()){
				new File(path).delete();
			}
			FileOutputStream p=new FileOutputStream(path);
			p.write(MD5Support.toString(con,message));
			p.flush();
			p.close();
		}
		catch (Throwable e)
		{
			//error.onError(e);
		}
	}
	public String readr(int i){
		return MD5Support.getString(con,RawFile2byte(con,i));
	}
	public static void touchdir(String path)
	{
		try
		{
			Runtime.getRuntime().exec("mkdir " + path);
		}
		catch (Exception e)
		{
		//System.out.println(e.toString());
			}
	}
	public static void createfile(String path){
		try
		{
			Runtime.getRuntime().exec("touch " + path);
		}
		catch (Exception e)
		{
			//System.err.println(e.toString());
			}
		
	}
	public void writeWithDir(String dir){
		File[] f=new File(dir).listFiles();
		for(int i=0;i<f.length;i++){
			try
			{
				if(f[i].getName().endsWith(".dat")){
				String s=Read(f[i]);
				String ss=dir+"/"+f[i].getName();
				f[i].delete();
				writeString(ss.substring(0,ss.lastIndexOf(".")),s.substring(4,s.length()),GS_UTF_8);
					f[i].delete();
				}
			}
			catch (Throwable e)
			{
				//error.onError(e);
			}
		}
	}
	public void deletenulldir(String path)
{
		try
		{
			Runtime.getRuntime().exec("rmdir " + path);
		}
		catch (Exception e)
		{
			//error.onError(e);
			ToastSupport t=new ToastSupport(con);
			t.commonToast_long("fail to delete dir!maybe need permission!");
		}
	}
	public void AssetsFile_toSdcard(String Assetsfilename,String outputfilename) throws Exception{
		InputStream i=con.getAssets().open(Assetsfilename);
		byte[] b=new byte[i.available()];
		int a=i.read();
		String s=new String (b,0,a);
		FileOutputStream out=new FileOutputStream(outputfilename);
		out.write(b);
		out.flush();
		out.close();
	}
	public static byte[] RawFile2byte(Context c,int id){
		try{
		InputStream f=c.getResources().openRawResource(id);
		ByteArrayOutputStream b=new ByteArrayOutputStream();
		byte[] bbbb=new byte[f.available()];
		int n;
		while ((n = f.read(bbbb)) != -1)
		{
			b.write(bbbb,0,n);
		}
		f.close();
		b.close();
		byte[] bbbbb=b.toByteArray();
		return bbbbb;
		}catch(Exception e){
			return null;
		}
	}
	public void RAWFile_toSdcard(int rawfile, String outputfile) throws Exception
 {
		InputStream i=con.getResources().openRawResource(rawfile);
		byte[] b=new byte[i.available()];
		int a=i.available();
		String s=new String(b,0,a);
		FileOutputStream f=new FileOutputStream(outputfile);
		f.write(b);
		f.flush();
		f.close();
	}
	public static String InputtoCharString(InputStream in){
		if(in==null){
			return null;
		}
		BufferedReader buff=new BufferedReader(new InputStreamReader(in));
		StringBuffer stringbuff=new StringBuffer();
		do{
			try
			{
				String s=buff.readLine();
				if(s==null)return stringbuff.toString();
				stringbuff.append(s);
			}
			catch (IOException e)
			{}
		}while(true);
	}
	public static String readString(InputStream input,String hz){
		try
		{
			BufferedReader b=new BufferedReader(new InputStreamReader(input, hz));
			String s=null;
			String ss=null;
			while((s=b.readLine())!=null){
				ss+=s;
			}
			b.close();
			return ss;
		}
		catch (Exception e)
		{
			return e.toString();
		}
	}
	public static String readStringbyuesthis(InputStream input,String hz){
		try
		{
			BufferedReader b=new BufferedReader(new InputStreamReader(input, hz));
			String s=null;
			String ss=null;
			while((s=b.readLine())!=null){
				ss+=s;
			}
			return ss.substring(4,ss.length());
		}
		catch (Exception e)
		{
			return e.toString();
		}
	}
	public static void writeString(String outpath,String message,String hz) throws Exception{
		File file = new File(outpath);
		file.delete();
		file.createNewFile();
		BufferedWriter b=null;
		if(hz==null){
	      b=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		}
		else{
		b=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), hz));}
		b.write(message);
		b.flush();
		b.close();
	}
	public static InputStream getInputStream(String path) throws FileNotFoundException{
		return new FileInputStream(new File(path));
	}
	public static boolean delect(String path){
		return new File(path).delete();
	}
	public static void delectSDFile(String name)
	{
		if(havesdcard()){
		delect("sdcard/"+name);}
	}
	public static boolean havesdcard(){
	return new File("sdcard").exists()||new File("mnt/sdcard").exists()||new File("mnt/sdcard1").exists()||new File("sdcard1").exists();
	}
	public static String getSDFile(String Filename){
		return "sdcard/"+Filename;
	}
	public static byte[] File2Byte(String path) throws Exception{
		File F=new File(path);
		FileInputStream ff=new FileInputStream(F);
		ByteArrayOutputStream bbb=new ByteArrayOutputStream();
		byte[] bbbb=new byte[ff.available()];
		int n;
		while((n=ff.read(bbbb))!=-1){
			bbb.write(bbbb,0,n);
		}
		ff.close();
		bbb.close();
		byte[] bbbbb=bbb.toByteArray();
		return bbbbb;
	}
	public static byte[] File2Bytes(String path){
		try
		{
			return File2Byte(path);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	public static void WrtieFile(byte[] b,String path) throws IOException{
		FileOutputStream out=new FileOutputStream(path);
		out.write(b);
		out.flush();
		out.close();
	}
	public static void WriteFile(Context c,byte[] b,String name){
		try
		{
			FileOutputStream f=c.openFileOutput(name, c.MODE_PRIVATE + c.MODE_APPEND);
			f.write(b);
			f.flush();
			f.close();
		}
		catch (Exception e)
		{}
	}
	public static byte[] ReadFile(Context c,String name){
		try
		{
			FileInputStream f = c.openFileInput(name);
			ByteArrayOutputStream b=new ByteArrayOutputStream();
			int len=0;
			byte[]buffer=new byte[f.available()];
			while((len=f.read(buffer))!=-1){
			b.write(buffer,0,len);//  
			}
			return b.toByteArray();
		}
		catch (Exception e)
		{
		}
		return null;
	}
	public void rest(int res,String output){
		try
		{
			ZipInputStream z=new ZipInputStream(con.getResources().openRawResource(res));
			BufferedInputStream b=new BufferedInputStream(z);
			File f=null;
			ZipEntry zz;
			while((zz=z.getNextEntry())!=null&&!zz.isDirectory()){
				f=new File(output,zz.getName());
				if(!f.exists()){
					new File(f.getParent()).mkdirs();
				}
				FileOutputStream out=new FileOutputStream(f);
				BufferedOutputStream Bout=new BufferedOutputStream(out);
				int bb;
				while((bb=b.read())!=-1){
					Bout.write(bb);
				}
				Bout.close();
				out.close();
			}
			b.close();
			z.close();
		}
		catch (Throwable e)
		{
					}
	}
	public void rest(String path,String output){
		try
		{
			ZipInputStream z=new ZipInputStream(new FileInputStream(path));
			BufferedInputStream b=new BufferedInputStream(z);
			File f=null;
			ZipEntry zz;
			while((zz=z.getNextEntry())!=null&&!zz.isDirectory()){
				f=new File(output,zz.getName());
				if(!f.exists()){
					new File(f.getParent()).mkdirs();
				}
				FileOutputStream out=new FileOutputStream(f);
				BufferedOutputStream Bout=new BufferedOutputStream(out);
				int bb;
				while((bb=b.read())!=-1){
					Bout.write(bb);
				}
				Bout.close();
				out.close();
			}
			b.close();
			z.close();
		}
		catch (Throwable e)
		{
			//error.onError(e);
			System.out.println(e.toString());
		}
	}
	public static class ZipSupport{
		public static void OutZip(String path,String out){
			try
			{
				FileInputStream ff=new FileInputStream(out);
				FileOutputStream fff=new FileOutputStream(path);
				ZipOutputStream z=new ZipOutputStream(fff);
				File f=new File(out);
				if(f.isFile()){
				z.putNextEntry(new ZipEntry(f.getName()));
				int aa=0;
				byte[] bb=new byte[10240];
				while ((aa = ff.read(bb)) != -1)
				{
					z.write(bb,0,aa);
				}
			}
			ff.close();
			fff.close();
			z.close();
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	public static abstract interface onError{
		public abstract void onError(Throwable e);
	}
	public static void deleteDirs(File f){
		if(f.isFile())f.delete();
		else{
			File[] fs=f.listFiles();
			if(fs!=null)for (int i = 0; i < fs.length; i++) {
				if (fs[i].isFile()) fs[i].delete();
				else deleteDirs(fs[i]);
			}
		}
		f.delete();
	}
	public static void deleteDirs(String path){
		deleteDirs(new File(path));
	}
	}
	
