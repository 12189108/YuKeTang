package Support;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
//import 你的app.包名
import android.util.*;

import android.widget.AdapterView.*;
import waste.time.yuketang.R;

import java.util.*;

public class DialogFactorySupport extends ClassSupport
{
	//警告！此类存在问题:error会报NullPointerException错误，原因:oError未注册，如遇此类问题，请自行解决！！！！！！
	//警告！此类存在问题:error会报NullPointerException错误，原因:oError未注册，如遇此类问题，请自行解决！！！！！！
	//警告！此类存在问题:error会报NullPointerException错误，原因:oError未注册，如遇此类问题，请自行解决！！！！！！
	//警告！此类存在问题:error会报NullPointerException错误，原因:oError未注册，如遇此类问题，请自行解决！！！！！！
	//警告！此类存在问题:error会报NullPointerException错误，原因:oError未注册，如遇此类问题，请自行解决！！！！！！
	//警告！此类存在问题:error会报NullPointerException错误，原因:oError未注册，如遇此类问题，请自行解决！！！！！！
	//警告！此类存在问题:error会报NullPointerException错误，原因:oError未注册，如遇此类问题，请自行解决！！！！！！
	//警告！此类存在问题:error会报NullPointerException错误，原因:oError未注册，如遇此类问题，请自行解决！！！！！！
	//可在catch后加finish();以减小用户体验不适。否则无法关闭软件！
	private Context con;
	private boolean isservice=false;
	private TextView tv;

	private void a(AlertDialog.Builder a){
		a.create().getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	}
	public DialogFactorySupport(Context c){
		Log.i(getClass().toString(),"isuses!");
		con=c;
		 	}
	public AlertDialog SimpleDialog (String Title,String message,String Button1,String Button2,View.OnClickListener c1,View.OnClickListener c2,boolean candis){
		if(Title.equals("")||message.equals("")||Button1.equals("")||Button2.equals("")){
			Log.e("fail", "unopendialog");
		}
		else{
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
		TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
		TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
		Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
		Button bnright=(Button) view.findViewById(R.id.dialogButton2);
		TitleString.setText(Title);
		MessaeString.setText(message);
		bnleft.setText(Button1);
		bnright.setText(Button2);
		a.setView(view);
		a.setCancelable(candis);
		if(isservice){a(a);}
		AlertDialog aa=a.show();
		if (c1 == null)
		{
			bnleft.setOnClickListener(new dismiss(aa));
				}
		else{
			bnleft.setOnClickListener(c1);
			}
		if(c2==null){
			bnright.setOnClickListener(new dismiss(aa));
		}
		else{
			bnright.setOnClickListener(c2);
		}
		return aa;}
		return null;
	}
	public AlertDialog SimpleDialog (String Title,String message,String Button1,String Button2,View.OnClickListener c1,View.OnClickListener c2,boolean candis,boolean[] b){
		if(Title.equals("")||message.equals("")||Button1.equals("")||Button2.equals("")){
			Log.e("fail", "unopendialog");
		}
		else{
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			bnleft.setEnabled(b[0]);
			bnright.setEnabled(b[1]);
			TitleString.setText(Title);
			MessaeString.setText(message);
			bnleft.setText(Button1);
			bnright.setText(Button2);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if (c1 == null)
			{
				bnleft.setOnClickListener(new dismiss(aa));
			}
			else{
				bnleft.setOnClickListener(c1);
			}
			if(c2==null){
				bnright.setOnClickListener(new dismiss(aa));
			}
			else{
				bnright.setOnClickListener(c2);
			}
			return aa;}
		return null;
	}

	public AlertDialog SimpleDialog(int Title,int message,String Button1,String Button2,View.OnClickListener c1,View.OnClickListener c2,boolean dis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			TitleString.setText(Title);
			MessaeString.setText(message);
			bnleft.setText(Button1);
			bnright.setText(Button2);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(dis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c1==null){
				bnleft.setOnClickListener(new dismiss(aa));
			}
			else{bnleft.setOnClickListener(c1);}
			if(c2==null){bnright.setOnClickListener(new dismiss(aa));}else{bnright.setOnClickListener(c2);}
			return aa;
		}
		catch (Throwable e)
		{
			if(error!=null)error.onError(e,"SimpleDialog",DialogFactorySupport.class);
			Log.e("SimpleDialog",DialogFactorySupport.class.toString(),e);}
		return null;
	}
	public AlertDialog SimpleDialog_NoText(String Title,String Button1,String Button2,View.OnClickListener c1,View.OnClickListener c2,boolean candis){
		try{
		View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
		TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
		TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
		Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
		Button bnright=(Button) view.findViewById(R.id.dialogButton2);
	//	MessaeString.setVisibility(View.GONE);
		ImageView i=(ImageView) view.findViewById(R.id.dialogImageView2);
		LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
		l.topMargin=0;
		i.setLayoutParams(l);
		MessaeString.setVisibility(View.GONE);
		TitleString.setText(Title);
		bnleft.setText(Button1);
		bnright.setText(Button2);
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		a.setView(view);
		a.setCancelable(candis);
		if(isservice){a(a);}
		AlertDialog aa=a.show();
		if(c1==null){bnleft.setOnClickListener(new dismiss(aa));}
		else{bnleft.setOnClickListener(c1);}
		if(c2==null){bnright.setOnClickListener(new dismiss(aa));}
		else{bnright.setOnClickListener(c2);}
		return aa;}
		catch(Throwable e){
			if(error!=null)error.onError(e,"SimpleDialog_NoText",DialogFactorySupport.class);
			Log.e("SimpleDialog_NoText",DialogFactorySupport.class.toString());
			return null;
		}
	}

	public AlertDialog SimpleDialog_NoText(int Title,int Button1,int Button2,View.OnClickListener c1,View.OnClickListener c2,boolean candis){
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView2);
			LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
			l.topMargin=0;
			i.setLayoutParams(l);
			MessaeString.setVisibility(View.GONE);
			TitleString.setText(Title);
			bnleft.setText(Button1);
			bnright.setText(Button2);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c1==null){bnleft.setOnClickListener(new dismiss(aa));}
			else{bnleft.setOnClickListener(c1);}
			if(c2==null){bnright.setOnClickListener(new dismiss(aa));}
			else{bnright.setOnClickListener(c2);}
			return aa;}
		catch(Throwable e){
			if(error!=null)error.onError(e,"SimpleDialog_NoText",DialogFactorySupport.class);
			return null;
		}
	}

	public AlertDialog SimpleDialog__NoTitle(String Message,String Button1,String Button2,View.OnClickListener c1,View.OnClickListener c2,boolean candis) {
	try{
		View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
		TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
		TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
		Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
		Button bnright=(Button) view.findViewById(R.id.dialogButton2);
		ImageView i=(ImageView) view.findViewById(R.id.dialogImageView1);
		LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
		l.topMargin=0;
		l.bottomMargin=0;
		TitleString.setLayoutParams(l);
		TitleString.setVisibility(View.GONE);
		i.setVisibility(View.GONE);
		MessaeString.setText(Message);
		bnleft.setText(Button1);
		bnright.setText(Button2);
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		a.setView(view);
		a.setCancelable(candis);
		if(isservice){a(a);}
		AlertDialog aa=a.show();
		if(c1==null){
			bnleft.setOnClickListener(new dismiss(aa));
		}else{bnleft.setOnClickListener(c1);}
		if(c2==null){bnright.setOnClickListener(new dismiss(aa));}
		else{bnright.setOnClickListener(c2);}
		return aa;}catch(Throwable e){
			if(error!=null)error.onError(e,"SimpleDialog__NoTitle",DialogFactorySupport.class);
			Log.e("SimpleDialog_No",DialogFactorySupport.class.toString(),e);
		}
		return null;
	}
	public AlertDialog SimpleDialog__NoTitle(int Message,int Button1,int Button2,View.OnClickListener c1,View.OnClickListener c2,boolean candis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView1);
			LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
			l.topMargin=0;
			l.bottomMargin=0;
			TitleString.setLayoutParams(l);
			TitleString.setVisibility(View.GONE);
			i.setVisibility(View.GONE);
			MessaeString.setText(Message);
			bnleft.setText(Button1);
			bnright.setText(Button2);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c1==null){
				bnleft.setOnClickListener(new dismiss(aa));
			}else{bnleft.setOnClickListener(c1);}
			if(c2==null){bnright.setOnClickListener(new dismiss(aa));}
			else{bnright.setOnClickListener(c2);}
			return aa;}catch(Throwable e){
				if(error!=null)error.onError(e,"SimpleDialog_NoTitle",DialogFactorySupport.class);
				Log.e("SimpleDialog_NoTitle",DialogFactorySupport.class.toString(),e);
		}
		return null;
	}
	public AlertDialog SimpleDialog_Only_OneButton(String Title,String Message,String Button1,View.OnClickListener c,boolean candis) {
		try{View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
		TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
		TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
		Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
		Button bnright=(Button) view.findViewById(R.id.dialogButton2);
		ImageView i=(ImageView) view.findViewById(R.id.dialogImageView3);
		i.setVisibility(View.GONE);
		bnright.setVisibility(View.GONE);
		TitleString.setText(Title);
		MessaeString.setText(Message);
		bnleft.setText(Button1);
		bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only));
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		a.setView(view);
		a.setCancelable(candis);
		if(isservice){a(a);}
		AlertDialog aa=a.show();
		if(c==null){bnleft.setOnClickListener(new dismiss(aa));}else{bnleft.setOnClickListener(c);}
			return aa;}catch(Throwable e){if(error!=null)error.onError(e,"SimpleDialog_Noly_OneButton",DialogFactorySupport.class);
			Log.e("SimpleDialog",DialogFactorySupport.class.toString(),e);
		}
		return null;
	}
	public AlertDialog SimpleDialog_NoTitle_Only_OneButton(int Message,int Button1,View.OnClickListener c,boolean candis) {
		try{View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView3);
			LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
			l.topMargin=0;
			l.bottomMargin=0;
			TitleString.setLayoutParams(l);
			TitleString.setVisibility(View.GONE);
			i.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			MessaeString.setText(Message);
			bnleft.setText(Button1);
			bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only));
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c==null){bnleft.setOnClickListener(new dismiss(aa));}else{bnleft.setOnClickListener(c);}
			return aa;}catch(Throwable e){error.onError(e,"SimpleDialog_Only_OneButton",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_Only_OneButton(int Title,int Message,int Button1,View.OnClickListener c,boolean candis) {
		try{View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView3);
			i.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			TitleString.setText(Title);
			MessaeString.setText(Message);
			bnleft.setText(Button1);
			bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only));
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c==null){bnleft.setOnClickListener(new dismiss(aa));}else{bnleft.setOnClickListener(c);}
			return aa;}catch(Throwable e){error.onError(e,"SimpleDialog_Only_OneButton",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_Notitle_NoText(String Button1,String Button2,View.OnClickListener c1,View.OnClickListener c2,boolean candis) {
		try{View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView1);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView2);
			LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
			l.topMargin=0;
			l.bottomMargin=0;
			TitleString.setLayoutParams(l);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
			ll.topMargin=0;
			ll.bottomMargin=0;
			ii.setLayoutParams(ll);
			TitleString.setVisibility(View.GONE);
			MessaeString.setVisibility(View.GONE);
			i.setVisibility(View.GONE);
			bnleft.setText(Button1);
			bnright.setText(Button2);
			bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only_2_left));
			bnright.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only_2_right));
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c1==null){
				bnleft.setOnClickListener(new dismiss(aa));
			}else{bnleft.setOnClickListener(c1);}
			if(c2==null){bnright.setOnClickListener(new dismiss(aa));}else{bnright.setOnClickListener(c2);}
			return aa;
		}
		catch (Throwable e)
		{
}
		return null;
	}

	public AlertDialog SimpleDialog_Notitle_NoText(int Button1,int Button2,View.OnClickListener c1,View.OnClickListener c2,boolean candis) {
		try{View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView1);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView2);
			LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
			l.topMargin=0;
			l.bottomMargin=0;
			TitleString.setLayoutParams(l);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
			ll.topMargin=0;
			ll.bottomMargin=0;
			ii.setLayoutParams(ll);
			TitleString.setVisibility(View.GONE);
			MessaeString.setVisibility(View.GONE);
			i.setVisibility(View.GONE);
			bnleft.setText(Button1);
			bnright.setText(Button2);
			bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only_2_left));
			bnright.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only_2_right));
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c1==null){
				bnleft.setOnClickListener(new dismiss(aa));
			}else{bnleft.setOnClickListener(c1);}
			if(c2==null){bnright.setOnClickListener(new dismiss(aa));}else{bnright.setOnClickListener(c2);}
			return aa;
		}
		catch(Throwable e)
		{

}
		return null;
	}

	public AlertDialog SimpleDialog_NoText_NoTitle_OneButton(String Button,View.OnClickListener c,boolean candis) {
		try{View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
		TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
		TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
		Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
		Button bnright=(Button) view.findViewById(R.id.dialogButton2);
		ImageView i=(ImageView) view.findViewById(R.id.dialogImageView1);
		ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView2);
		ImageView iii=(ImageView) view.findViewById(R.id.dialogImageView3);
		LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
		l.topMargin=0;
		l.bottomMargin=0;
		TitleString.setLayoutParams(l);
		LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
		ll.topMargin=0;
		ll.bottomMargin=0;
		ii.setLayoutParams(ll);
		TitleString.setVisibility(View.GONE);
		MessaeString.setVisibility(View.GONE);
		i.setVisibility(View.GONE);
		bnright.setVisibility(View.GONE);
		iii.setVisibility(View.GONE);
		bnleft.setText(Button);
		bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only_3));
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		a.setCancelable(candis);
		a.setView(view);
		if(isservice){a(a);}
		AlertDialog aa=a.show();
			if(c==null){bnleft.setOnClickListener(new dismiss(aa));}else{bnleft.setOnClickListener(c);}return aa;}catch(Throwable e){error.onError(e,"SimpleDialog_NoText_NoTitle_OneButton",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_NoText_NoTitle_OneButton(int Button,View.OnClickListener c,boolean candis) {
		try{View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView1);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView2);
			ImageView iii=(ImageView) view.findViewById(R.id.dialogImageView3);
			LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
			l.topMargin=0;
			l.bottomMargin=0;
			TitleString.setLayoutParams(l);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
			ll.topMargin=0;
			ll.bottomMargin=0;
			ii.setLayoutParams(ll);
			TitleString.setVisibility(View.GONE);
			MessaeString.setVisibility(View.GONE);
			i.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			iii.setVisibility(View.GONE);
			bnleft.setText(Button);
			bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only_3));
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setCancelable(candis);
			a.setView(view);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c==null){bnleft.setOnClickListener(new dismiss(aa));}else{bnleft.setOnClickListener(c);}return aa;}catch(Throwable e){error.onError(e,"SimpleDialog_NoText_NoTitle_OneButton",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_NoButton(String Title,String Message,boolean candis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView2);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView3);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(0,0);
			ll.topMargin=0;
			ll.bottomMargin=0;
			i.setLayoutParams(ll);
			i.setVisibility(View.GONE);
			ii.setVisibility(View.GONE);
			bnleft.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			TitleString.setText(Title);
			MessaeString.setText(Message);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			return a.show();
		}
		catch(Throwable e)
		{error.onError(e,"SimpleDialog_NoButton",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_NoButton(int Title,int Message,boolean candis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView2);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView3);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(0,0);
			ll.topMargin=0;
			ll.bottomMargin=0;
			i.setLayoutParams(ll);
			i.setVisibility(View.GONE);
			ii.setVisibility(View.GONE);
			bnleft.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			TitleString.setText(Title);
			MessaeString.setText(Message);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			return a.show();
		}
		catch(Throwable e)
		{error.onError(e,"SimpleDialog_NoButton",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_OnlyText(String Message,boolean candis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView2);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView3);
			ImageView iii=(ImageView) view.findViewById(R.id.dialogImageView1);
			iii.setVisibility(View.GONE);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(0,0);
			ll.topMargin=0;
			ll.bottomMargin=0;
			i.setLayoutParams(ll);
			i.setVisibility(View.GONE);
			ii.setVisibility(View.GONE);
			bnleft.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			MessaeString.setText(Message);
			TitleString.setLayoutParams(ll);
			TitleString.setVisibility(View.GONE);
			LinearLayout.LayoutParams lll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			lll.leftMargin=20;
			lll.topMargin=20;
			lll.bottomMargin=0;
			MessaeString.setLayoutParams(lll);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			return a.show();
		}
		catch(Throwable e)
		{error.onError(e,"SimpleDialog_OnlyText",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_OnlyText(int Message,boolean candis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView2);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView3);
			ImageView iii=(ImageView) view.findViewById(R.id.dialogImageView1);
			iii.setVisibility(View.GONE);
			LinearLayout.LayoutParams lll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			lll.leftMargin=20;
			lll.topMargin=20;
			lll.bottomMargin=0;
			MessaeString.setLayoutParams(lll);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(0,0);
			ll.topMargin=0;
			ll.bottomMargin=0;
			i.setLayoutParams(ll);
			i.setVisibility(View.GONE);
			ii.setVisibility(View.GONE);
			bnleft.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			MessaeString.setText(Message);
			TitleString.setLayoutParams(ll);
			TitleString.setVisibility(View.GONE);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			return a.show();
		}
		catch(Throwable e)
		{error.onError(e,"SimpleDialog_OnlyText",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_OnlyTitle(String Title,boolean candis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView2);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView3);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(0,0);
			ll.topMargin=0;
			ll.bottomMargin=0;
			i.setLayoutParams(ll);
			i.setVisibility(View.GONE);
			ii.setVisibility(View.GONE);
			bnleft.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
	      	TitleString.setText(Title);
			MessaeString.setLayoutParams(ll);
			MessaeString.setVisibility(View.GONE);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			return a.show();
		}
		catch(Throwable e)
		{error.onError(e,"SimpleDialog_OnlyTitle",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_OnlyTitle(int Title,boolean candis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView2);
			ImageView ii=(ImageView) view.findViewById(R.id.dialogImageView3);
			LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(0,0);
			ll.topMargin=0;
			ll.bottomMargin=0;
			i.setLayoutParams(ll);
			i.setVisibility(View.GONE);
			ii.setVisibility(View.GONE);
			bnleft.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
	      	TitleString.setText(Title);
			MessaeString.setLayoutParams(ll);
			MessaeString.setVisibility(View.GONE);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(view);
			a.setCancelable(candis);
			if(isservice){a(a);}
			return a.show();
		}
		catch(Throwable e)
		{error.onError(e,"SimpleDialog_OnlyTitle",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_NoTitle_OnlyOneButton(String Message,String Button,View.OnClickListener c,boolean candis) {
		try{
			View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView1);
			ImageView iii=(ImageView) view.findViewById(R.id.dialogImageView3);
			LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
			l.topMargin=0;
			l.bottomMargin=0;
			TitleString.setLayoutParams(l);
			TitleString.setVisibility(View.GONE);
			MessaeString.setText(Message);
			i.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			iii.setVisibility(View.GONE);
			bnleft.setText(Button);
			bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only_3));
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setCancelable(candis);
			a.setView(view);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c==null){bnleft.setOnClickListener(new dismiss(aa));}else{bnleft.setOnClickListener(c);}return aa;}catch(Throwable e){error.onError(e,"SimpleDialog_NoTitle_OnlyOneButton",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_NoTitle_OnlyOneButton(int Message,int Button,View.OnClickListener c,boolean candis) {
		try{View view=LayoutInflater.from(con).inflate(R.layout.dialog, null);
			TextView TitleString=(TextView) view.findViewById(R.id.dialogTextView1);
			TextView MessaeString=(TextView) view.findViewById(R.id.dialogTextView2);
			Button bnleft=(Button) view.findViewById(R.id.dialogButton1);
			Button bnright=(Button) view.findViewById(R.id.dialogButton2);
			ImageView i=(ImageView) view.findViewById(R.id.dialogImageView1);
			ImageView iii=(ImageView) view.findViewById(R.id.dialogImageView3);
			LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(0, 0);
			l.topMargin=0;
			l.bottomMargin=0;
			TitleString.setLayoutParams(l);
			TitleString.setVisibility(View.GONE);
			MessaeString.setText(Message);
			i.setVisibility(View.GONE);
			bnright.setVisibility(View.GONE);
			iii.setVisibility(View.GONE);
			bnleft.setText(Button);
			bnleft.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.only_3));
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setCancelable(candis);
			a.setView(view);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			if(c==null){bnleft.setOnClickListener(new dismiss(aa));}else{bnleft.setOnClickListener(c);}return aa;}catch(Throwable e){error.onError(e,"SimpleDialog_NoTitle_OnlyOneButton",DialogFactorySupport.class);}
		return null;
	}

	public AlertDialog SimpleDialog_NoAll(boolean b){
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		a.setCancelable(b);
		return a.show();
	}
	public AlertDialog SimpleDialog_List(String[] s,String Title,OnItemClickListener d,boolean candis) {
		try{View v=LayoutInflater.from(con).inflate(R.layout.dialog_list,null);
		TextView tv=(TextView) v.findViewById(R.id.dialoglistTextView1);
		ListView lv=(ListView) v.findViewById(R.id.dialoglistListView1);
		tv.setText(Title);
		ArrayList<HashMap<String, Object>> listitem=new ArrayList<HashMap<String,Object>>();
		SimpleAdapter simple = new SimpleAdapter(con, listitem, R.layout.dialog_list_adper, new String[]{"text"}, new int[]{R.id.dialoglistadperTextView1});
		lv.setAdapter(simple);
		for(int i=0;i<s.length;++i){
			HashMap<String, Object> map=new HashMap<String,Object>();
			map.put("text",s[i]);
			listitem.add(map);
		}
		simple.notifyDataSetChanged();
		lv.setOnItemClickListener(d);
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		a.setView(v);
		a.setCancelable(candis);
		if(isservice){a(a);}
		AlertDialog aa=a.show();
		return aa;
		}
		catch(Throwable e)
{
	error.onError(e,"SimpleDialog_NoAll",DialogFactorySupport.class);
		}
		return null;
	}
	public AlertDialog SimpleDialog_List(String[] s,int Title,OnItemClickListener d,boolean candis) {
		try{View v=LayoutInflater.from(con).inflate(R.layout.dialog_list,null);
			TextView tv=(TextView) v.findViewById(R.id.dialoglistTextView1);
			ListView lv=(ListView) v.findViewById(R.id.dialoglistListView1);
			tv.setText(Title);
			ArrayList<HashMap<String, Object>> listitem=new ArrayList<HashMap<String,Object>>();
			SimpleAdapter simple = new SimpleAdapter(con, listitem, R.layout.dialog_list_adper, new String[]{"text"}, new int[]{R.id.dialoglistadperTextView1});
			lv.setAdapter(simple);
			for(int i=0;i<s.length;++i){
			HashMap<String, Object> map=new HashMap<String,Object>();
			map.put("text",s[i]);
			listitem.add(map);
		}
		simple.notifyDataSetChanged();
			lv.setOnItemClickListener(d);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(v);
			a.setCancelable(candis);
			if(isservice){a(a);}
			final AlertDialog aa=a.show();
			return aa;
		}
		catch(Throwable e){
			error.onError(e,"SimpleDialog_List",DialogFactorySupport.class);
		}
		return null;
	}

	public AlertDialog SimpleDialog_List_NoTitle(String[] s,OnItemClickListener d,boolean candis) {
		try{View v=LayoutInflater.from(con).inflate(R.layout.dialog_list,null);
			LinearLayout ly=(LinearLayout) v.findViewById(R.id.dialoglistLinearLayout1);
			ListView lv=(ListView) v.findViewById(R.id.dialoglistListView1);
			ly.removeViewsInLayout(0,2);
			ArrayList<HashMap<String, Object>> listitem=new ArrayList<HashMap<String,Object>>();
			SimpleAdapter simple = new SimpleAdapter(con, listitem, R.layout.dialog_list_adper, new String[]{"text"}, new int[]{R.id.dialoglistadperTextView1});
			lv.setAdapter(simple);
			for(int i=0;i<s.length;++i){
				HashMap<String, Object> map=new HashMap<String,Object>();
				map.put("text",s[i]);
				listitem.add(map);
			}
			simple.notifyDataSetChanged();
			lv.setOnItemClickListener(d);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(v);
			a.setCancelable(candis);
			if(isservice){a(a);}
			final AlertDialog aa=a.show();
			return aa;
		}
		catch(Throwable e){
			error.onError(e,"SimpleDialog_List_NoTitle",DialogFactorySupport.class);
		}
		return null;
	}

	public AlertDialog SimpleDialog_ListWhitDrawable(String[] s,String Title,int[] drawable,OnItemClickListener d,boolean candis) {
		try{View v=LayoutInflater.from(con).inflate(R.layout.dialog_list,null);
			TextView tv=(TextView) v.findViewById(R.id.dialoglistTextView1);
			ListView lv=(ListView) v.findViewById(R.id.dialoglistListView1);
			tv.setText(Title);
			ArrayList<HashMap<String, Object>> listitem=new ArrayList<HashMap<String,Object>>();
			SimpleAdapter simple = new SimpleAdapter(con, listitem, R.layout.dialog_list_drawable, new String[]{"text","drawable"}, new int[]{R.id.dialoglistdrawableTextView1,R.id.dialoglistdrawableImageView1});
			lv.setAdapter(simple);
			for(int i=0;i<s.length;++i){
				HashMap<String, Object> map=new HashMap<String,Object>();
				map.put("text",s[i]);
				map.put("drawable",drawable[i]);
				listitem.add(map);
			}
			simple.notifyDataSetChanged();
			lv.setOnItemClickListener(d);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(v);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			return aa;
		}
		catch(Throwable e)
		{
			error.onError(e,"SimpleDialog_ListWhitDrawable",DialogFactorySupport.class);return null;
		}
	}

	public AlertDialog SimpleDialog_ListWhitDrawable(int[] s,int Title,int[] drawable,OnItemClickListener d,boolean candis) {
		try{View v=LayoutInflater.from(con).inflate(R.layout.dialog_list,null);
			TextView tv=(TextView) v.findViewById(R.id.dialoglistTextView1);
			ListView lv=(ListView) v.findViewById(R.id.dialoglistListView1);
			tv.setText(Title);
			ArrayList<HashMap<String, Object>> listitem=new ArrayList<HashMap<String,Object>>();
			SimpleAdapter simple = new SimpleAdapter(con, listitem, R.layout.dialog_list_drawable, new String[]{"text","drawable"}, new int[]{R.id.dialoglistdrawableTextView1,R.id.dialoglistdrawableImageView1});
			lv.setAdapter(simple);
			for(int i=0;i<s.length;++i){
				HashMap<String, Object> map=new HashMap<String,Object>();
				map.put("text",con.getString(s[i]));
				map.put("drawable",drawable[i]);
				listitem.add(map);
			}
			simple.notifyDataSetChanged();
			lv.setOnItemClickListener(d);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(v);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			return aa;
		}
		catch(Throwable e)
		{
			error.onError(e,"SimpleDialog_ListWhitDrawable",DialogFactorySupport.class);return null;
		}
	}

	public AlertDialog SimpleDialog_ListWhitDrawable_NoTitle(String[] s,int[] drawable,OnItemClickListener d,boolean candis) {
		try{View v=LayoutInflater.from(con).inflate(R.layout.dialog_list,null);
			ListView lv=(ListView) v.findViewById(R.id.dialoglistListView1);
			lv.removeViewsInLayout(0,2);
			ArrayList<HashMap<String, Object>> listitem=new ArrayList<HashMap<String,Object>>();
			SimpleAdapter simple = new SimpleAdapter(con, listitem, R.layout.dialog_list_drawable, new String[]{"text","drawable"}, new int[]{R.id.dialoglistdrawableTextView1,R.id.dialoglistdrawableImageView1});
			lv.setAdapter(simple);
			for(int i=0;i<s.length;++i){
				HashMap<String, Object> map=new HashMap<String,Object>();
				map.put("text",s[i]);
				map.put("drawable",drawable[i]);
				listitem.add(map);
			}
			simple.notifyDataSetChanged();
			lv.setOnItemClickListener(d);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(v);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			return aa;
		}
		catch(Throwable e)
		{
			error.onError(e,"SimpleDialog_ListWhitDrawable_NoTitle",DialogFactorySupport.class);return null;
		}
	}

	public AlertDialog SimpleDialog_ListWhitDrawable_NoTitle(int[] s,int[] drawable,OnItemClickListener d,boolean candis) {
		try{View v=LayoutInflater.from(con).inflate(R.layout.dialog_list,null);
			ListView lv=(ListView) v.findViewById(R.id.dialoglistListView1);
			lv.removeViewsInLayout(0,2);
			ArrayList<HashMap<String, Object>> listitem=new ArrayList<HashMap<String,Object>>();
			SimpleAdapter simple = new SimpleAdapter(con, listitem, R.layout.dialog_list_drawable, new String[]{"text","drawable"}, new int[]{R.id.dialoglistdrawableTextView1,R.id.dialoglistdrawableImageView1});
			lv.setAdapter(simple);
			for(int i=0;i<s.length;++i){
				HashMap<String, Object> map=new HashMap<String,Object>();
				map.put("text",con.getString(s[i]));
				map.put("drawable",drawable[i]);
				listitem.add(map);
			}
			simple.notifyDataSetChanged();
			lv.setOnItemClickListener(d);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(v);
			a.setCancelable(candis);
			if(isservice){a(a);}
			AlertDialog aa=a.show();
			return aa;
		}
		catch(Throwable e)
		{
			//new SystemServiceSupport(con).CopytoSystem(e.toString());
			error.onError(e,"SimpleDialog_ListWhitDrawable_NoTitle",DialogFactorySupport.class);return null;
		}
	}

	public AlertDialog SimpleProgressDialog(String Message,boolean candis) {
		try{
		View v=LayoutInflater.from(con).inflate(R.layout.dialog_progress,null);
		ImageView im=(ImageView) v.findViewById(R.id.dialogprogressImageView1);
		tv=(TextView) v.findViewById(R.id.dialogprogressTextView1);
		im.startAnimation(AnimSupport.rotate(360,1000,true,true));
		tv.setText(Message);
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		a.setView(v);
		a.setCancelable(candis);
		if(isservice){a(a);}
		return a.show();
		}catch(Throwable e){
			error.onError(e,"SimpleProgressDialog",DialogFactorySupport.class);return null;
		}
	}

	public AlertDialog SimpleProgressDialog(int Message,boolean candis) {
		try{
			View v=LayoutInflater.from(con).inflate(R.layout.dialog_progress,null);
			ImageView im=(ImageView) v.findViewById(R.id.dialogprogressImageView1);
			TextView tv=(TextView) v.findViewById(R.id.dialogprogressTextView1);
			im.startAnimation(AnimSupport.rotate(360,1000,true,true));
			tv.setText(Message);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(v);
			a.setCancelable(candis);
			if(isservice){a(a);}
			return a.show();
		}catch(Throwable e){
			error.onError(e,"SimpleProgressDialog",DialogFactorySupport.class);return null;
		}
	}

	public AlertDialog SimpleProgressDialog_NoMessage(boolean candis) {
		try{
			View v=LayoutInflater.from(con).inflate(R.layout.dialog_progress,null);
			ImageView im=(ImageView) v.findViewById(R.id.dialogprogressImageView1);
			TextView tv=(TextView) v.findViewById(R.id.dialogprogressTextView1);
			im.startAnimation(AnimSupport.rotate(360,1000,true,true));
			tv.setVisibility(View.GONE);
			AlertDialog.Builder a=new AlertDialog.Builder(con);
			a.setView(v);
			a.setCancelable(candis);
			if(isservice){a(a);}
			return a.show();
		}catch(Throwable e){
			error.onError(e,"SimpleProgressDialog_NoMessage",DialogFactorySupport.class);return null;
		}
	}
	//为适应功能需要，临时修改
	public void changeText(String newText){
		tv.setText(newText);
	}
	private class dismiss implements View.OnClickListener
	{
		private AlertDialog aa;
		public dismiss(AlertDialog a){
	aa=a;
}
		@Override
		public void onClick(View p1)
		{
			aa.dismiss();
			// TODO: Implement this method
		}
	}
	public void setservicemode(){
		isservice=true;
	}
}
