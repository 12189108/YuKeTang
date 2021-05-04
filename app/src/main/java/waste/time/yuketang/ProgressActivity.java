package waste.time.yuketang;

import Support.*;
import android.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class ProgressActivity extends BaseActivity {
    private JSONObject data,course_data;
	private JSONArray course_datas;
    private String video_url,cookie,uv_id,course_id,user_id,video_detail,classroom_id,x_csrftoken;
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
        x_csrftoken=getIntent().getStringExtra("token");
        load_dialog=DialogFactorySupport.SimpleProgressDialog(getstring(R.string.course_load),false);
        try { 
            data=new JSONObject(getIntent().getStringExtra("msg"));
            course_id=data.getString("course_id");
            classroom_id=data.getString("classroom_id");
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
        String vurl=getstring(R.string.get_progress).replace("*",course_id).replace("#",user_id).replace("%",classroom_id).replace("!",video_id);
        HttpSupport http=new HttpSupport();
        http.init(vurl);
        http.AttachCookie(cookie).AttachUser_Agent(getstring(R.string.user_agent)).AttachProperty("x-csrftoken",x_csrftoken).AttachProperty("xtbz","cloud").AttachProperty("university-id",uv_id);
        String[] response=http.getHtml();
        if(response[0].equals("200")){
            video_detail=response[1];
            return check(response[1]);
        }else{
            return compete_state(video_id);
        }
    }
    private boolean check(String data){
        if(data.contains("completed")) {
            String tmp = data.substring(data.indexOf("completed"));
            tmp = tmp.substring(11, 12);
            return tmp.equals("1");
        }
        return false;
        //SystemServiceSupport.CopytoSystem(m.group().toString());
    }
    private String getProgress(String data){
        if(data.contains("rate")){
            String tmp=data.substring(data.indexOf("rate"));
            String rates=tmp.substring(tmp.indexOf(":")+1,tmp.indexOf(","));
            double rate=Double.parseDouble(rates)*100;
            DecimalFormat d=new DecimalFormat("0.00");
            return getstring(R.string.show_progress).replace("#",d.format(rate));
        }
        return getstring(R.string.show_progress).replace("#","0");
    }
    private boolean getDiscussion_state(String discussion_id) throws JSONException {
        HttpSupport http=new HttpSupport();
        http.init(getstring(R.string.get_discussion_state_url).replace("*",discussion_id).replace("#",classroom_id)+uv_id);
        http.AttachCookie(cookie).AttachUser_Agent(getstring(R.string.user_agent)).AttachProperty("x-csrftoken",x_csrftoken).AttachProperty("xtbz","cloud");
        String[] response = http.getHtml();
        if(response[0].equals("200")){
            if(response[1].contains("data")){
                return new JSONObject(response[1]).getBoolean("data");
            }
        }
        else return getDiscussion_state(discussion_id);
        return false;
    }
    private void listitem() throws JSONException {
        List<Course_data> list=new ArrayList<Course_data>();
        for(int i=0;i<course_datas.length();i++){
           JSONArray tmp=course_datas.getJSONObject(i).getJSONArray("section_leaf_list");
           for(int n=1;n<tmp.length();n++){
               JSONObject tmp2=tmp.getJSONObject(n);
               if(tmp2.has("leaf_list")){
                   JSONArray tmp3=tmp2.getJSONArray("leaf_list");
                   for(int m=0;m<tmp3.length();m++){
                       JSONObject tmp4 = tmp3.getJSONObject(m);
                       if (tmp4.getInt("leaf_type") == 0) {
                           boolean complete = compete_state(tmp4.getString("id"));
                           list.add(new Course_data(tmp4.getString("name"), getProgress(video_detail), complete, tmp4.getInt("leaf_type"), complete ? getResources().getDrawable(R.drawable.bd9) : getResources().getDrawable(R.drawable.bcp),!complete));
                       }
                   }
               }
               else{
                   JSONObject tmp3=tmp2;
                   boolean complete=getDiscussion_state(tmp3.getString("id"));
                   list.add(new Course_data(course_datas.getJSONObject(i).getString("name")+tmp3.getString("name"),complete?"已完成":"未开始",complete,tmp3.getInt("leaf_type"),complete?getResources().getDrawable(R.drawable.bd9) : getResources().getDrawable(R.drawable.bcp),!complete));
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
				{
				    e.printStackTrace();
                }
				new Handlers().sendEmptyMessage(0);
			}
            else new Handlers().sendEmptyMessage(1);
        }
    }
}
