package Support;
import Support.*;
import android.content.*;
import android.graphics.*;
import android.hardware.*;
import android.util.*;

import android.hardware.Camera;

public class DeviceSupport
{
	private Context c;
	private Camera camera;
	private Camera.Parameters mp;
	public DeviceSupport(Context c){
		this.c=c;
	}
	/*
	注意:调用该方法时是否已申请权限及注册设备:
	 <uses-permission android:name="android.permission.FLASHLIGHT" />
	 <uses-permission android:name="android.permission.CAMERA" />
	 <uses-feature android:name="android.hardware.camera" />
	 <uses-feature android:name="android.hardware.autofocus" />
	*/
	public boolean OpenFlashlight(){
		try{
		camera=Camera.open();
		camera.setPreviewTexture(new SurfaceTexture(0));
		camera.startPreview();
		mp=camera.getParameters();
		mp.setFlashMode(mp.FLASH_MODE_TORCH);
		camera.setParameters(mp);
	}catch(RuntimeException r){
		new LongToastFactorySupport(c).makeText("权限不足:"+r.toString()).show();
	}catch(Throwable t){
		Log.e(getClass().toString(),t.toString());
		return false;
	}
	return true;
	}
	public boolean OffFlashlight(){
		if(camera!=null){
			mp=camera.getParameters();
			mp.setFlashMode(mp.FLASH_MODE_OFF);
			camera.setParameters(mp);
			camera.stopPreview();
			camera.release();
			camera=null;
			return true;
		}
		else return false;
	}
	//返回true表示操作成功
	//返回false表示出现问题
}
