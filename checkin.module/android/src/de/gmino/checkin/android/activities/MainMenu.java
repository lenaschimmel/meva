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

public class MainMenu extends ActivityWithFacebook {

	private Button btDiscover;
	private Button btScan;
	private Button btCoupons;
	private Button btSettings;

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
		btSettings = (Button) findViewById(R.id.btSettings);
		btSettings.setOnClickListener(btSettingsListener);
	}

	private OnClickListener btDiscoverListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(MainMenu.this, ShopList.class);
			startActivity(intent);
		}
	};

	private OnClickListener btScanListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(MainMenu.this, QRScanner.class);
			startActivity(intent);
		}
	};

	private OnClickListener btCouponsListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(MainMenu.this, CheckinProgress.class);
			intent.setData(Uri.parse("http://gmino.de/qr/c/test/nQA1JWbi"));
			startActivity(intent);
		}
	};

	private OnClickListener btSettingsListener = new OnClickListener() {
		public void onClick(View v) {
			openOptionsMenu();
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
