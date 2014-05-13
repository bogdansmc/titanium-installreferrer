package net.pixelfactor.installreferrer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.ads.conversiontracking.InstallReceiver;

public class ReferrerBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "net.pixelfactor.installreferrer";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String uri = intent.toURI();
		if (uri != null && uri.length() > 0) {
			Log.i(TAG, "Received URI: " + uri);
			int index = uri.indexOf("referrer=");
			if (index > -1) {
				uri = uri.substring(index + 9, uri.length() - 4);
				Log.i(TAG, "Received INSTALL_REFERRER URI: " + uri);

				SharedPreferences settings = context.getSharedPreferences("installPreferences", 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("INSTALL_REFERRER", uri);
				editor.commit();
			}
		}
		
		try {
			((BroadcastReceiver)Class.forName("com.google.ads.conversiontracking.InstallReceiver").newInstance()).onReceive(context, intent); //send intent by dynamically creating instance of receiver
		} catch (java.lang.ClassNotFoundException e) {
		} catch (java.lang.InstantiationException e) {
		} catch (java.lang.IllegalAccessException e) {
		} finally {
		}
		
		//InstallReceiver.onReceive(context, intent);
		
		//com.google.ads.conversiontracking.InstallReceiver.onReceive(context, intent);
	}
}
