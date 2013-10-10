// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

// gmino stuff
import de.gmino.geobase.server.domain.LatLon;
import de.gmino.geobase.server.domain.Timestamp;
import de.gmino.issuemap.server.domain.gen.PoiGen;
import de.gmino.meva.server.domain.KeyValueSet;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
// imports for field types
@SuppressWarnings("unused")
public class Poi extends PoiGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Poi(long id)
	{
		super(id);
	}
	
	public Poi(
			long id,
			boolean ready,
			LatLon location,
			String title,
			Markertype markertype,
			KeyValueSet keyvalueset,
			Map map_instance,
			Timestamp creationTimestamp,
			Timestamp resolvedTimestamp,
			int rating,
			int number_of_rating,
			boolean marked,
			boolean deleted)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.server.domain.LatLon)location,
			title,
			(de.gmino.issuemap.server.domain.Markertype)markertype,
			(de.gmino.meva.server.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.server.domain.Map)map_instance,
			(de.gmino.geobase.server.domain.Timestamp)creationTimestamp,
			(de.gmino.geobase.server.domain.Timestamp)resolvedTimestamp,
			rating,
			number_of_rating,
			marked,
			deleted
		);
		loadValueSet();
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
