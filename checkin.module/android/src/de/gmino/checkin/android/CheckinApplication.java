package de.gmino.checkin.android;

import android.app.Application;
import de.gmino.checkin.android.domain.Consumer;
import de.gmino.checkin.android.facebook.FacebookLoginStatusListener;
import de.gmino.checkin.android.facebook.FacebookUtil;
import de.gmino.checkin.android.request.QueryConsumerByFid;
import de.gmino.meva.android.request.NetworkRequestsImplAsyncTaskBinaryHttp;
import de.gmino.meva.android.request.UtilAndroid;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CheckinApplication extends Application implements
		FacebookLoginStatusListener {
	private Consumer me;

	@Override
	public void onCreate() {
		super.onCreate();
		Util.setImpl(new UtilAndroid());
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Requests.setImplementation(new NetworkRequestsImplAsyncTaskBinaryHttp(
				Util.getBaseUrl()));
		FacebookUtil.addFacebookEventListener(this);
	}

	@Override
	public void onLoggedIn(String fid, String name) {
		Query q = new QueryConsumerByFid(fid);
		Requests.getLoadedEntitiesByQuery(Consumer.type, q,
				new RequestListener<Consumer>() {
					@Override
					public void onNewResult(Consumer result) {
						me = result;
						System.out.println("Me object: " + me);
					}
				});
	}

	@Override
	public void onLoggedOut() {
		me = null;
	}

	public Consumer getConsumer() {
		return me;
	}
}
