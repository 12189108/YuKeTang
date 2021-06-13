package waste.time.yuketang;

import Support.BaseActivity;
import Support.EditTexts;
import Support.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        ServiceSupport.startActivity(this,TestDownload.class);
        //ShortToastFactorySupport.makeText(getFilesDir().getAbsolutePath()).show();
        //ShortToastFactorySupport.makeText(System.currentTimeMillis()+"").show();
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
