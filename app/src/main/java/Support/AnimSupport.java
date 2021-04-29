package Support;

import android.content.*;
import android.util.*;
import android.view.animation.*;
public class AnimSupport
{
	//*TODO:匀速
	public static final int MODE_YX=0x2578;
	//*TODO:加速
	public static final int MODE_JX=0x3567;
	//*TODO:减速
	public static final int MODE_XS=0x2098;
	//*TODO:物理
	public static final int MODE_WL=0x5358;
	//*TODO:变速
	public static final int MODE_BS=0x2557;
    //*TODO:动画透明
	public static final AlphaAnimation Alpah(float start,float end,int animtime,boolean after,boolean whiles){
		AlphaAnimation a=new AlphaAnimation(start,end);
		a.setDuration(animtime);
		a.setFillEnabled(after);
		a.setFillAfter(after);
		if(whiles==true){
			a.setRepeatMode(Animation.RESTART);
			a.setRepeatCount(Animation.INFINITE);
		}
		LinearInterpolator l=new LinearInterpolator();
		a.setInterpolator(l);
		return a;
	}
	//*TODO:动画旋转
	public static final RotateAnimation rotate(int 角度,int animtime,boolean whiles,boolean alter){
		RotateAnimation ro=new RotateAnimation(0, 角度, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		if(whiles==true){
			ro.setRepeatMode(Animation.RESTART);
			ro.setRepeatCount(Animation.INFINITE);}
		ro.setFillAfter(alter);
		ro.setDuration(animtime);
		ro.setInterpolator(new LinearInterpolator());
		return ro;
	}
	//*TODO:尺寸渐变
	public static final ScaleAnimation Scale(float toX,float toY,int animtime,boolean after,boolean whiles){
		ScaleAnimation s=new ScaleAnimation(0f,toX,0f,toY,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		s.setDuration(animtime);
		s.setFillEnabled(after);
		s.setFillAfter(after);
		if(whiles==true){
			s.setRepeatMode(Animation.RESTART);
			s.setRepeatCount(Animation.INFINITE);
		}
		s.setInterpolator(new LinearInterpolator());
		return s;
		}
	/*TODO:移动动画*/
	public static final TranslateAnimation Translate(float toX,float toY,int animtime,boolean after,boolean whiles){
		TranslateAnimation a=new TranslateAnimation(10, toX, 10, toY);
		a.setDuration(animtime);
		a.setFillEnabled(after);
		a.setFillAfter(after);
		if(whiles==true){
			a.setRepeatMode(Animation.RESTART);
			a.setRepeatCount(Animation.INFINITE);
		}
		LinearInterpolator l=new LinearInterpolator();
		a.setInterpolator(l);
		return a;
	}
	public static final AnimationSet AnimationSet(boolean whiles,boolean after){
		AnimationSet a=new AnimationSet(true);
		a.setFillEnabled(after);
		a.setFillAfter(after);
		if(whiles==true){
			a.setRepeatMode(Animation.RESTART);
			a.setRepeatCount(Animation.INFINITE);
		}
		return a;
	}
	public static final TranslateAnimation Translate(float toX,float toY,int animtime,boolean after,boolean whiles,int mode){
		TranslateAnimation a=new TranslateAnimation(10, toX, 10, toY);
		a.setDuration(animtime);
		a.setFillEnabled(after);
		a.setFillAfter(after);
		if(whiles==true){
			a.setRepeatMode(Animation.RESTART);
			a.setRepeatCount(Animation.INFINITE);
		}
		LinearInterpolator l=new LinearInterpolator();
		if(mode==MODE_YX){
			a.setInterpolator(l);}
		if(mode==MODE_BS){
			a.setInterpolator(new AccelerateDecelerateInterpolator());
		}
		if(mode==MODE_JX){
			a.setInterpolator(new AccelerateInterpolator());
		}
		if(mode==MODE_XS){
			a.setInterpolator(new DecelerateInterpolator());
		}
		if(mode==MODE_WL){
			a.setInterpolator(new BounceInterpolator());
		}
		return a;
	}
	
	public static final ScaleAnimation Scale(float toX, float toY, int animtime, boolean after, boolean whiles, int mode)
{
		ScaleAnimation s=new ScaleAnimation(0f,toX,0f,toY,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		s.setDuration(animtime);
		s.setFillEnabled(after);
		s.setFillAfter(after);
		if(whiles==true){
			s.setRepeatMode(Animation.RESTART);
			s.setRepeatCount(Animation.INFINITE);
		}
		LinearInterpolator l=new LinearInterpolator();
		if(mode==MODE_YX){
			s.setInterpolator(l);}
		if(mode==MODE_BS){
			s.setInterpolator(new AccelerateDecelerateInterpolator());
		}
		if(mode==MODE_JX){
			s.setInterpolator(new AccelerateInterpolator());
		}
		if(mode==MODE_XS){
			s.setInterpolator(new DecelerateInterpolator());
		}
		
		return s;
	}
	public static final AlphaAnimation Alpah(float start, float end, int animtime, boolean after, boolean whiles, int mode)
{
		AlphaAnimation a=new AlphaAnimation(start,end);
		a.setDuration(animtime);
		a.setFillEnabled(after);
		a.setFillAfter(after);
		if(whiles==true){
			a.setRepeatMode(Animation.RESTART);
			a.setRepeatCount(Animation.INFINITE);
		}
		LinearInterpolator l=new LinearInterpolator();
		if(mode==MODE_YX){
		a.setInterpolator(l);}
		if(mode==MODE_BS){
			a.setInterpolator(new AccelerateDecelerateInterpolator());
		}
		if(mode==MODE_JX){
			a.setInterpolator(new AccelerateInterpolator());
		}
		if(mode==MODE_XS){
			a.setInterpolator(new DecelerateInterpolator());
		}
		return a;
	}
	public static final RotateAnimation rotate(int 角度,int animtime,boolean whiles,boolean alter,int mode){
		RotateAnimation ro=new RotateAnimation(0, 角度, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		if(whiles==true){
			ro.setRepeatMode(Animation.RESTART);
			ro.setRepeatCount(Animation.INFINITE);}
		ro.setFillAfter(alter);
		ro.setDuration(animtime);
		LinearInterpolator l=new LinearInterpolator();
		if (mode == MODE_YX)
		{
			ro.setInterpolator(l);}
		if(mode==MODE_BS){
			ro.setInterpolator(new AccelerateDecelerateInterpolator());
		}
		if(mode==MODE_JX){
			ro.setInterpolator(new AccelerateInterpolator());
		}
		if(mode==MODE_XS){
			ro.setInterpolator(new DecelerateInterpolator());
		}
		return ro;
	}
}
