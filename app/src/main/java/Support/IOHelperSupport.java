package Support;

import javax.crypto.*;
import javax.crypto.spec.*;
import android.content.*;
import java.io.*;
import java.util.zip.*;
import java.util.*;
public class IOHelperSupport
{
	private Context context;
	public IOHelperSupport(Context context){
		this.context=context;
	}
	private Cipher init(String sKey, int cipherMode) {
		KeyGenerator k=null;
		Cipher c=null;
		try
		{
			IvParameterSpec lv=new IvParameterSpec(MD5Support.getMD5(new SystemServiceSupport(context).getapp()).substring(0,16).getBytes(IOSupport.GS_UTF_8));
			SecretKeySpec s=new SecretKeySpec(sKey.getBytes(IOSupport.GS_UTF_8),"AES");
			c=Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(cipherMode,s,lv);
		}
		catch (Throwable e)
		{
			
		}
		return c;
	}
	//加密
	public File encrypt(String password,String inputFilepath,String outputFilepath) throws Throwable{
		password=passwordchange(password);
		FileInputStream in=null;
		FileOutputStream out=null;
		File infile=null;
		File outfile=null;
		infile=new File(inputFilepath);
		outfile=new File(outputFilepath);
		in=new FileInputStream(infile);
		if(outfile.exists()){outfile.delete();}
		outfile.createNewFile();
		out=new FileOutputStream(outfile);
		Cipher c=init(password, Cipher.ENCRYPT_MODE);
		CipherInputStream cc=new CipherInputStream(in,c);
		byte[] cache=new byte[1024];
		int r=0;
		while((r=cc.read(cache))!=-1){
			out.write(cache,0,r);
			out.flush();
		}
		cc.close();
		out.close();
		in.close();
		return outfile;
	}
	public byte[] encrypt(String password,String inputFilepath) throws Throwable{
		password=passwordchange(password);
		FileInputStream in=null;
		File infile=null;
		infile=new File(inputFilepath);
		in=new FileInputStream(infile);
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		Cipher c=init(password, Cipher.ENCRYPT_MODE);
		CipherInputStream cc=new CipherInputStream(in, c);
		byte[] cache=new byte[1024];
		int r=0;
		while((r=cc.read(cache))!=-1){
			out.write(cache,0,r);
		}
		byte[] b=out.toByteArray();
		out.flush();
		cc.close();
		out.close();
		in.close();
		return b;
	}
	
	public File decrypt(String password,String inputFilepath,String outputFilepath) throws Throwable{
		password=passwordchange(password);
		FileInputStream in=null;
		FileOutputStream out=null;
		File infile=null;
		File outfile=null;
		infile=new File(inputFilepath);
		outfile=new File(outputFilepath);
		in=new FileInputStream(infile);
		if(outfile.exists()){outfile.delete();}
		outfile.createNewFile();
		out=new FileOutputStream(outfile);
		Cipher c=init(password, Cipher.DECRYPT_MODE);
		CipherOutputStream cc=new CipherOutputStream(out,c);
		byte[] cache=new byte[1024];
		int r=0;
		while((r=in.read(cache))>=0){
			cc.write(cache,0,r);
			cc.flush();
		}
		cc.close();
		out.close();
		in.close();
		return outfile;
	}
	public byte[] decrypt(String password,String inputFilepath) throws Throwable{
		password=passwordchange(password);
		FileInputStream in=null;
		File infile=null;
		infile=new File(inputFilepath);
		in=new FileInputStream(infile);
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		Cipher c=init(password, Cipher.DECRYPT_MODE);
		CipherOutputStream cc=new CipherOutputStream(out, c);
		byte[] cache=new byte[1024];
		int r=0;
		while((r=in.read(cache))>=0){
			cc.write(cache,0,r);
			cc.flush();
		}
		byte[] b=out.toByteArray();
		out.flush();
		cc.close();
		out.close();
		in.close();
		return b;
	}
	
	private String passwordchange(String password){
		return MD5Support.getMD5(password);
	}
	//参数说明:path输出目录
	public static void zip(String out,String path,File... fff) throws Throwable{
		if(!new File(out.substring(0,out.lastIndexOf("/"))).exists()){new File(out.substring(0,out.lastIndexOf("/"))).mkdirs();}
		if(!new File(out).exists()){new File(out).createNewFile();}
		ZipOutputStream z=zip(new ZipOutputStream(new FileOutputStream(out)), path, fff);
		z.close();
	}
	//参数说明:f 输出目录 path 打开zip后的首文件夹，若为""则直接为压缩目录的顺序，不可为null!!!!  onename 输入目录（可以为文件夹）
	public static void zip(String f,String path,String onename) throws Throwable{
		if(!new File(f.substring(0,f.lastIndexOf("/"))).exists()){new File(f.substring(0,f.lastIndexOf("/"))).mkdirs();}
		if(!new File(f).exists()){new File(f).createNewFile();}
		ZipOutputStream z=zip(new ZipOutputStream(new FileOutputStream(f)), path,new File[]{new File(onename)});
		z.close();
	}
	public static void zip(String f,String path,File onefile) throws Throwable{
		if(!new File(f.substring(0,f.lastIndexOf("/"))).exists()){new File(f.substring(0,f.lastIndexOf("/"))).mkdirs();}
		if(!new File(f).exists()){new File(f).createNewFile();}
		ZipOutputStream z=zip(new ZipOutputStream(new FileOutputStream(f)), path,new File[]{onefile});
		z.close();
	}
	//加密
	public void zip(String f,String path,File onefile,String password) throws Throwable{
		zip(f,path,onefile);
		encrypt(password,f,f);
	}
	public void zip(String f,String path,File[] file,String password) throws Throwable{
		zip(f,path,file);
		encrypt(password,f,f);
	}
	public void zip(String f,String path,String onefile,String password) throws Throwable{
		zip(f,path,onefile);
		encrypt(password,f,f);
	}
	
	private static ZipOutputStream zip(ZipOutputStream z, String path, File... src) throws Throwable
	{
		if(!path.equals("")){
		path=path.replaceAll("\\*","/");
		if(!path.endsWith("/")){
			path+="/";
		}}
		byte[] buf=new byte[1024];
		for(int i=0;i<src.length;i++){
			if(src[i].isDirectory()){
			File[] files=src[i].listFiles();
			String srcpath=src[i].getName();
			srcpath=srcpath.replaceAll("\\*","/");
			if(!srcpath.endsWith("/")){srcpath+="/";}
			z.putNextEntry(new ZipEntry(path+srcpath));
			zip(z,path+srcpath,files);
			}
			else{
				FileInputStream in=new FileInputStream(src[i]);
				z.putNextEntry(new ZipEntry(path+src[i].getName()));
				int l;
				while((l=in.read(buf))!=-1){
					z.write(buf,0,l);
				}
				z.closeEntry();
				in.close();
			}
		}
	return z;
	}
	private static ZipFile unzip(File zipFile,String outputpath)throws Throwable{
		if(!outputpath.endsWith("/")){outputpath+="/";}
		ZipFile z=new ZipFile(zipFile);
		File out=new File(outputpath);
		if(!out.exists()){out.mkdirs();}
		Enumeration<?> en=z.entries();
		while(en.hasMoreElements()){
			ZipEntry zz=(ZipEntry) en.nextElement();
			String s=zz.getName();
			InputStream in=z.getInputStream(zz);
			String outp=(outputpath+s).replace("\\*","/");
			File f=new File(outp.substring(0, outp.lastIndexOf("/")));
			if(!f.exists()){f.mkdirs();}
			if(new File(outp).isDirectory()){continue;}
			FileOutputStream outt=new FileOutputStream(outp);
			byte[] b=new byte[4*1024];
			int l;
			while((l=in.read(b))>=0){outt.write(b,0,l);}
			in.close();
			outt.close();
		}
		return z;
	}
	public static void unzip(String zipfile, String outputpath) throws Throwable
	{
		ZipFile z=unzip(new File(zipfile), outputpath);
		z.close();
	}
	public void unzip(String in,String out,String password) throws Throwable{
		File f=new File(in + ".temp");
		try{if(f.exists())f.delete();f.createNewFile();
			decrypt(password,in,in+".temp");
			unzip(in+".temp",out);}catch(Throwable e){throw e;}
		finally{f.delete();}
	}
	
	}
