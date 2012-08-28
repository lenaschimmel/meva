package de.gmino.checkin.android;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import de.gmino.meva.android.request.NetworkRequestsImplAsyncTaskBinaryHttp;
import de.gmino.meva.android.request.UtilAndroid;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.Requests;

public class CheckinApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Util.setImpl(new UtilAndroid());
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Requests.setImplementation(new NetworkRequestsImplAsyncTaskBinaryHttp(
				Util.getBaseUrl()));
	}
}
