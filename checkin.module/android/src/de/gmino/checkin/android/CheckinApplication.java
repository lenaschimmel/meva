package de.gmino.checkin.android;

import android.app.Application;
import de.gmino.checkin.android.domain.Coupon;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.meva.android.request.NetworkRequestsImplAsyncTaskBinaryHttp;
import de.gmino.meva.android.request.Util;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.request.Requests;

public class CheckinApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Requests.setImplementation(new NetworkRequestsImplAsyncTaskBinaryHttp(Util.getBaseUrl()));
	}
}
