package waste.time.yuketang;

import Support.BaseActivity;
import Support.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoginActivity extends BaseActivity {
    private android.widget.ImageView qrcodeimg;
    private LinearLayout request_tip;
    private TextView tip;
    private String Cookies="";
    private int get_msgtime;
    private String qrcode="https://api.qrserver.com/v1/create-qr-code/?size=250%C3%97250&data=";
    private SSLContext ssl;

    //private String mesg="DqdkjZZMwuUa1tuUNVJfbaiZqXapIoGtNpHmp9rXSoslTflqMAeTRW7vVxUN+kH4wnNLX5B56DBAiDoE/7n85a160cRRKqoWb6vWry7T8mo=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        setOrientation(1);
        setContentView(R.layout.login);
        setTabTitle(getstring(R.string.login_title));
        qrcodeimg=findViewById(R.id.qrcode);
        request_tip=findViewById(R.id.requesting);
        tip=findViewById(R.id.tip);
        getcookie();
        //new test().start();
        //LongToastFactorySupport.makeText(this.getExternalFilesDir())
        //ShortToastFactorySupport.makeText(getFilesDir().getAbsolutePath()).show();
        //ShortToastFactorySupport.makeText(System.currentTimeMillis()+"").show();

    }
  public void save(View v){
      getcookie();
  }
  public void getcookie(){
        request_tip.setVisibility(View.VISIBLE);
        qrcodeimg.setVisibility(View.GONE);
      get_msgtime=0;
      OkHttpClient mClient=new OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS) .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
              .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
              .build();
      String url = "wss://bksycsu.yuketang.cn/wsapp";
      Request request = new Request.Builder().get().url(url).build();
      WebSocket webSocket=mClient.newWebSocket(request, new WebSocketListener() {
          @Override
          public void onOpen(WebSocket webSocket, Response response) {
              super.onOpen(webSocket, response);
              JSONObject js = new JSONObject();
              try{
                  js.put("op","requestlogin");
                  js.put("role","web");
                  js.put("version",1.4);
                  js.put("type","qrcode");
              }catch (Throwable e){}
              webSocket.send(js.toString());
          }

          @Override
          public void onMessage(WebSocket webSocket, String text) {
              super.onMessage(webSocket, text);
              if(get_msgtime==0){
                  get_msgtime+=1;
                  try {
                      JSONObject data=new JSONObject(text);
                      final String url=qrcode+data.getString("qrcode");
                      new Thread(){
                          @Override
                          public void run() {
                              super.run();
                              try {
                                  URL imgurl=new URL(url);
                                  final Bitmap img= BitmapFactory.decodeStream(imgurl.openStream());
                                  runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          qrcodeimg.setImageBitmap(img);
                                          qrcodeimg.setVisibility(View.VISIBLE);
                                          request_tip.setVisibility(View.GONE);
                                      }
                                  });
                              } catch (Throwable e) {

                              }
                          }
                      }.start();
                  } catch (JSONException e) {

                  }
              }
              else {
                  try {
                      getCookie(new JSONObject(text));
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              }
          }

          @Override
          public void onMessage(WebSocket webSocket, ByteString bytes) {
              super.onMessage(webSocket, bytes);
          }

          @Override
          public void onClosing(WebSocket webSocket, int code, String reason) {
              super.onClosing(webSocket, code, reason);
          }

          @Override
          public void onClosed(WebSocket webSocket, int code, String reason) {
              super.onClosed(webSocket, code, reason);
          }

          @Override
          public void onFailure(WebSocket webSocket, Throwable t, Response response) {
              super.onFailure(webSocket, t, response);
              SystemServiceSupport.CopytoSystem(t.toString());
          }
      });
  }
  private void getCookie(JSONObject returned_info) throws JSONException {
      String[] ids = getids();
      if(!ids[0].equals("fail")){
          Cookies="university_id="+ids[1]+";platform_id="+ids[0]+";";
          HttpSupport conn = new HttpSupport().init("https://bksycsu.yuketang.cn/pc/web_login?term=latest&uv_id=" + ids[1]).AttachUser_Agent(getstring(R.string.user_agent));
          JSONObject datas = new JSONObject();
          datas.put("Auth",returned_info.getString("Auth"));
          datas.put("UserID",returned_info.getInt("UserID"));
          datas.put("host_name","bksycsu.yuketang.cn");
          datas.put("no_loading",true);
          conn.AttachCookie(Cookies.replace("Secure",""));
          conn.POSTData(datas.toString());
          List<String> cookies=conn.getHeadFields().get("Set-Cookie");
          HttpSupport connect = new HttpSupport().init("https://bksycsu.yuketang.cn/edu_admin/check_user_session/?term=latest&uv_id=" + ids[1]).AttachUser_Agent(getstring(R.string.user_agent));
          String tmpcookie ="";
          for(String i:cookies){
            if(i.contains("sessionid")||i.contains("csrftoken")) {
                String tag = i.replace("Path=/", "").replace("; ;", "");
                tmpcookie+=tag.substring(0,tag.indexOf(";")+1);

            }
          }
          connect.AttachCookie(tmpcookie);
          connect.getHtml();
          List<String> last_cookie=connect.getHeadFields().get("Set-Cookie");
          for(String i:last_cookie){
              if(i.contains("sessionid")||i.contains("csrftoken")) {
                  String tag = i.replace("Path=/", "").replace("; ;", "");
                  Cookies+=tag.substring(0,tag.indexOf(";")+1);

              }
          }
          Intent i=new Intent(this,ListActivity.class);
          i.putExtra("cookie",Cookies.replace("Secure",""));
          startActivity(i);
      }
      else SystemServiceSupport.CopytoSystem(ids[1]);
  }
  private String[] getids(){
      JSONObject data;
      HttpSupport conn = new HttpSupport().init("https://bksycsu.yuketang.cn/edu_admin/get_custom_university_info/?current=1").AttachUser_Agent(getstring(R.string.user_agent));
      String[] content = conn.getHtml();
      if(content[0].equals("200")){
          try {
              data=new JSONObject(content[1]);
              return new String[]{data.getJSONObject("data").getString("platform_id"),data.getJSONObject("data").getString("university_id")};
          } catch (JSONException e) {
              return new String[]{"fail",e.toString()};
          }
      }
      else return new String[]{"fail",content[1]};
  }

}
