package waste.time.yuketang;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import Support.BaseActivity;
import Support.DownloadSupport;
import Support.DownloadsSupport;

public class TestDownload extends BaseActivity implements DownloadSupport.DownloadListener {
    private ProgressBar pb;
    private DownloadSupport DownloadTask;
    private String msgs;
    private long pr=0,ml=0;
    private android.widget.TextView tv;
    private DownloadsSupport d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        setContentView(R.layout.testdownload);
        pb=findViewById(R.id.progressBar);
        tv=findViewById(R.id.pbr);
        DownloadTask=new DownloadSupport(this,"http://192.168.137.1/cn_windows_10_business_editions_version_21h1_x64_dvd_57455ea1_2.iso","/sdcard/testdownload/test3.iso");
        DownloadTask.setDownloadListener(this);
    }



    public class Handers extends Handler{
        public  Handers(){
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    tv.setText(DownloadSupport.div(pr,ml,4)*100+"%");
                    break;
                default:
                    ShortToastFactorySupport.makeText(msgs).show();
                    break;
            }
        }
    }
    public void startdownload(View view){
        DownloadTask.initTask();
        //ShortToastFactorySupport.makeText("start").show();
    }
    public void stop(View view){
        DownloadTask.stopDownload();
    }

    @Override
    public void onReceiveFileLength(long fileLength) {
        pb.setMax((int) fileLength);
        new Handers().sendEmptyMessage(200);
        ml=fileLength;
        msgs=fileLength+"";
    }

    @Override
    public void onInitFailed(Throwable e) {
      SystemServiceSupport.CopytoSystem(e.toString());
    }

    @Override
    public void onDownload(long process) {
        pb.setProgress((int) process);
        pr=process;
        new Handers().sendEmptyMessage(100);
    }

    @Override
    public void onDownloadFailed(Throwable e) {
        SystemServiceSupport.CopytoSystem(e.toString());
        //ShortToastFactorySupport.makeText(e.toString()).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
