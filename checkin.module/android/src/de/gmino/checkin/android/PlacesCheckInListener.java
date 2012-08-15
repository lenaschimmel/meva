package de.gmino.checkin.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;

import de.gmino.checkin.android.activities.CheckIn;
import de.gmino.checkin.android.activities.Coupons;
import de.gmino.checkin.android.domain.Shop;

public class PlacesCheckInListener implements RequestListener {
	
	CheckIn context;
	Shop shop;
	public PlacesCheckInListener(CheckIn context, Shop shop) {
		super();
		this.shop = shop;
		this.context = context;
	}

	@Override
	public void onComplete(String response, Object state) {
		// TODO Auto-generated method stub
        Intent intent = new Intent(this.context, Coupons.class);
        intent.putExtra("shopId", shop.getId());
        context.startActivity(intent);

    	context.runOnUiThread(new Runnable(){

			@Override
			public void run() {
		    	Toast toast = Toast.makeText(context, "Checked in!", Toast.LENGTH_SHORT);
		    	toast.show();				
			}});

	}

	@Override
	public void onIOException(IOException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileNotFoundException(FileNotFoundException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMalformedURLException(MalformedURLException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFacebookError(FacebookError e, Object state) {
			Log.d("FBerror", e.toString());
	}

}
