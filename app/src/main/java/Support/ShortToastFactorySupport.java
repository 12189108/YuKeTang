package Support;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.graphics.drawable.*;
import waste.time.yuketang.R;

public class ShortToastFactorySupport extends Toast
{
	private Context con;
	public ShortToastFactorySupport(Context c){
		super(c);
		con=c;
		//new SystemServiceSupport(c).toKill("7cf3fe6da768e952a527523511a14132");
	}
	public ShortToastFactorySupport makeText(String what){
		View view=LayoutInflater.from(con).inflate(R.layout.toast, null);
		TextView tv=(TextView) view.findViewById(R.id.tips_msg);
		tv.setText(what);
		view.getBackground().setAlpha(128);
		ShortToastFactorySupport t=new ShortToastFactorySupport(con);
		t.setView(view);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public ShortToastFactorySupport makeText(int stringid){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		TextView tv=(TextView) v.findViewById(R.id.tips_msg);
		tv.setText(stringid);
		v.getBackground().setAlpha(128);
		ShortToastFactorySupport t=new ShortToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public ShortToastFactorySupport makeText(String what,Drawable image){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		TextView tv=(TextView) v.findViewById(R.id.tips_msg);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackground(image);
		tv.setText(what);
		v.getBackground().setAlpha(128);
		ShortToastFactorySupport t=new ShortToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public ShortToastFactorySupport makeText(String what,int drawableid){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		TextView tv=(TextView) v.findViewById(R.id.tips_msg);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackgroundResource(drawableid);
		tv.setText(what);
		v.getBackground().setAlpha(128);
		ShortToastFactorySupport t=new ShortToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public ShortToastFactorySupport makeText(int stringid,int drawableid){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		TextView tv=(TextView) v.findViewById(R.id.tips_msg);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackgroundResource(drawableid);
		tv.setText(stringid);
		v.getBackground().setAlpha(128);
		ShortToastFactorySupport t=new ShortToastFactorySupport(con);
	    t.setView(v);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public ShortToastFactorySupport makeText_OnlyDrawable(int drawableid){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackgroundResource(drawableid);
		v.getBackground().setAlpha(128);
		ShortToastFactorySupport t=new ShortToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public ShortToastFactorySupport makeText_OnlyDrawable(Drawable drawable){
		View v=LayoutInflater.from(con).inflate(R.layout.toast,null);
		ImageView i=(ImageView) v.findViewById(R.id.tips_icon);
		i.setBackground(drawable);
		v.getBackground().setAlpha(128);
		ShortToastFactorySupport t=new ShortToastFactorySupport(con);
		t.setView(v);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		return t;
	}
	public double gettoasty()
	{
		WindowManager w=(WindowManager) con.getSystemService(con.WINDOW_SERVICE);
		int h=w.getDefaultDisplay().getHeight();
		double toasthight=h*0.07241875;
		return toasthight;
	}
	
}
