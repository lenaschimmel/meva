// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.domain;

// gmino stuff
import de.gmino.checkin.android.domain.gen.CouponGen;
import de.gmino.geobase.android.domain.DateSpan;
// default imports
// imports for SQL stuff
// android
// imports for field types

public class Coupon extends CouponGen {
	// Constructors
	public Coupon(long id) {
		super(id);
	}

	public Coupon(long id, boolean ready, Shop shopWhichIssues,
			Shop shopWhichAccepts, String img, String title, String text,
			short neededVisits, DateSpan validty) {
		super(id, ready, shopWhichIssues, shopWhichAccepts, img, title, text,
				neededVisits, validty);
	}
}
