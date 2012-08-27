package de.gmino.checkin.android;

import android.app.Application;
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
