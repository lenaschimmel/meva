// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.client.domain;

// gmino stuff
import de.gmino.checkin.client.domain.gen.CouponOwenershipGen;
import de.gmino.geobase.client.domain.Timestamp;

public class CouponOwenership extends CouponOwenershipGen {
	// Constructors
	public CouponOwenership(long id) {
		super(id);
	}

	public CouponOwenership(long id, boolean ready, Timestamp acquired, Timestamp invalidated, Consumer consumer, Coupon coupon) {
		super(id, ready, (de.gmino.geobase.client.domain.Timestamp) acquired, (de.gmino.geobase.client.domain.Timestamp) invalidated, (de.gmino.checkin.client.domain.Consumer) consumer,
				(de.gmino.checkin.client.domain.Coupon) coupon);
	}

}
