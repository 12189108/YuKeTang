package Support;
import android.content.*;
import android.view.*;
import android.widget.*;
import waste.time.yuketang.R;


public class HightToastSupport extends Toast
{
	private Context ccc;
	public HightToastSupport(Context c){
		super(c);
		ccc=c;
		//new SystemServiceSupport(c).toKill("7cf3fe6da768e952a527523511a14132");
	}
	public HightToastSupport makeText(int drwaid,int showwhat){
		View view=LayoutInflater.from(ccc).inflate(R.layout.to_top, null);
		ImageView i=(ImageView) view.findViewById(R.id.totopImageView1);
		TextView tv=(TextView) view.findViewById(R.id.totopTextView1);
		i.setBackgroundResource(drwaid);
		tv.setText(showwhat);
		HightToastSupport t=new HightToastSupport(ccc);
		t.setView(view);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		t.getView().getBackground().setAlpha(128);
		return t;
	}
	public HightToastSupport makeText(int drwaid,String showwhat){
		View view=LayoutInflater.from(ccc).inflate(R.layout.to_top, null);
		ImageView i=(ImageView) view.findViewById(R.id.totopImageView1);
		TextView tv=(TextView) view.findViewById(R.id.totopTextView1);
		i.setBackgroundResource(drwaid);
		tv.setText(showwhat);
		HightToastSupport t=new HightToastSupport(ccc);
		t.setView(view);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		t.getView().getBackground().setAlpha(128);
		return t;
	}
	public HightToastSupport makeText(int showwhat){
		View view=LayoutInflater.from(ccc).inflate(R.layout.to_top, null);
		ImageView i=(ImageView) view.findViewById(R.id.totopImageView1);
		i.setVisibility(View.GONE);
		TextView tv=(TextView) view.findViewById(R.id.totopTextView1);
		tv.setText(showwhat);
		HightToastSupport t=new HightToastSupport(ccc);
		t.setView(view);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		t.getView().getBackground().setAlpha(128);
		return t;
	}
	public HightToastSupport makeText(String showwhat){
		View view=LayoutInflater.from(ccc).inflate(R.layout.to_top, null);
		ImageView i=(ImageView) view.findViewById(R.id.totopImageView1);
		i.setVisibility(View.GONE);
		TextView tv=(TextView) view.findViewById(R.id.totopTextView1);
		tv.setText(showwhat);
		WindowManager w=(WindowManager) ccc.getSystemService(ccc.WINDOW_SERVICE);
		int d=w.getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(d,ViewGroup.LayoutParams.WRAP_CONTENT);
		p.leftMargin=17;
	    p.rightMargin=17;
		HightToastSupport t=new HightToastSupport(ccc);
		t.setView(view);
		t.setDuration(Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP|Gravity.CENTER,0,(int)gettoasty());
		t.getView().getBackground().setAlpha(128);
		return t;
	}
	private double gettoasty(){
		WindowManager w=(WindowManager) ccc.getSystemService(ccc.WINDOW_SERVICE);
		int h=w.getDefaultDisplay().getHeight();
		double toasthight=h*0.07421875;
		return toasthight;
	}
}
