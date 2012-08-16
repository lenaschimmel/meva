

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/checkin.module/meva/src/de/gmino/checkin/shared/domain/Shop.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import java.util.Collection;

import de.gmino.checkin.shared.domain.gen.ShopGen;
import de.gmino.geobase.shared.domain.LatLon;
public class Shop extends ShopGen implements Comparable<Shop>{
	// Constructors
	public Shop(long id)
	{
		super(id);
	}
	
	public Shop(
			long id,
			boolean ready,
			LatLon location,
			String img,
			String title,
			String text,
			String owner,
			Shop neighbour,
			String facebookId)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.shared.domain.LatLon)location,
			img,
			title,
			text,
			owner,
			(de.gmino.checkin.shared.domain.Shop)neighbour,
			facebookId
		);
	}
	
	public Collection<Coupon> getCoupons()
	{
		// This method  
		return null;
	}
	

}
