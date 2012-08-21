// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.domain;

// gmino stuff
import de.gmino.checkin.android.domain.gen.ShopGen;
import de.gmino.geobase.android.domain.Address;
import de.gmino.geobase.android.domain.ImageUrl;
import de.gmino.geobase.android.domain.LatLon;
public class Shop extends ShopGen {
	// Constructors
	public Shop(long id)
	{
		super(id);
	}
	
	public Shop(
			long id,
			boolean ready,
			LatLon location,
			String facebookId,
			String title,
			String description,
			ImageUrl logo,
			Address shopAddress,
			Address billingAddress,
			ShopAdmin admin)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.android.domain.LatLon)location,
			facebookId,
			title,
			description,
			(de.gmino.geobase.android.domain.ImageUrl)logo,
			(de.gmino.geobase.android.domain.Address)shopAddress,
			(de.gmino.geobase.android.domain.Address)billingAddress,
			(de.gmino.checkin.android.domain.ShopAdmin)admin
		);
	}
	

}
