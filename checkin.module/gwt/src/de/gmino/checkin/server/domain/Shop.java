// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.domain;

// gmino stuff
import de.gmino.checkin.server.domain.gen.ShopGen;
import de.gmino.checkin.shared.domain.Coupon;
import de.gmino.geobase.server.domain.Address;
import de.gmino.geobase.server.domain.ImageUrl;
import de.gmino.geobase.server.domain.LatLon;

public class Shop extends ShopGen {
	// Constructors
	public Shop(long id) {
		super(id);
	}

	public Shop(long id, boolean ready, LatLon location, String facebookId, String scanCode, String title, String description, ImageUrl logo, Address shopAddress, Address billingAddress,
			ShopAdmin admin, Coupon currentCoupon) {
		super(id, ready, (de.gmino.geobase.server.domain.LatLon) location, facebookId, scanCode, title, description, (de.gmino.geobase.server.domain.ImageUrl) logo,
				(de.gmino.geobase.server.domain.Address) shopAddress, (de.gmino.geobase.server.domain.Address) billingAddress, (de.gmino.checkin.server.domain.ShopAdmin) admin,
				(de.gmino.checkin.server.domain.Coupon) currentCoupon);
	}

}
