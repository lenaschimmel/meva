

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/checkin.module/meva/src/de/gmino/checkin/shared/domain/Coupon.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.checkin.shared.domain.gen.CouponGen;
import de.gmino.geobase.shared.domain.Duration;
import de.gmino.geobase.shared.domain.ImageUrl;
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
			(de.gmino.checkin.shared.domain.Shop)shop,
			title,
			description,
			(de.gmino.geobase.shared.domain.ImageUrl)image,
			(de.gmino.geobase.shared.domain.Duration)duration
		);
	}
	

}
