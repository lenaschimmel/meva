package de.gmino.checkin.android.facebook.errorhandling;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public abstract class FacebookDialogListener extends FacebookErrorListener
		implements DialogListener {

	@Override
	public void onFacebookError(FacebookError e) {
		handleError(e);
	}

	@Override
	public void onError(DialogError e) {
		handleError(e);
	}

	@Override
	public void onCancel() {
		handleError(new RuntimeException("Canceled, no more info."));
	}

}
