// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

// gmino stuff
// imports for field types
import de.gmino.geobase.server.domain.Address;
import de.gmino.geobase.server.domain.LatLon;
import de.gmino.issuemap.server.domain.gen.ElectricalSubstationGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
@SuppressWarnings("unused")
public class ElectricalSubstation extends ElectricalSubstationGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public ElectricalSubstation(long id)
	{
		super(id);
	}
	
	public ElectricalSubstation(
			long id,
			boolean ready,
			LatLon location,
			String title,
			Address address,
			String unitType,
			String operator,
			int frequence,
			float high_voltage,
			float low_voltage,
			float consumption,
			Map map_instance)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.server.domain.LatLon)location,
			title,
			(de.gmino.geobase.server.domain.Address)address,
			unitType,
			operator,
			frequence,
			high_voltage,
			low_voltage,
			consumption,
			(de.gmino.issuemap.server.domain.Map)map_instance
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
