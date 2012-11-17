// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.client.domain;

// gmino stuff
import de.gmino.checkin.client.domain.gen.ShopGen;
import de.gmino.checkin.shared.domain.Coupon;
import de.gmino.geobase.client.domain.Address;
import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.client.domain.LatLon;

public class Shop extends ShopGen {
	// Constructors
	public Shop(long id) {
		super(id);
	}

	public Shop(long id, boolean ready, LatLon location, String facebookId, String scanCode, String title, String description, ImageUrl logo, Address shopAddress, Address billingAddress,
			ShopAdmin admin, Coupon currentCoupon) {
		super(id, ready, (de.gmino.geobase.client.domain.LatLon) location, facebookId, scanCode, title, description, (de.gmino.geobase.client.domain.ImageUrl) logo,
				(de.gmino.geobase.client.domain.Address) shopAddress, (de.gmino.geobase.client.domain.Address) billingAddress, (de.gmino.checkin.client.domain.ShopAdmin) admin,
				(de.gmino.checkin.client.domain.Coupon) currentCoupon);
	}

}
