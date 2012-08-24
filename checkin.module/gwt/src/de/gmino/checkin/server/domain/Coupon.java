// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.domain;

// gmino stuff
import de.gmino.checkin.server.domain.gen.CouponGen;
import de.gmino.geobase.server.domain.Duration;
import de.gmino.geobase.server.domain.ImageUrl;
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
			(de.gmino.checkin.server.domain.Shop)shop,
			title,
			description,
			(de.gmino.geobase.server.domain.ImageUrl)image,
			(de.gmino.geobase.server.domain.Duration)duration
		);
	}
	

}
