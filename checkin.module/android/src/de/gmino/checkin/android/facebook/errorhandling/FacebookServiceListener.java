package de.gmino.checkin.android.facebook.errorhandling;

import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook.ServiceListener;
import com.facebook.android.FacebookError;

public abstract class FacebookServiceListener extends FacebookErrorListener implements ServiceListener {

	@Override
	public void onFacebookError(FacebookError e) {
		handleError(e);
	}

	@Override
	public void onError(Error e) {
		handleError(e);
	}

}
