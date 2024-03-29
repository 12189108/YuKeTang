package waste.time.yuketang;

import Support.*;
import android.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.ListView;
import java.text.DecimalFormat;
import java.util.*;

import org.json.*;

public class ProgressActivity extends BaseActivity {
    private JSONObject data,course_data;
	private JSONArray course_datas,set_video;
    private String video_url,cookie,uv_id,course_id,user_id,video_detail,classroom_id,x_csrftoken;
    private AlertDialog load_dialog;
    private String[] msg;
    private int sku_id;
    private Course_Adapter adapter;
    private ListView lv;
    private android.widget.TextView course_num;
    private int course_num_num=0;
    private int complete_quire=0;
    private int compelete_kill_course=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        setOrientation(1);
        setContentView(R.layout.course_list);
        lv=findViewById(R.id.course_list);
        course_num=findViewById(R.id.couse_num_2);
        cookie=getIntent().getStringExtra("cookie");
        uv_id=getIntent().getStringExtra("uv_id");
        user_id=getIntent().getStringExtra("user_id");
        sku_id=getIntent().getIntExtra("sku_id",0);
        x_csrftoken=getIntent().getStringExtra("token");
        load_dialog=DialogFactorySupport.SimpleProgressDialog(getstring(R.string.course_load),false);
        try { 
            data=new JSONObject(getIntent().getStringExtra("msg"));
            course_id=data.getString("course_id");
            classroom_id=data.getString("classroom_id");
            video_url=getstring(R.string.video_id_url).replace("*",data.getString("classroom_id")).replace("#",data.getString("course_sign")).replace("%",uv_id);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        new Process().start();
	}
	public void kill_lesson(View v){
		new lesson_killer().start();
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
    private boolean compete_state(String video_id){
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
    private int getInquireNum() throws JSONException {
        for(int i=0;i<course_datas.length();i++){
            JSONArray tmp=course_datas.getJSONObject(i).getJSONArray("section_leaf_list");
            for(int n=1;n<tmp.length();n++){
                JSONObject tmp2=tmp.getJSONObject(n);
                if(tmp2.has("leaf_list")){
                    JSONArray tmp3=tmp2.getJSONArray("leaf_list");
                    for(int m=0;m<tmp3.length();m++){
                        JSONObject tmp4 = tmp3.getJSONObject(m);
                        if (tmp4.getInt("leaf_type") == 0) {
                            course_num_num+=1;
                        }
                    }
                }
                else{
                    course_num_num+=1;
                    JSONObject tmp3=tmp2;

                }
            }
        }
        return course_num_num;
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
        set_video=new JSONArray();
        List<Course_data> list=new ArrayList<>();
        for(int i=0;i<course_datas.length();i++){
           JSONArray tmp=course_datas.getJSONObject(i).getJSONArray("section_leaf_list");
           for(int n=0,len=tmp.length();n<len;n++){
               JSONObject tmp2=tmp.getJSONObject(n);
               if(tmp2.has("leaf_list")){
                   JSONArray tmp3=tmp2.getJSONArray("leaf_list");
                   for(int m=0;m<tmp3.length();m++){
                       JSONObject tmp4 = tmp3.getJSONObject(m);
                       if (tmp4.getInt("leaf_type") == 0) {
                           boolean complete = compete_state(tmp4.getString("id"));
                           list.add(new Course_data(tmp4.getString("name"), getProgress(video_detail), complete, tmp4.getInt("leaf_type"), complete ? getResources().getDrawable(R.drawable.bd9) : getResources().getDrawable(R.drawable.bcp),!complete));
                           complete_quire+=1;
                           new Handlers().sendEmptyMessage(200);
                           if(!complete) {
                               JSONObject tmp_js = new JSONObject();
                               tmp_js.put("video_id", tmp4.getInt("id"));
                               tmp_js.put("list_position", list.size() - 1);
                               set_video.put(tmp_js);
                           }
                       }
                   }
               }
               else{
                   JSONObject tmp3=tmp2;
                   boolean complete=getDiscussion_state(tmp3.getString("id"));
                   list.add(new Course_data(course_datas.getJSONObject(i).getString("name")+tmp3.getString("name"),complete?"已完成":"未开始",complete,tmp3.getInt("leaf_type"),complete?getResources().getDrawable(R.drawable.bd9) : getResources().getDrawable(R.drawable.bcp),!complete));
                   complete_quire+=1;
                   new Handlers().sendEmptyMessage(200);
                   if(!complete&&tmp3.getInt("leaf_type")==0){
                       JSONObject tmp_js = new JSONObject();
                       tmp_js.put("video_id", tmp3.getInt("id"));
                       set_video.put(tmp_js);
                   }
               }
           }
        }
        adapter=new Course_Adapter(getApplicationContext(),0,list);
    }
    private String getCcid(String video_id) throws JSONException {
        HttpSupport http = new HttpSupport();
        http.init(getstring(R.string.get_ccid_url).replace("*",classroom_id).replace("#",video_id).replace("!",data.getString("course_sign"))+uv_id);
        http.AttachCookie(cookie).AttachUser_Agent(getstring(R.string.user_agent)).AttachProperty("x-csrftoken",x_csrftoken).AttachProperty("xtbz","cloud").AttachProperty("university-id",uv_id);
        String[] ccids = http.getHtml();
        if(ccids[0].equals("200")){
            if(ccids[1].contains("ccid")){
                return new JSONObject(ccids[1]).getJSONObject("data").getJSONObject("content_info").getJSONObject("media").getString("ccid");
            }
            else return video_id;
        }
        else return getCcid(video_id);
    }
    private void kill_video(int video_id) throws JSONException {
        JSONObject d = new JSONObject();
        JSONArray progress_array = new JSONArray();
        double video_frame = 0;
        int learning_rate = 10;
        String ccid = getCcid(video_id + "");
            for (int i = 0; i <=300; i++) {
                JSONObject tmpjs = new JSONObject();
                tmpjs.put("i", 5);
                tmpjs.put("et", "loadeddata");
                tmpjs.put("p", "web");
                tmpjs.put("n", "sjy-cdn.xuetangx.com");
                tmpjs.put("lob", "cloud4");
                tmpjs.put("cp", video_frame);
                tmpjs.put("sp", 1);
                tmpjs.put("tp", 0);
                tmpjs.put("fp", 0);
                tmpjs.put("ts", System.currentTimeMillis() + "");
                tmpjs.put("u", Integer.valueOf(user_id));
                tmpjs.put("uip", "");
                tmpjs.put("c", Integer.valueOf(course_id));
                tmpjs.put("v", video_id);
                tmpjs.put("skuid", sku_id);
                tmpjs.put("classroomid", classroom_id);
                tmpjs.put("cc", ccid);
                tmpjs.put("d", 3000);
                tmpjs.put("pg", video_id + "_wlnd");
                tmpjs.put("sq", 1);
                tmpjs.put("t", "video");
                progress_array.put(tmpjs);
                video_frame += learning_rate;
            }
            d.put("heart_data", progress_array);
            HttpSupport https = new HttpSupport();
            https.init("https://bksycsu.yuketang.cn/video-log/heartbeat/");
            https.AttachCookie(cookie).AttachUser_Agent(getstring(R.string.user_agent)).AttachProperty("xtbz", "cloud").AttachProperty("x-csrftoken", x_csrftoken).AttachProperty("Content-Type", "application/json");
            https.POSTData(d.toString());
            compelete_kill_course+=1;
            new Handlers().sendEmptyMessage(3);
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
                    course_num.setText("共"+course_num_num+"个可刷作业");
                    load_dialog.dismiss();
                    complete_quire=0;
                    break;
                case 1:
                    ShortToastFactorySupport.makeText(getstring(R.string.get_course_failed),R.drawable.bcp).show();
                    finish();
                    break;
                case 2:
                    load_dialog.dismiss();
                    compelete_kill_course=0;
                    ShortToastFactorySupport.makeText(getstring(R.string.kill_course_complete),R.drawable.bd9).show();
                    break;
                case 3:
                    DialogFactorySupport.changeText(getstring(R.string.kill_course_num).replace("b",set_video.length()+"").replace("a",compelete_kill_course+""));
                    break;
                case 4:
                    load_dialog=DialogFactorySupport.SimpleProgressDialog(getstring(R.string.course_load),false);
                    break;
                case 200:
                    DialogFactorySupport.changeText(getstring(R.string.quire_num_show).replace("a",complete_quire+"").replace("b",course_num_num+""));
                    break;
                case 100:
                    load_dialog=DialogFactorySupport.SimpleProgressDialog(getstring(R.string.kill_course_num).replace("a","0").replace("b",set_video.length()+""),false);
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
					getInquireNum();
					listitem();
				}
				catch (JSONException e)
				{
				    //e.printStackTrace();
                }
				new Handlers().sendEmptyMessage(0);
			}
            else new Handlers().sendEmptyMessage(1);
        }
    }
	private class lesson_killer extends Thread
	{
		@Override
		public void run()
		{
			// TODO: Implement this method
			super.run();
			new Handlers().sendEmptyMessage(100);
			for(int i=0;i<set_video.length();i++){
				try
				{
					JSONObject js=set_video.getJSONObject(i);
					kill_video(js.getInt("video_id"));
				}
				catch (JSONException e)
				{
					//e.printStackTrace();
				}
			}
			new Handlers().sendEmptyMessage(2);
            try {
                new Handlers().sendEmptyMessage(4);
                listitem();
                new Handlers().sendEmptyMessage(0);
            } catch (JSONException e) {
                //
            }
        }
	}
}
