package Support;

import android.content.Context;

public class UPDateSupport {
    private String CheckUrl;
    private Context mContext;
    public UPDateSupport(Context mContext,String checkURL){
        this.mContext=mContext;
        this.CheckUrl=checkURL;
    }
    public void checkVersion()throws Throwable{
        final DownloadSupport mDownloadSupport=new DownloadSupport(mContext,CheckUrl,mContext.getFilesDir().getAbsolutePath()+"/update_config.dat");
        mDownloadSupport.setSingleThreadMode();
        mDownloadSupport.setDownloadListener(new DownloadSupport.DownloadListener() {
            @Override
            public void onReceiveFileLength(long fileLength) {

            }

            @Override
            public void onInitFailed(Throwable e) {

            }

            @Override
            public void onDownload(long process) {

            }

            @Override
            public void onDownloadFailed(Throwable e) {

            }
        });
        mDownloadSupport.initTask();
    }
}
