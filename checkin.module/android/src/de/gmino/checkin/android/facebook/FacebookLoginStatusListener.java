package de.gmino.checkin.android.facebook;

public interface FacebookLoginStatusListener {
	public void onLoggedIn(String fid, String name);
	public void onLoggedOut();
}
