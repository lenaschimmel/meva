package de.gmino.checkin.android.facebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonObject;
import org.itemscript.standard.StandardConfig;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.Facebook.ServiceListener;
import com.facebook.android.FacebookError;

import de.gmino.checkin.android.PlacesCheckInListener;
import de.gmino.checkin.android.WeakHashSet;
import de.gmino.checkin.android.meRequestListener;
import de.gmino.checkin.android.domain.Shop;

public class FacebookUtil {
	private static String[] permissions = { "offline_access", "publish_stream",
			"publish_checkins", "photo_upload" };

	private static Facebook facebook = new Facebook("349524965127236");
	private static SharedPreferences mPrefs;
	private static Activity activity;
	private static AsyncFacebookRunner mAsyncRunner;
	private static String fid = null;
	private static String name = null;
	private static Set<FacebookEventListener> listeners;

	static {
		listeners = new WeakHashSet<FacebookEventListener>();
	}

	public static boolean addFacebookEventListener(FacebookEventListener object) {
		return listeners.add(object);
	}

	public static boolean removeFacebookEventListener(Object object) {
		return listeners.remove(object);
	}

	public boolean isLoggedIn() {
		return facebook.isSessionValid();
	}

	public String getFid() {
		if (!isLoggedIn())
			throw new RuntimeException(
					"Can't provide an fid when the User is not logged in on Facebook.");
		return fid;
	}

	public static void ensureFacebookLogin(Activity activity) {
		FacebookUtil.activity = activity;

		if (mAsyncRunner == null)
			mAsyncRunner = new AsyncFacebookRunner(facebook);

		if (facebook.isSessionValid()) {
			System.out.println("Session already valid.");
			requestFid();
			return;
		}

		/*
		 * Get existing access_token if any
		 */
		mPrefs = activity.getSharedPreferences("facebook",
				Activity.MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		if (access_token != null) {
			facebook.setAccessToken(access_token);
			Log.d("de.gmino.checkin", "Token exist: " + access_token);
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

		requestFid();
	}

	private static void requestFid() {
		System.out.println("Requesting FID, current token: "
				+ facebook.getAccessToken());
		mAsyncRunner.request("me", new meRequestListener(activity) {
			@Override
			public void onComplete(String response, Object state) {
				JsonSystem json = StandardConfig.createSystem();
				JsonObject user = json.parse(response).asObject();
				String oldFid = fid;
				fid = user.getString("id");
				name = user.getString("name");

				System.out.println("Your FID: " + fid);
				if (!fid.equals(oldFid)) {
					for (FacebookEventListener listener : listeners) {
						listener.onLoggedIn(fid, name);
					}
				}
			}
		});
	}

	private static void showLoginDialog() {
		System.out.println("showLoginDialog/authorize");
		facebook.authorize(activity, permissions, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
				SharedPreferences.Editor editor = mPrefs.edit();
				editor.putString("access_token", facebook.getAccessToken());
				editor.putLong("access_expires", facebook.getAccessExpires());
				editor.commit();
				requestFid();
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

	public static void checkIn(String message, Shop shop) {
		Bundle params = new Bundle();

		params.putString("place", shop.getFacebookId());
		params.putString("message", message);
		params.putString("coordinates", shop.getLocation().toString());

		mAsyncRunner.request("me/checkins", params, "POST",
				new PlacesCheckInListener(activity, shop), null);
	}

	public static void extendAccessTokenIfNeeded() {
		boolean res = facebook.extendAccessTokenIfNeeded(activity,
				new ServiceListener() {

					@Override
					public void onFacebookError(FacebookError e) {
						System.out.println("extend onFacebookError: " + e);
					}

					@Override
					public void onError(Error e) {
						System.out.println("extend onError: " + e);
					}

					@Override
					public void onComplete(Bundle values) {
						System.out.println("extend onComplete");
						requestFid();
					}
				});
		System.out.println("extendAccessTokenIfNeeded: " + res);
	}

	public static void authorizeCallback(int requestCode, int resultCode,
			Intent data) {
		System.out.println("authorizeCallback");
		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	public static void logout()
	{
			mAsyncRunner.logout(activity, new RequestListener() {
				
				@Override
				public void onMalformedURLException(MalformedURLException e, Object state) {
					System.err.println("Facebookerror on log out: " + e);
				}
				
				@Override
				public void onIOException(IOException e, Object state) {
					System.err.println("Facebookerror on log out: " + e);
				}
				
				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state) {
					System.err.println("Facebookerror on log out: " + e);
				}
				
				@Override
				public void onFacebookError(FacebookError e, Object state) {
					System.err.println("Facebookerror on log out: " + e);
				}
				
				@Override
				public void onComplete(String response, Object state) {
					fid = null;
					for (FacebookEventListener listener : listeners) {
						listener.onLoggedOut();
					}
				}
			});
	}
}
