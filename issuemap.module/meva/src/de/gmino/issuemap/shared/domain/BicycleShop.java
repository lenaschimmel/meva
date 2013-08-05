// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

// gmino stuff
import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.shared.domain.gen.BicycleShopGen;
// default imports
// imports for JSON
// imports for field types
public class BicycleShop extends BicycleShopGen implements Poi {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public BicycleShop(long id)
	{
		super(id);
	}
	
	public BicycleShop(
			long id,
			boolean ready,
			LatLon location,
			String title,
			String description,
			String email,
			String phone,
			String website,
			Address address,
			String openingHours,
			boolean repairService)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.shared.domain.LatLon)location,
			title,
			description,
			email,
			phone,
			website,
			(de.gmino.geobase.shared.domain.Address)address,
			openingHours,
			repairService
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
