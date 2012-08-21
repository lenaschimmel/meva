// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.domain;

// gmino stuff
import de.gmino.checkin.android.domain.gen.CouponGen;
import de.gmino.geobase.android.domain.Duration;
import de.gmino.geobase.android.domain.ImageUrl;
public class Coupon extends CouponGen {
	// Constructors
	public Coupon(long id)
	{
		super(id);
	}
	
	public Coupon(
			long id,
			boolean ready,
			Shop shop,
			String title,
			String description,
			ImageUrl image,
			Duration duration)
	{
		super(
			id,
			ready,
			(de.gmino.checkin.android.domain.Shop)shop,
			title,
			description,
			(de.gmino.geobase.android.domain.ImageUrl)image,
			(de.gmino.geobase.android.domain.Duration)duration
		);
	}
	

}
