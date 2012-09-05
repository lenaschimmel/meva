package de.gmino.checkin.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.TileLayout;

public class ShopList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_list);

		TileLayout tiles = new TileLayout(this);
		for (int i = 0; i < 20; i++) {
			Button button = new Button(this);
			button.setText("Button " + i);
			tiles.addView(button);
		}
		ScrollView svList = (ScrollView) findViewById(R.id.svList);
		svList.addView(tiles);

		// Request all shops near you
		// final LatLon myLocation = new LatLon(52.2723, 10.53547);
		// Query q = new QueryNearbyShops(myLocation, 5000, 20);
		// Requests.getLoadedEntitiesByQuery(Shop.type, q, new
		// RequestListener<Shop>() {
		// @Override
		// public void onFinished(Collection<Shop> results) {
		// // prepare a set of all the Coupons that we should pre-load, because
		// the shops may contain unloaded coupons.
		// Set<Coupon> couponsToLoad = new HashSet<Coupon>();
		//
		// // put the shops into a list of Strings
		// String[] arr = new String[results.size()];
		// int i = 0;
		// for (Shop shop : results) {
		// arr[i++] = shop.getTitle() +
		// "("+shop.getLocation().getDistanceTo(myLocation)+" entfernt)";
		// couponsToLoad.addAll(shop.getCoupons());
		// }
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// ShopList.this, R.layout.shoplistitem,
		// R.id.textView_List, arr);
		//
		// Requests.loadEntities(couponsToLoad, new RequestListener<Coupon>() {
		// @Override
		// public void onFinished(Collection<Coupon> coupons) {
		// StringBuilder sb = new StringBuilder("Coupons: ");
		// boolean first = true;
		// for(Coupon c : coupons)
		// {
		// if(!first)
		// sb.append(", ");
		// sb.append(c.getTitle());
		// first = false;
		// }
		// Toast.makeText(ShopList.this, sb.toString(),
		// Toast.LENGTH_LONG).show();
		// }
		// });
		// }
		//
		// @Override
		// public void onError(String message, Throwable e) {
		// Toast.makeText(ShopList.this, message, Toast.LENGTH_LONG)
		// .show();
		// }
		// });
		//

		/*
		 * System.out.println("I STARTED DA DANG!");
		 * 
		 * new RequestNewEntities<Consumer>("Consumer", 1) { protected void
		 * onFinish(java.util.Collection<Consumer> results) { Consumer c =
		 * results.iterator().next(); c.setFacebookId("testId");
		 * System.out.println(c);
		 * 
		 * EntityFactory.saveEntity(c); };
		 * 
		 * @Override protected void onFinishOnUi(Collection<Consumer> results) {
		 * Consumer c = results.iterator().next(); Toast.makeText(ShopList.this,
		 * c.toString(), Toast.LENGTH_LONG) .show(); } }.start();
		 */
	}

}