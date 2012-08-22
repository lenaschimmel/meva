package de.gmino.checkin.android.activities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.domain.Coupon;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.checkin.android.request.QueryNearbyShops;
import de.gmino.geobase.android.domain.LatLon;
import de.gmino.meva.android.request.RequestEntitiesByIds;
import de.gmino.meva.android.request.RequestEntititesByQuery;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.ReturnEntityPolicy;

public class ShopList extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_list);

		// Request all shops near you
		final LatLon myLocation = new LatLon(52.2723, 10.53547);
		Query q = new QueryNearbyShops(myLocation, 5000, 20);
		new RequestEntititesByQuery<Shop>(this, q, Shop.class, ReturnEntityPolicy.BLOCK_IF_NEEDED) {
			protected void onFinishOnUi(Collection<Shop> results) {
				// prepare a set of all the Coupons that we should pre-load, because the shops may contain unloaded coupons.
				Set<Coupon> couponsToLoad = new HashSet<Coupon>();
				
				// put the shops into a list of Strings 
				String[] arr = new String[results.size()];
				int i = 0;
				for (Shop shop : results) {
					arr[i++] = shop.getTitle() + "("+shop.getLocation().getDistanceTo(myLocation)+" entfernt)";
					// TODO: Can't add this because of inheritance problems.
					// couponsToLoad.addAll(shop.getCoupons());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						ShopList.this, R.layout.shoplistitem,
						R.id.textView_List, arr);
				setListAdapter(adapter);
				
				// TODO: Need an asynchronous wrapper for that. Both of those variants are synchronous: 
				//EntityFactory.loadEntities(couponsToLoad);
				//new RequestEntitiesByIds<Entity>(null, null) {		};
				
			}

			protected void onErrorOnUi(String message) {
				Toast.makeText(ShopList.this, message, Toast.LENGTH_LONG)
						.show();
			}
		}.start();
	
		/*
		System.out.println("I STARTED DA DANG!");

		new RequestNewEntities<Consumer>("Consumer", 1) {
			protected void onFinish(java.util.Collection<Consumer> results) {
				Consumer c = results.iterator().next();
				c.setFacebookId("testId");
				System.out.println(c);

				EntityFactory.saveEntity(c);
			};

			@Override
			protected void onFinishOnUi(Collection<Consumer> results) {
				Consumer c = results.iterator().next();
				Toast.makeText(ShopList.this, c.toString(), Toast.LENGTH_LONG)
						.show();
			}
		}.start();
*/
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

}