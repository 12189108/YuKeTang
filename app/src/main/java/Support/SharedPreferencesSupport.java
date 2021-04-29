package Support;
import android.content.*;
import java.util.*;

public class SharedPreferencesSupport
{
	private Context con;
	public SharedPreferencesSupport(Context c){
		con=c;
		
	}
		public void putString(String SharedPreferencesName,String putStringName,String putString){
			con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).edit().putString(putStringName,putString).commit();
		}
		public void putboolean(String SharedPreferencesName,String putStringName,boolean putwhat){
			con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).edit().putBoolean(putStringName,putwhat).commit();
		}
		public void putint(String SharedPreferencesName,String putStringName,int putwhat){
			con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).edit().putInt(putStringName,putwhat).commit();
		}
		public void putfloat(String SharedPreferencesName,String putStringName,float putwhat){
			con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).edit().putFloat(putStringName,putwhat).commit();
		}
		public void putlong(String SharedPreferencesName,String putStringName,long putwhat){
			con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).edit().putLong(putStringName,putwhat).commit();
		}
		public void putStringSet(String SharedPreferencesName,String putStringName,Set<String> putwhat){
			con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).edit().putStringSet(putStringName,putwhat).commit();
		}
		public void remove(String SharedPreferencesName,String removewhat){
			con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).edit().remove(removewhat).commit();
		}
		public SharedPreferences.Editor getSharedPreferencesEdit(String SharedPreferencesName){
			return con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).edit();
		}
		public boolean isnull(String SharedPreferencesName){
			if(con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).getAll().toString().equals("{}")){
				return true;
			}
			else{return false;}}
					
		public String getString(String SharedPreferencesName,String what){
			try{return con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).getString(what,null);}catch(Exception e){return "";}
		}
		public boolean getboolean(String SharedPreferencesName,String name){
			return con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).getBoolean(name,false);
		}
		public int getint(String SharedPreferencesName,String what){
			return con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).getInt(what,0);
		}
		public float getfloat(String SharedPreferencesName,String what){
			return con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).getFloat(what,0);
		}
		public Set<String> getSetString(String SharedPreferencesName,String what){
			return con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).getStringSet(what,null);
		}
		public Map<String,?> getAll(String SharedPreferencesName){
			return con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE).getAll();
		}
		public SharedPreferences getSharedPreferences(String SharedPreferencesName,int mode){
			return con.getSharedPreferences(SharedPreferencesName,mode);
		}
		public SharedPreferences getSharedPreferences(String SharedPreferencesName){
			return con.getSharedPreferences(SharedPreferencesName,con.MODE_PRIVATE);
		
		
	}
}
