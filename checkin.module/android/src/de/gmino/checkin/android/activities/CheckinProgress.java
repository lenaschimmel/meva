package de.gmino.checkin.android.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import de.gmino.checkin.android.PlacesCheckInListener;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.domain.Coupon;
import de.gmino.checkin.android.domain.CouponOwenership;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.checkin.android.facebook.FacebookUtil;
import de.gmino.checkin.android.request.QueryShopByCode;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CheckinProgress extends ActivityWithFacebook {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin_progress);

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

	@Override
	public void onResume() {
		super.onResume();
		handleIntent(getIntent());
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
			((Button) findViewById(R.id.btState1)).setActivated(true);
			System.out.println("Got code: " + scanCode);
			QueryShopByCode q = new QueryShopByCode(scanCode);
			Requests.getLoadedEntitiesByQuery(Shop.type, q, new RequestListener<Shop>() {
				@Override
				public void onNewResult(final Shop shop) {
					System.out.println("Received shop, checking in...");
					((Button) findViewById(R.id.btState2)).setActivated(true);
					Toast toast = Toast.makeText(getApplicationContext(), "Checke bei " + shop.getTitle() + " ein...", Toast.LENGTH_SHORT);
					toast.show();
					FacebookUtil.checkIn("Ich bin hier!", shop, new PlacesCheckInListener(CheckinProgress.this, shop) {
						public void onComplete(String response, Object state) {
							System.out.println("Completed checking in.");
							super.onComplete(response, state);
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									((Button) findViewById(R.id.btState3)).setActivated(true);
									
								}
							});
							Query checkinQuery = null;
							Requests.getLoadedEntitiesByQuery(CouponOwenership.type, checkinQuery, new RequestListener<CouponOwenership>() {
								@Override
								public void onNewResult(CouponOwenership own) {
									Coupon c = (Coupon) own.getCoupon();
									Requests.loadEntity(c, new RequestListener<Coupon>() {
										public void onNewResult(Coupon result) {
											System.out.println("New Coupon: " + result.getTitle());
											Intent intent = new Intent(CheckinProgress.this, Coupons.class);
											intent.putExtra("shopId", shop.getId());
											startActivity(intent);
										};
									});
								}
							});
						}
					});
				}
			});
		}
	}
}
