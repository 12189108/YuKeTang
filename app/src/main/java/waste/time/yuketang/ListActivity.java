package waste.time.yuketang;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Support.BaseActivity;
import Support.HttpSupport;
import Support.LogSupport;

public class ListActivity extends BaseActivity {
    private String cookie,university_id,csrftoken,sessionid,user_id,class_id;
    private String[] msgs,msgs2;
    private AlertDialog Dialog;
    private TextView course_num;
    private ListView course_list;
    private JSONObject data;
    private JSONArray list_json;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        setContentView(R.layout.list_all);
        course_num=findViewById(R.id.couse_num);
        course_list=findViewById(R.id.all_list);
        layout=findViewById(R.id.all_list_layout);
        layout.setVisibility(View.INVISIBLE);
        cookie=getIntent().getStringExtra("cookie");
        university_id=Match(R.string.univeristy_id,cookie);
        csrftoken=Match(R.string.csrftoken,cookie);
        sessionid=Match(R.string.sessionid,cookie);
        new Process().start();
        Dialog=DialogFactorySupport.SimpleProgressDialog(getstring(R.string.check_cookie),false);
        //ShortToastFactorySupport.makeText(university_id).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private String Match(int pattern,String Content){
        Pattern p= Pattern.compile(getstring(pattern));
        Matcher m=p.matcher(Content);
        //必须要执行，不然会报错
        m.find();
        return m.group(0);
    }
    private String[] getUser_ID(){
        HttpSupport http=new HttpSupport();
        http.init(getstring(R.string.user_id_url)).AttachUser_Agent(getstring(R.string.user_agent)).AttachCookie(cookie);
        return http.getHtml();
    }
    private String[] getclass_room(){
        HttpSupport http=new HttpSupport();
        http.init(getstring(R.string.classrom_id_url)+university_id);
        http.AttachUser_Agent(getstring(R.string.user_agent)).AttachCookie(cookie).AttachProperty("xtbz","cloud").AttachProperty("x-csrftoken",csrftoken).AttachProperty("university-id",university_id);
        return http.getHtml();
    }
    private void update_course() throws JSONException {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list_json = data.getJSONArray("product_list");
        for(int i=0;i<data.getInt("count");i++){
            Map<String,String> map=new HashMap<String, String>();
            JSONObject class_find = list_json.getJSONObject(i);
            map.put("course_name",class_find.getString("course_name"));
            map.put("classroom_name",class_find.getString("classroom_name"));
            list.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.course_adapter,new String[]{"course_name","classroom_name"},new int[]{R.id.course_name,R.id.classroom});
        course_list.setAdapter(adapter);
        course_list.setOnItemClickListener(new ClickCallBack());
    }
    private class Handlers extends Handler {
        public Handlers() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                user_id=msgs[1];
                try {
                    JSONObject js = new JSONObject(user_id);
                    if(!js.getBoolean("success")){
                        ShortToastFactorySupport.makeText(getstring(R.string.Invalid_cookie),R.drawable.bcp).show();
                        finish();
                    }
                    else {
                        Dialog.dismiss();
                        user_id=js.getJSONObject("data").getString("user_id");
                        data = new JSONObject(msgs2[1]).getJSONObject("data");
                        course_num.setText(getstring(R.string.class_number).replace("%",data.getString("count")));
                        update_course();
                        layout.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                LongToastFactorySupport.makeText(getstring(R.string.check_fail)+msgs[1],R.drawable.bcp).show();
                finish();
            }
        }
    }
    private class Process extends Thread{
        @Override
        public void run() {
            super.run();
            msgs = getUser_ID();
            msgs2=getclass_room();
            if(msgs[0].equals("200")) new Handlers().sendEmptyMessage(0);
            else new Handlers().sendEmptyMessage(1);
        }
    }
    private class ClickCallBack implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(ListActivity.this, ProgressActivity.class);
            try {
                i.putExtra("msg",list_json.getJSONObject(position).toString());
                i.putExtra("uv_id",university_id);
                i.putExtra("sku_id",list_json.getJSONObject(position).getInt("sku_id"));
                i.putExtra("cookie",cookie);
                i.putExtra("user_id",user_id);
                i.putExtra("token",csrftoken);
            } catch (JSONException e) {
            }
            startActivity(i);
        }
    }
}
