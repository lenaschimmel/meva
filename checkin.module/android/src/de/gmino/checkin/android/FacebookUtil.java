package de.gmino.checkin.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import de.gmino.checkin.android.domain.Shop;

public class FacebookUtil {
	private static String[] permissions = { "offline_access", "publish_stream",
			"publish_checkins", "photo_upload" };

	private static Facebook facebook = new Facebook("349524965127236");
	private static  SharedPreferences mPrefs;
	private static Activity activity;
	private static AsyncFacebookRunner mAsyncRunner;

	
	public static void ensureFacebookLogin(Activity activity) {
		FacebookUtil.activity = activity;
		
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		/*
		 * Get existing access_token if any
		 */
		mPrefs = activity.getSharedPreferences("facebook", Activity.MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		if (access_token != null) {
			facebook.setAccessToken(access_token);
			Log.d("de.gmino.checkin", "Token exist");
		}
		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}
	
		/*
		 * Only call authorize if the access_token has expired.
		 */
		if (!facebook.isSessionValid()) {
			showLoginDialog();
		}
	}
	
	private static void showLoginDialog() {
		facebook.authorize(activity, permissions, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
				SharedPreferences.Editor editor = mPrefs.edit();
				editor.putString("access_token", facebook.getAccessToken());
				editor.putLong("access_expires", facebook.getAccessExpires());
				editor.commit();
				makeLoggedInToast();
				Log.d("logged_in", "logged in");
			}

			@Override
			public void onFacebookError(FacebookError error) {
			}

			@Override
			public void onError(DialogError e) {
				Log.d("error", "error");
			}

			@Override
			public void onCancel() {
			}
		});
	}
	

	private static void makeLoggedInToast() {
		Toast toast = Toast.makeText(activity, "Logged In",
				Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void checkIn(String message, Shop shop) {
		Bundle params = new Bundle();

		params.putString("place", shop.getFacebookId());
		params.putString("message", message);
		params.putString("coordinates", shop.getLocation().toString());
	
		mAsyncRunner.request("me/checkins", params, "POST",
				new PlacesCheckInListener(activity, shop), null);
	}

	public static void extendAccessTokenIfNeeded() {
		facebook.extendAccessTokenIfNeeded(activity, null);
	}

	public static void authorizeCallback(int requestCode, int resultCode,
			Intent data) {
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

}
