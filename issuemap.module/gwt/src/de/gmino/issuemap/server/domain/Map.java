// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

// gmino stuff
import de.gmino.geobase.server.domain.Address;
import de.gmino.geobase.server.domain.ImageUrl;
import de.gmino.geobase.server.domain.LatLon;
import de.gmino.issuemap.server.domain.gen.MapGen;
import de.gmino.meva.server.domain.KeyValueSet;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
// imports for field types
public class Map extends MapGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Map(long id)
	{
		super(id);
	}
	
	public Map(
			long id,
			boolean ready,
			String title,
			String subdomain,
			String barBackgroundColor,
			String barTextColor,
			String popupBackgroundColor,
			String popupTextColor,
			String markerColor,
			String resolvedColor,
			String city,
			LatLon initLocation,
			int initZoomlevel,
			String layer,
			ImageUrl logo,
			String infoText,
			String mapTyp,
			String website,
			String impressum_url,
			String email,
			Address postal_address,
			boolean has_fotos,
			boolean has_comments,
			boolean has_ratings,
			boolean has_list,
			boolean has_filters,
			boolean create,
			boolean edit,
			boolean delete,
			boolean mark,
			String rate_criteria,
			String mark_description,
			boolean searchStreet,
			boolean searchCity)
	{
		super(
			id,
			ready,
			title,
			subdomain,
			barBackgroundColor,
			barTextColor,
			popupBackgroundColor,
			popupTextColor,
			markerColor,
			resolvedColor,
			city,
			(de.gmino.geobase.server.domain.LatLon)initLocation,
			initZoomlevel,
			layer,
			(de.gmino.geobase.server.domain.ImageUrl)logo,
			infoText,
			mapTyp,
			website,
			impressum_url,
			email,
			(de.gmino.geobase.server.domain.Address)postal_address,
			has_fotos,
			has_comments,
			has_ratings,
			has_list,
			has_filters,
			create,
			edit,
			delete,
			mark,
			rate_criteria,
			mark_description,
			searchStreet,
			searchCity
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
