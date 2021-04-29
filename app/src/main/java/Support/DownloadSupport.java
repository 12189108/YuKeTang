package Support;
import java.math.*;

public class DownloadSupport
{
	private String downloadurl;
	public String mfilename="filename";
	public String mfilelength="filelength";
	public DownloadSupport(String downurl){
		this.downloadurl=downurl;
	}
	/*
	*注意判断网络状态！
	*注意不要放在主线程运行！！！
	*偶尔网络不稳定可重复调用2-3次
	*/
	public static double div(double d1,double d2,int scale){
		if(scale<0)throw new IllegalArgumentException("unknow scale");
		BigDecimal b1=new BigDecimal(d1);
		BigDecimal b2=new BigDecimal(d2);
		return b1.divide(b2,scale,b2.ROUND_HALF_UP).doubleValue();
	}
}
