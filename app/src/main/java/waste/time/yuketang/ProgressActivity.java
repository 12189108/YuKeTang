package waste.time.yuketang;

import Support.*;
import android.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.ListView;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class ProgressActivity extends BaseActivity {
    private JSONObject data,course_data;
	private JSONArray course_datas;
    private String video_url,cookie,uv_id,course_id,user_id;
    private AlertDialog load_dialog;
    private String[] msg;
    private Course_Adapter adapter;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        setContentView(R.layout.list_all);
        lv=findViewById(R.id.all_list);
        cookie=getIntent().getStringExtra("cookie");
        uv_id=getIntent().getStringExtra("uv_id");
        user_id=getIntent().getStringExtra("user_id");
        load_dialog=DialogFactorySupport.SimpleProgressDialog(getstring(R.string.course_load),false);
        try { 
            data=new JSONObject(getIntent().getStringExtra("msg"));
            course_id=data.getString("course_id");
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
    private boolean compete_state(String video_id) throws JSONException {
        String vurl=getstring(R.string.get_progress).replace("*",course_id).replace("#",user_id).replace("%",data.getString("classroom_id")).replace("!",video_id);
        HttpSupport http=new HttpSupport();
        http.init(vurl);
        http.AttachCookie(cookie).AttachUser_Agent(getstring(R.string.user_agent)).AttachProperty("x-csrftoken",getIntent().getStringExtra("token")).AttachProperty("xtbz","cloud").AttachProperty("university-id",uv_id);
        String[] response=http.getHtml();
        if(response[0].equals("200")){
            return check(response[1]);
        }else{
            return compete_state(video_id);
        }
    }
    private boolean check(String data){
        Pattern patt=Pattern.compile("(?<=completed\":)[0-9]");
        Matcher m=patt.matcher(data);
        SystemServiceSupport.CopytoSystem(data);
        if(m.find())return m.group(0).equals('1');
        return false;
        //SystemServiceSupport.CopytoSystem(m.group().toString());
    }
    private void listitem() throws JSONException {
        List<Course_data> list=new ArrayList<Course_data>();
        for(int i=0;i<course_datas.length();i++){
           JSONArray tmp=course_datas.getJSONObject(i).getJSONArray("section_leaf_list");
           for(int n=1;n<tmp.length();n++){
               JSONObject tmp2=tmp.getJSONObject(n);
               if(n!=tmp.length()){
                   JSONObject tmp3=tmp2.getJSONArray("leaf_list").getJSONObject(0);
                   if(tmp3.getInt("leaf_type")==0){
                       boolean complete = compete_state(tmp3.getString("id"));
                       list.add(new Course_data(tmp3.getString("name"),"未知",complete,tmp3.getInt("leaf_type"),complete?getResources().getDrawable(R.drawable.bd9):getResources().getDrawable(R.drawable.bcp)));
                   }
               }
               else{
                   JSONObject tmp3=tmp2;
               }
           }
        }
        adapter=new Course_Adapter(getApplicationContext(),0,list);
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
                    lv.setAdapter(adapter);
                    load_dialog.dismiss();
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
					listitem();
				}
				catch (JSONException e)
				{}
				new Handlers().sendEmptyMessage(0);
			}
            else new Handlers().sendEmptyMessage(1);
        }
    }
}
