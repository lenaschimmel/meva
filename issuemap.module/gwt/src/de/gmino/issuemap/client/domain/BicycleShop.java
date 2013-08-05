// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;

// gmino stuff
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.shared.domain.Address;
import de.gmino.issuemap.client.domain.gen.BicycleShopGen;
// default imports
// imports for JSON
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
			(de.gmino.geobase.client.domain.LatLon)location,
			title,
			description,
			email,
			phone,
			website,
			(de.gmino.geobase.client.domain.Address)address,
			openingHours,
			repairService
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	

	public void preventNulls() {
		location = new de.gmino.geobase.shared.domain.LatLon(0,0);
		title = "";
		description = "";
		email = "";
		phone = "";
		website = "";
		openingHours = "";
		repairService = false;
		address = new Address();
	}
}
