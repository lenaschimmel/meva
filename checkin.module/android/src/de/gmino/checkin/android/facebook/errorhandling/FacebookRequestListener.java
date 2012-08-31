package de.gmino.checkin.android.facebook.errorhandling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;

public abstract class FacebookRequestListener extends FacebookErrorListener implements
		RequestListener {


	@Override
	public void onIOException(IOException e, Object state) {
		handleError(e, state);
	}

	@Override
	public void onFileNotFoundException(FileNotFoundException e, Object state) {
		handleError(e, state);
	}

	@Override
	public void onMalformedURLException(MalformedURLException e, Object state) {
		handleError(e, state);
	}

	@Override
	public void onFacebookError(FacebookError e, Object state) {
		handleError(e, state);
	}

}
