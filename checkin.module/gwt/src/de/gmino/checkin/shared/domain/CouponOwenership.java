

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/checkin.module/meva/src/de/gmino/checkin/shared/domain/CouponOwenership.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.checkin.shared.domain.gen.CouponOwenershipGen;
import de.gmino.geobase.shared.domain.Timestamp;
public class CouponOwenership extends CouponOwenershipGen {
	// Constructors
	public CouponOwenership(long id)
	{
		super(id);
	}
	
	public CouponOwenership(
			long id,
			boolean ready,
			Timestamp acquired,
			Timestamp invalidated,
			Consumer consumer,
			Coupon coupon)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.shared.domain.Timestamp)acquired,
			(de.gmino.geobase.shared.domain.Timestamp)invalidated,
			(de.gmino.checkin.shared.domain.Consumer)consumer,
			(de.gmino.checkin.shared.domain.Coupon)coupon
		);
	}
	

}
