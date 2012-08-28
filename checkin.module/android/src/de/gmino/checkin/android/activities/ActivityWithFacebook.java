package de.gmino.checkin.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import de.gmino.checkin.android.FacebookUtil;

public class ActivityWithFacebook extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookUtil.ensureFacebookLogin(this);

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		FacebookUtil.authorizeCallback(requestCode, resultCode, data);
	}
	

	public void onResume() {
		super.onResume();
		FacebookUtil.extendAccessTokenIfNeeded();
	}
}
