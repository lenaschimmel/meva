// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.domain;

// gmino stuff
import de.gmino.geobase.ios.domain.LatLon;
import de.gmino.geobase.ios.domain.Timestamp;
import de.gmino.issuemap.ios.domain.gen.PoiGen;
import de.gmino.meva.ios.domain.KeyValueSet;
// default imports
// imports for JSON
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
			int rating,
			int number_of_rating,
			boolean marked,
			boolean deleted)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.ios.domain.LatLon)location,
			title,
			(de.gmino.issuemap.ios.domain.Markertype)markertype,
			(de.gmino.meva.ios.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.ios.domain.Map)map_instance,
			(de.gmino.geobase.ios.domain.Timestamp)creationTimestamp,
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
