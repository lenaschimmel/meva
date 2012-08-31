package de.gmino.checkin.android.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.checkin.android.facebook.FacebookUtil;
import de.gmino.checkin.android.request.QueryShopByCode;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CheckIn extends ActivityWithFacebook {

	Button btDiscover;
	Button btScan;
	Button btCoupons;
	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		btDiscover = (Button) findViewById(R.id.btDiscover);
		btDiscover.setOnClickListener(btDiscoverListener);
		btScan = (Button) findViewById(R.id.btScan);
		btScan.setOnClickListener(btScanListener);
		btCoupons = (Button) findViewById(R.id.btCoupons);
		btCoupons.setOnClickListener(btCouponsListener);

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
			String scanCode = array[array.length - 1];

			if (scanCode != null) {

				checkinWithScanCode(scanCode);
			}
		}
		if (intent.getCharSequenceExtra("scanCode") != null) {
			checkinWithScanCode(intent.getCharSequenceExtra("scanCode").toString());
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

	private void checkinWithScanCode(final String scanCode) {
		if (scanCode != null) {
			System.out.println("Got code: " + scanCode);
			QueryShopByCode q = new QueryShopByCode(scanCode);
			Requests.getLoadedEntitiesByQuery(Shop.type, q, new RequestListener<Shop>(){
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

	private OnClickListener btDiscoverListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(CheckIn.this, ShopList.class);
			startActivity(intent);
		}
	};

	private OnClickListener btScanListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(CheckIn.this, QRScanner.class);
			startActivity(intent);
		}
	};

	private OnClickListener btCouponsListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(CheckIn.this, Coupons.class);
			startActivity(intent);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_main, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.miLogout:
	        	FacebookUtil.logout();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
