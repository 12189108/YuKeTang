package Support;
import android.content.*;

public class ClassSupport
{
	protected onError error;
	public void setErrorListener(onError error){
		this.error=error;
	}
	public static String getString(Context c,int id){
		return MD5Support.getString(c,ByteTransformSupport.Base64Decode(c.getResources().getString(id)));
	}
	public static String getString(Context c,String s){
		return MD5Support.getString(c,ByteTransformSupport.Base64Decode(s));
	}
} 
