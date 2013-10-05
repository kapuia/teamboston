package hu.boston.tomorrow;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.UAirship;
import com.urbanairship.push.CustomPushNotificationBuilder;
import com.urbanairship.push.PushManager;

public class BostonApplication extends Application {
	private static Context context;
	public static SharedPreferences preferences;


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate() {
				super.onCreate();

		BostonApplication.context = getApplicationContext();
		
		AirshipConfigOptions options = AirshipConfigOptions
				.loadDefaultOptions(this);

		UAirship.takeOff(this, options);
		PushManager.enablePush();

		CustomPushNotificationBuilder nb = new CustomPushNotificationBuilder();

		nb.statusBarIconDrawableId = R.drawable.push_icon_small;// custom status
																// bar icon

		nb.layout = R.layout.notification;
		nb.layoutIconDrawableId = R.drawable.push_icon;// custom layout icon
		nb.layoutIconId = R.id.icon;
		nb.layoutSubjectId = R.id.subject;
		nb.layoutMessageId = R.id.message;

		PushManager.shared().setNotificationBuilder(nb);
		PushManager.shared().setIntentReceiver(IntentReceiver.class);
	}

	public static Context getAppContext() {
		return BostonApplication.context;
	}
}