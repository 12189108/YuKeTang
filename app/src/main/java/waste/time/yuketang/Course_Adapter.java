package waste.time.yuketang;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.io.*;

public class Course_Adapter extends ArrayAdapter<Course_data>
{
	private TextView progress,course_name,complete_state;
	private ImageView state_Image;
	public Course_Adapter(Context context, int resourceId, List<Course_data> Course_data){
        super(context, resourceId, Course_data);

    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
			convertView=LayoutInflater.from(getContext()).inflate(R.layout.course_list_adapterview,null);
			state_Image=convertView.findViewById(R.id.compelete_state);
			course_name=convertView.findViewById(R.id.course_name);
			complete_state=convertView.findViewById(R.id.compelete_state_text);
			progress=convertView.findViewById(R.id.progress);
			state_Image.setImageDrawable(getItem(position).getDrawable());
			complete_state.setText(getItem(position).getCompleteState());
			course_name.setText(getItem(position).getCourse_name());
			progress.setText(getItem(position).getProgress());
			if(!getItem(position).isNeedexec()){
				progress.setTextColor(getContext().getResources().getColor(R.color.gray));
				course_name.setTextColor(getContext().getResources().getColor(R.color.gray));
				complete_state.setTextColor(getContext().getResources().getColor(R.color.gray));
			}
		return convertView;
	}

}
