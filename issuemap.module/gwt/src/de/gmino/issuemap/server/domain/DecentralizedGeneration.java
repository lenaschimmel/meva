// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

// gmino stuff
import de.gmino.geobase.server.domain.Address;
import de.gmino.geobase.server.domain.LatLon;
import de.gmino.issuemap.server.domain.gen.DecentralizedGenerationGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
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
			(de.gmino.geobase.server.domain.LatLon)location,
			(de.gmino.geobase.server.domain.Address)address,
			unitType,
			power,
			voltage,
			(de.gmino.issuemap.server.domain.Map)map_instance
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	//blaub
}
