package Support;
import android.content.*;
import android.net.*;
import android.net.wifi.*;
import java.lang.reflect.*;
import java.util.*;
public class WifiSuport
{
	private Context con;
	private WifiManager WifiManager;
	public WifiSuport(Context c4){
		con=c4;
		WifiManager=(WifiManager) con.getSystemService(con.WIFI_SERVICE);
	}
	public WifiInfo getWifiInfo(){
		return WifiManager.getConnectionInfo();
	}
	public List<ScanResult> getScanResult(){
		return WifiManager.getScanResults();
	}
	public List<WifiConfiguration> getConfiguration()
    {
		return WifiManager.getConfiguredNetworks();
		//return wifiConfigurationlist;
    }

    public DhcpInfo getDhcpInfo()
    {
		DhcpInfo wifiDhcpInfo = WifiManager.getDhcpInfo();
		return wifiDhcpInfo;
    }
	public WifiConfiguration getCustomeWifiConfiguration(String ssid,
														 String passwd, int type)
    {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = ssid;
		if (type == 1) // NOPASS
		{
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (type == 2) // WEP
		{
			config.hiddenSSID = true;
			config.wepKeys[0] = passwd;
			config.allowedAuthAlgorithms
				.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers
				.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (type == 3) // WPA
		{
			config.preSharedKey = passwd;
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
				.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		if (type == 4) // WPA2psk test
		{
			config.preSharedKey = passwd;
			config.hiddenSSID = true;

			config.status = WifiConfiguration.Status.ENABLED;
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

		}
		return config;

    }
	public WifiConfiguration getCustomeWifiClientConfiguration(String ssid,
															   String passwd, int type)
    {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		//双引号必须
		config.SSID = "\"" + ssid + "\"";
		if (type == 1) // WIFICIPHER_NOPASS
		{
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (type == 2) // WIFICIPHER_WEP
		{
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + passwd + "\"";
			config.allowedAuthAlgorithms
				.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers
				.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (type == 3) // WIFICIPHER_WPA
		{
			config.preSharedKey = "\"" + passwd + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
				.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		if (type == 4) // WPA2psk test
		{
			config.preSharedKey = "\"" + passwd + "\"";
			config.hiddenSSID = true;

			config.status = WifiConfiguration.Status.ENABLED;
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

		}
		return config;

    }

    public Boolean stratWifiAp(String ssid, String psd, int type)
    {
		Method method1 = null;
		try
		{
			method1 = WifiManager.getClass().getMethod("setWifiApEnabled",
													   WifiConfiguration.class, boolean.class);
			WifiConfiguration netConfig = getCustomeWifiConfiguration(ssid,
																	  psd, type);

			method1.invoke(WifiManager, netConfig, true);
			return true;
		}
		catch (Exception e)
		{
	
			return false;
		}
    }

    public void closeWifiAp()
    {
		if (isWifiApEnabled())
		{
			try
			{
				Method method = WifiManager.getClass().getMethod(
					"getWifiApConfiguration");
				method.setAccessible(true);

				WifiConfiguration config = (WifiConfiguration) method
					.invoke(WifiManager);

				Method method2 = WifiManager.getClass().getMethod(
					"setWifiApEnabled", WifiConfiguration.class,
					boolean.class);
				method2.invoke(WifiManager, config, false);
			}
			catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
    }

    public boolean isWifiApEnabled()
    {
		try
		{
			Method method = WifiManager.getClass().getMethod("isWifiApEnabled");
			method.setAccessible(true);
			return (Boolean) method.invoke(WifiManager);

		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
    }
	
}
