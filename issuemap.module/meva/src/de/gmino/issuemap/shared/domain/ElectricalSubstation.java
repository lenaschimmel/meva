// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

// gmino stuff
// imports for field types
import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.PoiInterface;
import de.gmino.issuemap.shared.domain.gen.ElectricalSubstationGen;
// default imports
// imports for JSON
@SuppressWarnings("unused")
public class ElectricalSubstation extends ElectricalSubstationGen implements PoiInterface {
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
			(de.gmino.geobase.shared.domain.LatLon)location,
			title,
			(de.gmino.geobase.shared.domain.Address)address,
			unitType,
			operator,
			frequence,
			high_voltage,
			low_voltage,
			consumption,
			(de.gmino.issuemap.shared.domain.Map)map_instance
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
