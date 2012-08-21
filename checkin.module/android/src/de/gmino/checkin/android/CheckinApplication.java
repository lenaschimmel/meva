package de.gmino.checkin.android;

import android.app.Application;
import de.gmino.checkin.android.domain.Coupon;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.meva.shared.EntityFactory;

public class CheckinApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		EntityFactory.setImplementations(new EntityFactoryImpl(), new EntityRequestBinary("http://134.169.137.217:8002/"));
	}
}
