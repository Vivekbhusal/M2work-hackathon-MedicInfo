package vivek.com.medicinfo.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class checkconnection {
	
	public static boolean checkInternetConnection(Context context) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			NetworkInfo[] netInfo = cm.getAllNetworkInfo();
			for (NetworkInfo ni : netInfo) {
				if (ni.getTypeName().equalsIgnoreCase("WIFI"))
					if (ni.isConnected())
						haveConnectedWifi = true;
				if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
					if (ni.isConnected())
						haveConnectedMobile = true;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return haveConnectedWifi || haveConnectedMobile;
	}


}
