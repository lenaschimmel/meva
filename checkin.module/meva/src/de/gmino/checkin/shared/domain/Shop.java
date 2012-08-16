// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import java.util.Collection;

import de.gmino.checkin.shared.domain.gen.ShopGen;
import de.gmino.geobase.shared.domain.LatLon;

public class Shop extends ShopGen {
	// Constructors
	public Shop(long id) {
		super(id);
	}

	public Shop(long id, boolean ready, LatLon location, String img,
			String title, String text, String facebookId) {
		super(id, ready, location, img, title, text, facebookId);
		// TODO Auto-generated constructor stub
	}

	public Collection<Coupon> getCoupons() {
		// This method
		return null;
	}

}
