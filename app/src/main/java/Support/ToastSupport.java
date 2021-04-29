package Support;
import android.content.*;
import android.view.*;
import android.widget.*;
import waste.time.yuketang.R;

public class ToastSupport
{
	private Context con;
	public ToastSupport(Context c){
		con=c;
			}
    
	public void commonToast_short(String showwhat){
		Toast.makeText(con,showwhat,Toast.LENGTH_SHORT).show();
	}
	public void commonToast_long(String showwhat){
		Toast.makeText(con,showwhat,Toast.LENGTH_LONG).show();
	}
	public void commonToast_short(int id,String showwhat,int mode){
		if(mode==1){
		Toast.makeText(con,con.getString(id)+showwhat,Toast.LENGTH_SHORT).show();}
		else{
			Toast.makeText(con,showwhat+con.getString(id),Toast.LENGTH_SHORT).show();}
		}
	
	public void commonToast_long(int id,String showwhat,int mode){
		if(mode==1){
		Toast.makeText(con,con.getString(id)+showwhat,Toast.LENGTH_LONG).show();}
		else{
			Toast.makeText(con,showwhat+con.getString(id),Toast.LENGTH_LONG).show();}
		}
	
	
	public void commonToast_customtime(String showwhat,int showtime){
		Toast.makeText(con,showwhat,showtime).show();
	}
	public void Toast_custtomposition_Long(String showwhat,int position){
		Toast t=Toast.makeText(con, showwhat, Toast.LENGTH_LONG);
		t.setGravity(position,0,0);
		t.show();
	}
	public void Toast_custtomposition_short(String showwhat,int position){
		Toast t=Toast.makeText(con, showwhat, Toast.LENGTH_SHORT);
		t.setGravity(position,0,0);
		t.show();
	}
	public void Toast_custtomposition_customtime(String showwhat,int showtime,int position){
		Toast t=Toast.makeText(con, showwhat, showtime);
		t.setGravity(position,0,0);
		t.show();
	}
	public void Toast_custtompostion_customall(String showwhat,int showtime,int position,int X,int Y){
		Toast t=Toast.makeText(con, showwhat, showtime);
		t.setGravity(position,X,Y);
		t.show();
	}
	public void ImageToast(String showwhat,int position,int Showtime,int res){
		Toast t=Toast.makeText(con, showwhat, Showtime);
		t.setGravity(position,0,0);
		LinearLayout toastview=(LinearLayout) t.getView();
		toastview.setOrientation(LinearLayout.HORIZONTAL);
		ImageView i=new ImageView(con);
		i.setImageResource(res);
		toastview.addView(i,0);
		t.show();
	}
	public void commonToast_short(int showwhat){
		Toast.makeText(con,showwhat,Toast.LENGTH_SHORT).show();
	}
	public void commonToast_long(int showwhat){
		Toast.makeText(con,showwhat,Toast.LENGTH_LONG).show();
	}
	public void commonToast_customtime(int showwhat,int showtime){
		Toast.makeText(con,showwhat,showtime).show();
	}
	public void Toast_custtomposition_Long(int showwhat,int position){
		Toast t=Toast.makeText(con, showwhat, Toast.LENGTH_LONG);
		t.setGravity(position,0,0);
		t.show();
	}
	public void Toast_custtomposition_short(int showwhat,int position){
		Toast t=Toast.makeText(con, showwhat, Toast.LENGTH_SHORT);
		t.setGravity(position,0,0);
		t.show();
	}
	public void Toast_custtomposition_customtime(int showwhat,int showtime,int position){
		Toast t=Toast.makeText(con, showwhat, showtime);
		t.setGravity(position,0,0);
		t.show();
	}
	public void Toast_custtompostion_customall(int showwhat,int showtime,int position,int X,int Y){
		Toast t=Toast.makeText(con, showwhat, showtime);
		t.setGravity(position,X,Y);
		t.show();
	}
	public void ImageToast(int showwhat,int position,int Showtime,int res){
		Toast t=Toast.makeText(con, showwhat, Showtime);
		t.setGravity(position,0,0);
		LinearLayout toastview=(LinearLayout) t.getView();
		toastview.setOrientation(LinearLayout.HORIZONTAL);
		ImageView i=new ImageView(con);
		i.setImageResource(res);
		toastview.addView(i,0);
		t.show();
	}
	/*public void toast(int string,int drawable){
		View view=LayoutInflater.from(con).inflate(R.layout.toast, null);
		ImageView i=(ImageView) view.findViewById(R.id.toastImageView1);
		TextView t=(TextView) view.findViewById(R.id.toastTextView1);
		i.setBackgroundResource(drawable);
		t.setText(string);
		Toast toast=new Toast(con);
		toast.setGravity(Gravity.CENTER|Gravity.TOP,0,0);
		toast.setView(view);
	//	toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}*/
	public void TestToast(){
		View v=LayoutInflater.from(con).inflate(R.layout.toast, null);
		v.getBackground().setAlpha(128);
		Toast t=new Toast(con);
		t.setView(v);
		t.setGravity(Gravity.TOP,0,0);
		t.setDuration(5000);
		t.show();
	}
}
