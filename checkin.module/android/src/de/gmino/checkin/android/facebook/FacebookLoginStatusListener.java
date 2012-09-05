package de.gmino.checkin.android.facebook;

import de.gmino.checkin.android.facebook.FacebookUtil.State;

public interface FacebookLoginStatusListener {
	public void onLoggedIn(String fid, String name);
	public void onStateChanged(State state);
}
