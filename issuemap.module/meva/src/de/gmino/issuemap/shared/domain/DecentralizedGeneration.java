// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

// gmino stuff
// imports for field types
import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.PoiInterface;
import de.gmino.issuemap.shared.domain.gen.DecentralizedGenerationGen;
// default imports
// imports for JSON
@SuppressWarnings("unused")
public class DecentralizedGeneration extends DecentralizedGenerationGen implements PoiInterface {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public DecentralizedGeneration(long id)
	{
		super(id);
	}
	
	public DecentralizedGeneration(
			long id,
			boolean ready,
			LatLon location,
			Address address,
			String unitType,
			float power,
			String voltage,
			Map map_instance)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.shared.domain.LatLon)location,
			(de.gmino.geobase.shared.domain.Address)address,
			unitType,
			power,
			voltage,
			(de.gmino.issuemap.shared.domain.Map)map_instance
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT

	@Override
	public String getTitle() {
		if(unitType.equals("pv"))
			return "Photovoltaikanlage (" + address.getStreet()+")";
		else
			return "Windkraftanlage (" + address.getStreet()+")";
	}

	public String getDescription() {
		if(unitType.equals("pv"))
			return "Photovoltaikanlage " + voltage;
		else
			return "Windkraftanlage " + voltage;
	}
	
}
