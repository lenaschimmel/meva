package de.gmino.checkin.android.activities;

import java.util.Collection;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.domain.Consumer;
import de.gmino.checkin.android.domain.Shop;
import de.gmino.checkin.android.request.QueryNearbyShops;
import de.gmino.geobase.android.domain.LatLon;
import de.gmino.meva.android.request.RequestEntititesByQuery;
import de.gmino.meva.android.request.RequestNewEntities;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Query;


public class ShopList extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_list);
	
		LatLon myLocation = new LatLon(52.2723, 10.53547);
		Query q = new QueryNearbyShops(myLocation, 5000, 20);

		new RequestEntititesByQuery<Shop>(this, q, Shop.class) {
		   protected void onFinishOnUi(Collection<Shop> results) {
			   String[] arr = new String[results.size()];
			   int i = 0;
			   for(Shop s : results)
			   {
				   arr[i++] = s.toString();
			   }
				ArrayAdapter<String> adapter = 
						new ArrayAdapter<String>(ShopList.this, R.layout.shoplistitem,R.id.textView_List, arr);
				setListAdapter(adapter);			   
		   }
		   
		   protected void onErrorOnUi(String message) {
			   Toast.makeText(ShopList.this, message, Toast.LENGTH_LONG).show();
		   }
		}.start();
		
		
		new RequestNewEntities<Consumer>("Consumer", 1) {@Override
		protected void onFinishOnUi(Collection<Consumer> results) {
			Consumer c = results.iterator().next();
			c.setFacebookId("testId");
			System.out.println(c);
			Toast.makeText(ShopList.this, c.toString(), Toast.LENGTH_LONG).show();
		}}.start();
		
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