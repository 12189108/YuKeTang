package Support;
import java.net.*;
import android.webkit.*;
import java.io.*;
import java.util.*;

public class HttpSupport
{
	private Map<String,List<String>> HeaderFields;
	private URL url;
	private String Url;
	private HttpURLConnection conn;
	public HttpSupport init(String Url){
		this.Url=Url;
		try {
			url=new URL(Url);
			conn=(HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return this;
	}
	public HttpSupport AttachCookie(String Cookie){
		if(Cookie!=null)conn.setRequestProperty("Cookie",Cookie);
		return this;
	}
	
	public HttpSupport AttachContent_type(String content_type){
		if(content_type!=null)conn.setRequestProperty("Content-Type",content_type);
		return this;
	}
	
	public HttpSupport AttachUser_Agent(String User_Agent){
		if(User_Agent!=null)conn.setRequestProperty("User-Agent",User_Agent);
		return this;
	}
	
	public HttpSupport AttachAccept_Encoding(String Accept_Encoding){
		if(Accept_Encoding!=null)conn.setRequestProperty("Accept-Encoding",Accept_Encoding);
		return this;
	}
	
	public HttpSupport AttachProperty(String key,String value){
		if(key!=null&&value!=null)conn.setRequestProperty(key,value);
		return this;
	}
	
	public Map<String,List<String>> getHeadFields(){
		if(HeaderFields!=null)return HeaderFields;
		else return null;
	}
	
	public String[] getHtml(){
		if(URLUtil.isNetworkUrl(Url)){
			try
			{
				int responseCode=conn.getResponseCode();
				BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder content=new StringBuilder();
				String line;
				while((line=reader.readLine())!=null){
					content.append(line+"\n");
				}
				reader.close();
				HeaderFields=conn.getHeaderFields();
				conn.disconnect();
				return new String[]{responseCode+"",content.toString()};
			}
			catch (Throwable e)
			{
				return new String[]{"-200",e.toString()};
			}
		}else{
			return new String[]{"-100","URL无效"};
		}	
	}
	
	public String[] POSTData(String postdata){
		if(URLUtil.isNetworkUrl(Url)){
			try
			{
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestMethod("POST");
				conn.setUseCaches(false);
				conn.setInstanceFollowRedirects(true);
				DataOutputStream dataout=new DataOutputStream(conn.getOutputStream());
				dataout.writeBytes(postdata);
				dataout.flush();
				dataout.close();
				int responsecode=conn.getResponseCode();
				BufferedReader bf=new BufferedReader(new InputStreamReader(conn.getInputStream(), IOSupport.GS_UTF_8));
				String result;
				StringBuilder sb=new StringBuilder();
				while((result=bf.readLine())!=null){
					sb.append(result).append("\n");
				}
				bf.close();
				HeaderFields=conn.getHeaderFields();
				conn.disconnect();
				return new String[]{responsecode+"",sb.toString()};
			}
			catch (Throwable e)
			{
				return new String[]{"-200",e.toString()};
			}
		}else{
			return new String[]{"-100","URL无效"};
		}
	}
}
