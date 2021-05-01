package waste.time.yuketang;
import android.graphics.drawable.*;

public class Course_data
{
	private String Course_name,Progress;
	private boolean complete;
	private Drawable state;
	private int type;
	public Course_data(String Course_name,String Progress,boolean complete,int type,Drawable sate){
		this.Course_name=Course_name;
		this.Progress=Progress;
		this.complete=complete;
		this.type=type;
		this.state=sate;
	}
	public String getCourse_name(){
		return Course_name+"("+CheckType(type)+")";
	}
	public Drawable getDrawable(){
		return state;
	}
	public String getCompleteState(){
		return complete?"已完成":"未完成";
	}
	public String getProgress(){
		return Progress;
	}
	private String CheckType(int mtype){
		switch(mtype)
		{
			case 0:
				return "视频";
			case 3:
				return "推荐";
			case 4:
				return "讨论";
			case 5:
				return "考试";
			case 6:
			return "作业";
		}
		return "其他";
	}
}
