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

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import de.gmino.checkin.android.PlacesCheckInListener;
import de.gmino.checkin.android.WeakHashSet;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.checkin.android.facebook.errorhandling.FacebookDialogListener;
import de.gmino.checkin.android.facebook.errorhandling.FacebookRequestListener;
import de.gmino.checkin.android.facebook.errorhandling.FacebookServiceListener;

/**
 * This class shall provide easy access to facebook functionality across
 * multiple activities, especially when your activity has no knowledge about the
 * actions performed by other activities before.
 * 
 * Each activity should call FacebookUtil.setActivity upon (re-)activation,
 * because FacebookUtil should always have a reference to some activity, and if
 * possible, this should be the currently active activity.
 * 
 * When FacebookUtil's setActivity is called for the first time, it tries to
 * load the access token and check its validity.
 * 
 * Most methods of this class won't work at all before you called setActivity.
 * Even after you called this method, there may be a short period of time (about
 * 1-2 seconds) when the methods don't work completely reliable - this is
 * because network access is still pending and we only have a rough estimate on
 * whether the access token is valid.
 * 
 * @author lena
 * 
 */
public class FacebookUtil {
	public enum State {
		LOGGED_OUT, PROBABLY_LOGGED_IN, UNITIALIZED, LOGGED_IN, TOKEN_EXPIRED;

		public boolean mightShowDialog() {
			return this != LOGGED_IN;
		}
	}

	private static String[] permissions = { "offline_access", "publish_stream",
			"publish_checkins", "photo_upload" };

	private static Facebook facebook = new Facebook("349524965127236");
	private static SharedPreferences mPrefs;
	private static Activity activity;
	private static AsyncFacebookRunner mAsyncRunner;
	private static String fid = null;
	private static String name = null;
	private static State state = State.UNITIALIZED;
	private static Set<FacebookLoginStatusListener> listeners;

	static {
		listeners = new WeakHashSet<FacebookLoginStatusListener>();
	}

	public static void setActivity(Activity a) {
		if (activity == null) {
			activity = a;
			if (mAsyncRunner == null)
				mAsyncRunner = new AsyncFacebookRunner(facebook);

			mPrefs = activity.getSharedPreferences("facebook",
					Activity.MODE_PRIVATE);
			String access_token = mPrefs.getString("access_token", null);
			if (access_token == null) {
				setState(State.LOGGED_OUT);
			} else {

				long expires = mPrefs.getLong("access_expires", 0);
				if (access_token != null) {
					facebook.setAccessToken(access_token);
				}
				if (expires != 0) {
					facebook.setAccessExpires(expires);
				}

				if (facebook.isSessionValid()) {
					setState(State.PROBABLY_LOGGED_IN);
					requestFid();
				} else
					setState(State.TOKEN_EXPIRED);
			}
		}
		activity = a;
	}

	public static boolean addFacebookEventListener(
			FacebookLoginStatusListener object) {
		return listeners.add(object);
	}

	public static boolean removeFacebookEventListener(Object object) {
		return listeners.remove(object);
	}

	public static String getFid() {
		if (state != State.LOGGED_IN)
			throw new RuntimeException(
					"Can't provide an fid when the User is not logged in on Facebook.");
		return fid;
	}

	public static void ensureFacebookLogin(Activity activity) {
		FacebookUtil.activity = activity;
		/*
		 * Only call authorize if the access_token has expired.
		 */
		if (!facebook.isSessionValid()) {
			System.err.println("INVALID session, now showing the dialog.");
			facebook.authorize(activity, new String[] {},
					new FacebookDialogListener() {
						@Override
						public void onComplete(Bundle values) {
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();
							requestFid();
						}
					});
		} else {
			System.err.println("VALID session.");
			requestFid();
		}
	}

	private static void requestFid() {
		if (!facebook.isSessionValid()) {
			System.err.println("Can't get FID while not logged in.");
			return;
		}
		System.out.println("Requesting FID, current token: "
				+ facebook.getAccessToken());
		mAsyncRunner.request("me", new FacebookRequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				JsonSystem json = StandardConfig.createSystem();
				JsonObject user = json.parse(response).asObject();
				if (checkResponse(user)) {
					String oldFid = fid;
					fid = user.getString("id");
					name = user.getString("name");

					if (fid == null) {
						System.err.println("fid is null, response was "
								+ response);
						return;
					}

					System.out.println("Your FID: " + fid);
					setState(State.LOGGED_IN);
					if (!fid.equals(oldFid)) {

						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								for (FacebookLoginStatusListener listener : listeners) {
									listener.onLoggedIn(fid, name);
								}

							}
						});
					}
				}
			}

			@Override
			public void handleError(Throwable innerCause) {
				super.handleError(innerCause);
				// seems like we are not logged in, at least not perfectly.
				handleLoggedOutCondition();
			}
		});
	}

	private static void setState(State newState) {
		if (state != newState) {
			System.out.println("State has changed from " + state + " to " + newState);
			state = newState;
			if (activity != null)
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						for (FacebookLoginStatusListener listener : listeners) {
							System.out.println("Notifying " + listener);
							listener.onStateChanged(state);
						}
					}
				});
		}
	}

	private static void showLoginDialog() {
		System.out.println("showLoginDialog/authorize");
		facebook.authorize(activity, permissions, new FacebookDialogListener() {
			@Override
			public void onComplete(Bundle values) {
				SharedPreferences.Editor editor = mPrefs.edit();
				editor.putString("access_token", facebook.getAccessToken());
				editor.putLong("access_expires", facebook.getAccessExpires());
				editor.commit();
				requestFid();
				setState(State.PROBABLY_LOGGED_IN);
				Log.d("logged_in", "logged in");
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
				new FacebookServiceListener() {
					@Override
					public void onComplete(Bundle values) {
						System.out.println("extend onComplete");
						setState(State.PROBABLY_LOGGED_IN);
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

	public static void logout() {
		mAsyncRunner.logout(activity, new FacebookRequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				handleLoggedOutCondition();
			}
		});
	}

	public static void handleLoggedOutCondition() {
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.remove("access_token");
		editor.remove("access_expires");
		editor.commit();
		facebook.setAccessToken(null);
		facebook.setAccessExpires(0);
		fid = null;
		setState(State.LOGGED_OUT);
	}
	
	public static State getState()
	{
		return state;
	}
}
