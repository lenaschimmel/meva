package de.gmino.checkin.android.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import de.gmino.checkin.android.FacebookUtil;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CheckIn extends ActivityWithFacebook {

	ImageView shopsButton;
	ImageView QRButton;
	ImageView gutscheinButton;
	Intent intent;

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

		handleIntent(getIntent());
	}

	/**
	 * Handles incoming intents, no matter if the activity was just created or
	 * has got a new intent afterwards.
	 * 
	 * Searches both data and extra of the intent for an fid. Data should
	 * contain the fid within an URL, but extras should contain a plain fid.
	 * 
	 * @param intent
	 */
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

	/**
	 * Called when the activity is already running an another intent comes in.
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
		Log.d("de.gmino.checkin", "intent erhalten");
	}

	private void checkinWithFid(final String fid) {
		if (fid != null) {
			Long id = Long.parseLong(fid);
			Requests.getLoadedEntityById(Shop.type, id, new RequestListener<Shop>(){
				@Override
				public void onNewResult(Shop shop) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Checke bei " + shop.getTitle() + " ein...",
							Toast.LENGTH_SHORT);
					toast.show();
					FacebookUtil.checkIn("Ich bin hier!", shop);
				}
			});
		}
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


}
