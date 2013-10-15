// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;

// gmino stuff
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.domain.Timestamp;
import de.gmino.geobase.shared.domain.PoiInterface;
import de.gmino.issuemap.client.domain.gen.PoiGen;
import de.gmino.meva.client.domain.KeyValueSet;
// default imports
// imports for JSON
// imports for field types
@SuppressWarnings("unused")
public class Poi extends PoiGen implements PoiInterface {
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
			boolean deleted,
			User creator)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.client.domain.LatLon)location,
			title,
			(de.gmino.issuemap.client.domain.Markertype)markertype,
			(de.gmino.meva.client.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.client.domain.Map)map_instance,
			(de.gmino.geobase.client.domain.Timestamp)creationTimestamp,
			(de.gmino.geobase.client.domain.Timestamp)resolvedTimestamp,
			rating,
			number_of_rating,
			marked,
			deleted,
			(de.gmino.issuemap.client.domain.User)creator
		);
		loadValueSet();
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
