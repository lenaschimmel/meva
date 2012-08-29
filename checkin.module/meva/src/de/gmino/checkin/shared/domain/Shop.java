// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.checkin.shared.domain.gen.ShopGen;
import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
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
			String scanCode,
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
			(de.gmino.geobase.shared.domain.LatLon)location,
			facebookId,
			scanCode,
			title,
			description,
			(de.gmino.geobase.shared.domain.ImageUrl)logo,
			(de.gmino.geobase.shared.domain.Address)shopAddress,
			(de.gmino.geobase.shared.domain.Address)billingAddress,
			(de.gmino.checkin.shared.domain.ShopAdmin)admin
		);
	}
	

}
