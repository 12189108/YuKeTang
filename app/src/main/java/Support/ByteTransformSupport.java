package Support;
import android.graphics.*;
import java.io.*;
import android.util.*;

public class ByteTransformSupport
{ 
	public static byte[] File2Byte(File file){
		try
		{
			FileInputStream fileinput=new FileInputStream(file);
			ByteArrayOutputStream byteout=new ByteArrayOutputStream();
			byte[] b=new byte[fileinput.available()];
			int len=0;
			while((len=fileinput.read(b))!=-1){byteout.write(b,0,len); }
			byte[] ba=byteout.toByteArray();
			fileinput.close();
			byteout.flush();
			byteout.close();
			return ba;
		}
		catch (Throwable e)
		{
			return e.toString().getBytes();
		}
	}
	public static Bitmap Byte2Bitmap(byte[] b){
		return BitmapFactory.decodeByteArray(b,0,b.length);
	}
	public static String Byte2String(byte[] b){
		return new String(b);
	}
	public static String Base64Encode(byte[] bytes){
		return Base64.encodeToString(bytes,Base64.DEFAULT);
	}
	public static byte[] Base64Decode(String base64){
		return Base64.decode(base64,Base64.DEFAULT);
	}
	public static File Bytes2File(String Filepath,byte[] b){
		FileOutputStream out;
		try
		{
			out=new FileOutputStream(Filepath);
			out.write(b,0,b.length);
			out.flush();
			out.close();
		}
		catch (Throwable e)
		{}
		return new File(Filepath);
	}
}
