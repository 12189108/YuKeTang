package Support;
import Support.*;
import android.content.*;
import android.widget.*;
public class TextViews extends TextView
{
	public TextViews(android.content.Context context) {super(context);v1(this,context);}

	public TextViews(android.content.Context context, android.util.AttributeSet attrs) {super(context,attrs);v1(this,context);}

	public TextViews(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {super(context,attrs,defStyleAttr);v1(this,context);}

	public TextViews(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{super(context, attrs, defStyleAttr, defStyleRes);v1(this,context);}
	private void v1(TextView b,Context c){
		b.setText(MD5Support.getString(c,ByteTransformSupport.Base64Decode(b.getText().toString())));
	}
}
