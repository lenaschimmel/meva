package de.gmino.checkin.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import de.gmino.checkin.android.R;
import de.gmino.checkin.android.facebook.FacebookLoginStatusListener;
import de.gmino.checkin.android.facebook.FacebookUtil;
import de.gmino.checkin.android.facebook.FacebookUtil.State;

public class Start extends Activity implements FacebookLoginStatusListener {
	private Handler handlerForTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handlerForTimer = new Handler();
		FacebookUtil.addFacebookEventListener(this);
		FacebookUtil.setActivity(this);
		if (FacebookUtil.getState() == State.LOGGED_IN)
			startMainMenu();
		else {
			setContentView(R.layout.start);
			startTimer();
		}
	}

	private void startTimer() {
		handlerForTimer.postDelayed(new Runnable() {
			@Override
			public void run() {
				startLogin();
			}
		}, 2500);
	}

	private void startMainMenu() {
		Intent intent = new Intent(this, MainMenu.class);
		startActivity(intent);
		finish();
	}

	private void startLogin() {
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onLoggedIn(String fid, String name) {
	}

	@Override
	public void onStateChanged(State state) {
		if (state == State.LOGGED_IN)
			startMainMenu();
		if (state == State.LOGGED_OUT)
			startLogin();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (FacebookUtil.getState() == State.LOGGED_IN)
			startMainMenu();
		else
			startTimer();
	}

	@Override
	protected void onPause() {
		// Stop all timer tasks.
		handlerForTimer.removeCallbacksAndMessages(null);
		super.onPause();
	}
}
