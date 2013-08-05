// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.domain;

// gmino stuff
import de.gmino.geobase.client.domain.gen.GeoObjectGen;
// default imports
// imports for JSON
// imports for field types
public class GeoObject extends GeoObjectGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public GeoObject(long id)
	{
		super(id);
	}
	
	public GeoObject(
			long id,
			boolean ready,
			LatLon location,
			String title,
			String description,
			ImageUrl markerImage)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.client.domain.LatLon)location,
			title,
			description,
			(de.gmino.geobase.client.domain.ImageUrl)markerImage
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
