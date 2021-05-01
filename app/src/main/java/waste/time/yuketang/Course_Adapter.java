package waste.time.yuketang;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.io.*;

public class Course_Adapter extends ArrayAdapter<Course_data>
{
	public Course_Adapter(Context context, int resourceId, List<Course_data> Course_data){
        super(context, resourceId, Course_data);
        
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View_Holder View_Holder;
		// TODO: Implement this method
		if(convertView!=null){
			View_Holder=(Course_Adapter.View_Holder) convertView.getTag();
		}else{
			View_Holder=new View_Holder();
			convertView=LayoutInflater.from(getContext()).inflate(R.layout.course_list_adapterView,parent,false);
			View_Holder.state_Image=convertView.findViewById(R.id.compelete_state);
			View_Holder.course_name=convertView.findViewById(R.id.course_name);
			View_Holder.complete_state=convertView.findViewById(R.id.compelete_state_text);
			View_Holder.progress=convertView.findViewById(R.id.progress);
			View_Holder.state_Image.setImageDrawable(getItem(position).getDrawable());
			View_Holder.complete_state.setText(getItem(position).getCompleteState());
			View_Holder.course_name.setText(getItem(position).getCourse_name());
			View_Holder.progress.setText(getItem(position).getProgress());
		}
		return convertView;
	}
	private class View_Holder{
		public ImageView state_Image;
		public TextView course_name,complete_state,progress;
	}
}
