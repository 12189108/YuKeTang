package waste.time.yuketang;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import Support.BaseActivity;
import Support.DownloadSupport;
import Support.DownloadsSupport;

public class TestDownload extends BaseActivity implements DownloadSupport.DownloadListener {
    private ProgressBar pb;
    private DownloadSupport DownloadTask;
    private String msgs;
    private DownloadsSupport d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(this);
        setContentView(R.layout.testdownload);
        pb=findViewById(R.id.progressBar);
        DownloadTask=new DownloadSupport(this,"https://files.catbox.moe/6xyrv6.%E5%B8%B8%E7%94%A8%E5%BA%94%E7%94%A87z","/sdcard/testdownload/test2.7z");
        DownloadTask.setDownloadListener(this);
    }



    public class Handers extends Handler{
        public  Handers(){
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ShortToastFactorySupport.makeText(msgs).show();
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
        msgs=fileLength+"";
        SystemServiceSupport.CopytoSystem(fileLength+"");
    }

    @Override
    public void onInitFailed(Throwable e) {
      SystemServiceSupport.CopytoSystem(e.toString());
    }

    @Override
    public void onDownload(long process) {
        pb.setProgress((int) process);
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