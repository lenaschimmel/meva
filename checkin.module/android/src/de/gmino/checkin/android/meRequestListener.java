package de.gmino.checkin.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;

import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;

import de.gmino.checkin.android.activities.CheckIn;

public class meRequestListener implements RequestListener {

	CheckIn context;
	public meRequestListener(CheckIn context) {
		super();
		this.context = context;
	}

	@Override
	public void onComplete(final String response, Object state) {
		((Activity)context).runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
		//		context.text.setText(response);
			}
		});

	}

	@Override
	public void onIOException(IOException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileNotFoundException(FileNotFoundException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMalformedURLException(MalformedURLException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFacebookError(FacebookError e, Object state) {
		// TODO Auto-generated method stub

	}

}
