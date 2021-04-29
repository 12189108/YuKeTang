package Support;

import android.content.*;
import android.widget.*;

public class Buttons extends Button
{
	public Buttons(android.content.Context context) {super(context);v0(this,context);}
	public Buttons(android.content.Context context, android.util.AttributeSet attrs) {super(context,attrs);v0(this,context);} 
	public Buttons(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {super(context,attrs,defStyleAttr);v0(this,context);}
	public Buttons(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{super(context, attrs, defStyleAttr, defStyleRes);v0(this,context);}
	private void v0(Button b,Context c){
		b.setText(MD5Support.getString(c,ByteTransformSupport.Base64Decode(b.getText().toString())));
	}
}
