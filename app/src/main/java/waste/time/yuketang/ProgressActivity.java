package waste.time.yuketang;

import android.os.Bundle;
import android.view.KeyEvent;

import org.json.JSONException;
import org.json.JSONObject;

import Support.BaseActivity;
import Support.HttpSupport;

public class ProgressActivity extends BaseActivity {
    private JSONObject data;
    private String video_url,cookie,uv_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        cookie=getIntent().getStringExtra("cookie");
        uv_id=getIntent().getStringExtra("uv_id");
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
    private String getVideoIds(){
        HttpSupport http=new HttpSupport();
        http.init(video_url);
        http.AttachCookie(cookie).AttachUser_Agent(getstring(R.string.user_agent)).AttachProperty("university-id",uv_id).AttachProperty("xtbz","cloud");
        return http.getHtml()[1];
    }
    private class Process extends Thread{
        @Override
        public void run() {
            super.run();
            SystemServiceSupport.CopytoSystem(getVideoIds());
        }
    }
}
