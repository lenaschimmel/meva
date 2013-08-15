// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;

// gmino stuff
import de.gmino.geobase.client.domain.Address;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.issuemap.client.domain.gen.DecentralizedGenerationGen;
// default imports
// imports for JSON
// imports for field types
@SuppressWarnings("unused")
public class DecentralizedGeneration extends DecentralizedGenerationGen {
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
			(de.gmino.geobase.client.domain.LatLon)location,
			(de.gmino.geobase.client.domain.Address)address,
			unitType,
			power,
			voltage,
			(de.gmino.issuemap.client.domain.Map)map_instance
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	public int getPowerLevel()
	{
		return (int)(2 * Math.log10(getPower())) + 1;
	}
}
