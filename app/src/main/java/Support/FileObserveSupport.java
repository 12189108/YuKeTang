package Support;
import android.content.*;
import android.os.*;

public class FileObserveSupport extends FileObserver
{
	private Context con;
	/*
	 所能监听的事件类型如下：
	 ACCESS，即文件被访问
	 MODIFY，文件被 修改
	 ATTRIB，文件属性被修改，如 chmod、chown、touch 等
	 CLOSE_WRITE，可写文件被 close
	 CLOSE_NOWRITE，不可写文件被 close
	 OPEN，文件被 open
	 MOVED_FROM，文件被移走,如 mv
	 MOVED_TO，文件被移来，如 mv、cp
	 CREATE，创建新文件
	 DELETE，文件被删除，如 rm
	 DELETE_SELF，自删除，即一个可执行文件在执行时删除自己
	 MOVE_SELF，自移动，即一个可执行文件在执行时移动自己
	 CLOSE，文件被关闭，等同于(IN_CLOSE_WRITE | IN_CLOSE_NOWRITE)
	 ALL_EVENTS，包括上面的所有事件
	*/
	public FileObserveSupport(String filepath,Context context){
		super(filepath);
		this.con=context;
		startWatching();
		warn("测试",DELETE);
	}
	@Override
	public void onEvent(int event, String path)
	{
		int events=event&ALL_EVENTS;
		switch(events){
			case ACCESS:
			warn(path,event);
			break;
			case MODIFY:
			warn(path,event);
			break;
			case DELETE:
			warn(path,event);
			break;
			case ATTRIB:
			warn(path,event);
			break;
			case CLOSE_WRITE:
			warn(path,event);
			break;
			case CLOSE_NOWRITE:
			warn(path,event);
			break;
			case OPEN:
			warn(path,event);
			break;
			case MOVED_TO:
			warn(path,event);
			break;
			case MOVED_FROM:
			warn(path,event);
			break;
			case CREATE:
			warn(path,event);
			break;
		}
		// TODO: Implement this method
	}
	private void warn(String path,int action){
		new DialogFactorySupport(con).SimpleDialog_Only_OneButton("警告","文件"+path+"正在被"+action(action),"确定",null,false).show();
	}
	private String action(int action){
		switch(action){
			case ACCESS:
				return "访问";
			case MODIFY:
				return "修改";
			case DELETE:
				return "删除";
			case ATTRIB:
				return "修改文件属性";
			case CLOSE_WRITE:
				return "写入";
			case CLOSE_NOWRITE:
				return "写入";
			case OPEN:
				return "打开";
			case MOVED_TO:
				return "移动";
			case MOVED_FROM:
				return "移动";
			case CREATE:
				return "创建";
			default:
			return "未知操作";
		}
		
	}
}
