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
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

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
		Requests.getLoadedEntitiesByQuery(Shop.type, q, new RequestListener<Shop>() {
			@Override
			public void onFinished(Collection<Shop> results) {
				// prepare a set of all the Coupons that we should pre-load, because the shops may contain unloaded coupons.
				Set<Coupon> couponsToLoad = new HashSet<Coupon>();
				
				// put the shops into a list of Strings 
				String[] arr = new String[results.size()];
				int i = 0;
				for (Shop shop : results) {
					arr[i++] = shop.getTitle() + "("+shop.getLocation().getDistanceTo(myLocation)+" entfernt)";
					couponsToLoad.addAll(shop.getCoupons());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						ShopList.this, R.layout.shoplistitem,
						R.id.textView_List, arr);
				setListAdapter(adapter);
				
				Requests.loadEntities(couponsToLoad, new RequestListener<Coupon>() {
					@Override
					public void onFinished(Collection<Coupon> coupons) {
						StringBuilder sb = new StringBuilder("Coupons: ");
						boolean first = true;
						for(Coupon c : coupons)
						{
							if(!first)
								sb.append(", ");
							sb.append(c.getTitle());
							first = false;
						}
						Toast.makeText(ShopList.this, sb.toString(), Toast.LENGTH_LONG).show();
					}
				});
			}
			
			@Override
			public void onError(String message, Throwable e) {
				Toast.makeText(ShopList.this, message, Toast.LENGTH_LONG)
				.show();
			}
		});
		
	
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