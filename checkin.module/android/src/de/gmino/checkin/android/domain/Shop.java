// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.domain;

// gmino stuff
import de.gmino.checkin.android.domain.gen.ShopGen;
import de.gmino.geobase.android.domain.LatLon;
// default imports
// imports for SQL stuff
// android
// imports for field types
public class Shop extends ShopGen {
	// Constructors
	public Shop(long id)
	{
		super(id);
	}

	public Shop(long id, boolean ready, LatLon location, String img,
			String title, String text, String facebookId) {
		super(id, ready, location, img, title, text, facebookId);
		// TODO Auto-generated constructor stub
	}
	


}
