package de.gmino.checkin.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.facebook.FacebookLoginStatusListener;
import de.gmino.checkin.android.facebook.FacebookUtil;
import de.gmino.checkin.android.facebook.FacebookUtil.State;

public class Login extends Activity implements OnClickListener, FacebookLoginStatusListener {
	private Button btLogin;
	private Button btNearbyShops;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookUtil.addFacebookEventListener(this);
		FacebookUtil.setActivity(this);
		if (FacebookUtil.getState() == State.LOGGED_IN) {
			startMainMenu();
			return;
		}
		else
		{
			setContentView(R.layout.login);
			btNearbyShops = (Button)findViewById(R.id.btNearbyShops);
			btLogin = (Button)findViewById(R.id.btLogin);
			btLogin.setOnClickListener(this);
		}
	}

	private void startMainMenu() {
		Intent intent = new Intent(this, MainMenu.class);
		startActivity(intent);
		finish();
	}

	private void startNearbyShops() {
		Intent intent = new Intent(this, ShopList.class);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) {
		if(v == btLogin)
		{
			FacebookUtil.addFacebookEventListener(this);
			FacebookUtil.ensureFacebookLogin(this);
		}		
		if(v == btNearbyShops)
		{
			startNearbyShops();
		}
		
	}

	@Override
	public void onLoggedIn(String fid, String name) {
		startMainMenu();
	}

	@Override
	public void onStateChanged(State state) {
		// TODO Auto-generated method stub
	}
}
