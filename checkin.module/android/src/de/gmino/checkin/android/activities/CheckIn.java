package de.gmino.checkin.android.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import de.gmino.checkin.android.PlacesCheckInListener;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.domain.Shop;

public class CheckIn extends Activity {

	String[] permissions = { "offline_access", "publish_stream",
			"publish_checkins", "photo_upload" };

	Facebook facebook = new Facebook("349524965127236");
	private SharedPreferences mPrefs;
	ImageView shopsButton;
	ImageView QRButton;
	ImageView gutscheinButton;
	Intent intent;
	AsyncFacebookRunner mAsyncRunner;

	private NfcAdapter mNfcAdapter = null;
	private PendingIntent mNfcPendingIntent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		shopsButton = (ImageView) findViewById(R.id.button1);
		shopsButton.setOnClickListener(buttonListener1);
		QRButton = (ImageView) findViewById(R.id.button2);
		QRButton.setOnClickListener(buttonListener2);
		gutscheinButton = (ImageView) findViewById(R.id.button3);
		gutscheinButton.setOnClickListener(buttonListener3);

		mAsyncRunner = new AsyncFacebookRunner(facebook);
		/*
		 * Get existing access_token if any
		 */
		mPrefs = getPreferences(MODE_PRIVATE);
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

			logIn();
		}

		handleIntent(getIntent());
	}

	private void handleIntent(Intent intent) {
		Uri data = intent.getData();

		if (data != null) {

			String[] array = data.toString().split("/");
			String fid = array[array.length - 1];

			if (fid != null) {

				checkinWithFid(fid);
			}
		}
		if (intent.getCharSequenceExtra("fid") != null) {
			checkinWithFid(intent.getCharSequenceExtra("fid").toString());
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
		Log.d("de.gmino.checkin", "intent erhalten");
	}

	private void logIn() {
		facebook.authorize(this, permissions, new DialogListener() {
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

	private void checkinWithFid(final String fid) {
		if (fid != null) {
			/*
			 * new AsyncTask<Void, Void, Shop>() {
			 * 
			 * @Override protected Shop doInBackground(Void... params) { return
			 * CheckinQueries.getShopByFid(fid); }
			 * 
			 * @Override protected void onPostExecute(Shop shop) { Toast toast =
			 * Toast.makeText(getApplicationContext(), "Checke bei " +
			 * shop.getTitle() + " ein...", Toast.LENGTH_SHORT); toast.show();
			 * checkIn(shop); } }.execute();
			 */
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	public void makeLoggedInToast() {
		Toast toast = Toast.makeText(getApplicationContext(), "Logged In",
				Toast.LENGTH_SHORT);
		toast.show();
	}

	private OnClickListener buttonListener1 = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(CheckIn.this, ShopList.class);
			startActivity(intent);
		}
	};

	private OnClickListener buttonListener2 = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(CheckIn.this, QRScanner.class);
			startActivity(intent);
		}
	};

	private OnClickListener buttonListener3 = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(CheckIn.this, Coupons.class);
			startActivity(intent);
		}
	};

	public void checkIn(Shop shop) {

		Bundle params = new Bundle();

		params.putString("place", shop.getFacebookId());
		params.putString("message", "Ich bin hier");
		params.putString("coordinates", shop.getLocation().toString());
		mAsyncRunner.request("me/checkins", params, "POST",
				new PlacesCheckInListener(CheckIn.this, shop), null);
	}

	public void onResume() {
		super.onResume();
		facebook.extendAccessTokenIfNeeded(this, null);

	}
}
