package waste.time.yuketang;

import Support.*;
import android.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import java.util.*;
import org.json.*;

public class ProgressActivity extends BaseActivity {
    private JSONObject data,course_data;
	private JSONArray course_datas;
    private String video_url,cookie,uv_id;
    private AlertDialog load_dialog;
    private String[] msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        cookie=getIntent().getStringExtra("cookie");
        uv_id=getIntent().getStringExtra("uv_id");
        load_dialog=DialogFactorySupport.SimpleProgressDialog(getstring(R.string.course_load),false);
        try { 
            data=new JSONObject(getIntent().getStringExtra("msg"));
            video_url=getstring(R.string.video_id_url).replace("*",data.getString("classroom_id")).replace("#",data.getString("course_sign")).replace("%",uv_id);
        }
        catch (JSONException e) {}
        new Process().start();
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private String[] getVideoIds(){
        HttpSupport http=new HttpSupport();
        http.init(video_url);
		
        http.AttachCookie(cookie).AttachUser_Agent(getstring(R.string.user_agent)).AttachProperty("university-id",uv_id).AttachProperty("xtbz","cloud");
        return http.getHtml();
    }
    @SuppressLint("HandlerLeak")
    private class Handlers extends Handler{
        public Handlers(){
            super(getMainLooper());
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    ShortToastFactorySupport.makeText(getstring(R.string.get_course_failed),R.drawable.bcp).show();
                    finish();
                    break;
            }
        }
    }
    private class Process extends Thread{
        @Override
        public void run() {
            super.run();
            msg=getVideoIds();
            if(msg[0].equals("200")){
				try
				{
					course_data = new JSONObject(msg[1]);
					course_data=course_data.getJSONObject("data");
					course_datas=course_data.getJSONArray("course_chapter");
				}
				catch (JSONException e)
				{}
				new Handlers().sendEmptyMessage(0);
			}
            else new Handlers().sendEmptyMessage(1);
        }
    }
}
