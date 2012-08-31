package de.gmino.checkin.android.facebook;

public interface FacebookEventListener {
	public void onLoggedIn(String fid, String name);
	public void onLoggedOut();
}
