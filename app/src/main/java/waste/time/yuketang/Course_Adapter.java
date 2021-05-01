package waste.time.yuketang;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class Course_Adapter extends ArrayAdapter<Course_data>
{
	public Course_Adapter(Context context, int resourceId, List<Course_data> Course_data){
        super(context, resourceId, Course_data);
        
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		return super.getView(position, convertView, parent);
	}
	
}
