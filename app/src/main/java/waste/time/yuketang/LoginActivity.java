package waste.time.yuketang;

import Support.BaseActivity;
import Support.ByteTransformSupport;
import Support.EditTexts;
import Support.MD5Support;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import java.net.URI;


public class LoginActivity extends BaseActivity {
    private String json;
    private EditTexts cookies;
    //private String mesg="DqdkjZZMwuUa1tuUNVJfbaiZqXapIoGtNpHmp9rXSoslTflqMAeTRW7vVxUN+kH4wnNLX5B56DBAiDoE/7n85a160cRRKqoWb6vWry7T8mo=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        setOrientation(1);
        setContentView(R.layout.login);
        setTabTitle(getstring(R.string.login_title));
        cookies=findViewById(R.id.cookie);
    }
  public void save(View v){
      String cookie = cookies.getText().toString();
        if(cookie.contains("csrftoken")&&cookie.contains("sessionid")&&cookie.contains("university_id")){
            Intent i = new Intent(this, ListActivity.class);
            i.putExtra("cookie",cookie);
            startActivity(i);
        }
        else{
            ShortToastFactorySupport.makeText(getstring(R.string.err_cookie),R.drawable.bcp).show();
        }
  }

}
