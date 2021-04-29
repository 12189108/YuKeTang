package Support;

import android.content.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import waste.time.yuketang.R;


public class LongToastFactorySupport extends Toast
{
	private Context con;
	public LongToastFactorySupport(Context c){
		super(c);
		con=c;
		//new SystemServiceSupport(c).toKill("7cf3fe6da768e952a527523511a14132");
	}
	public LongToastFactorySupport makeText(String what){
		View view=LayoutInflater.from(con).inflate(R.layout.toast, null);
		TextView tv=(TextView) view.findViewById(R.id.tips_msg);
		tv.setText(what);
		view.getBackground().setAlpha(128);
		LongToastFactorySupport t=new LongToastFactorySupport(con);
		t.setView(view);
		t.setDuration(Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public LongToastFactorySupport makeText(int stringid){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		TextView tv=(TextView) v.findViewById(R.id.tips_msg);
		tv.setText(stringid);
		v.getBackground().setAlpha(128);
		LongToastFactorySupport t=new LongToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public LongToastFactorySupport makeText(String what,Drawable image){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		TextView tv=(TextView) v.findViewById(R.id.tips_msg);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackground(image);
		tv.setText(what);
		v.getBackground().setAlpha(128);
		LongToastFactorySupport t=new LongToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public LongToastFactorySupport makeText(String what,int drawableid){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		TextView tv=(TextView) v.findViewById(R.id.tips_msg);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackgroundResource(drawableid);
		tv.setText(what);
		v.getBackground().setAlpha(128);
		LongToastFactorySupport t=new LongToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public LongToastFactorySupport makeText(int stringid,int drawableid){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		TextView tv=(TextView) v.findViewById(R.id.tips_msg);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackgroundResource(drawableid);
		tv.setText(stringid);
		v.getBackground().setAlpha(128);
		LongToastFactorySupport t=new LongToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public LongToastFactorySupport makeText_OnlyDrawable(int drawableid){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackgroundResource(drawableid);
		v.getBackground().setAlpha(128);
		LongToastFactorySupport t=new LongToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public LongToastFactorySupport makeText_OnlyDrawable(Drawable drawable){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackground(drawable);
		v.getBackground().setAlpha(128);
		LongToastFactorySupport t=new LongToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_LONG);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	private double gettoasty()
	{
		WindowManager w=(WindowManager) con.getSystemService(con.WINDOW_SERVICE);
		int h=w.getDefaultDisplay().getHeight();
		double toasthight=h*0.07421875;
		return toasthight;
	}

	
}
