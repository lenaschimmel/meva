// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

// gmino stuff
import de.gmino.geobase.server.domain.LatLon;
import de.gmino.geobase.shared.domain.Address;
import de.gmino.issuemap.server.domain.gen.BicycleShopGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
// imports for field types
public class BicycleShop extends BicycleShopGen {
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
			(de.gmino.geobase.server.domain.LatLon)location,
			title,
			description,
			email,
			phone,
			website,
			(de.gmino.geobase.server.domain.Address)address,
			openingHours,
			repairService
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
