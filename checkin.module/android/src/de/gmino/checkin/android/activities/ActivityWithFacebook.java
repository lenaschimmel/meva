package de.gmino.checkin.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import de.gmino.checkin.android.facebook.FacebookEventListener;
import de.gmino.checkin.android.facebook.FacebookUtil;

public class ActivityWithFacebook extends Activity implements FacebookEventListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("onCreate");
		super.onCreate(savedInstanceState);
		FacebookUtil.addFacebookEventListener(this);
		FacebookUtil.ensureFacebookLogin(this);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		FacebookUtil.authorizeCallback(requestCode, resultCode, data);
	}
	

	public void onResume() {
		System.out.println("onResume");
		super.onResume();
		FacebookUtil.extendAccessTokenIfNeeded();
	}

	@Override
	public void onLoggedIn(String fid, String name) {
		Toast.makeText(this, "Du bist eingeloggt, " + name, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLoggedOut() {
		Toast.makeText(this, "Du bist ausgeloggt.", Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onDestroy() {
		FacebookUtil.removeFacebookEventListener(this);
		super.onDestroy();
	}
}
