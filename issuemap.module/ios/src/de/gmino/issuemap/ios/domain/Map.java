// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for field types
import de.gmino.geobase.ios.domain.ImageUrl;
import de.gmino.geobase.ios.domain.LatLon;
import de.gmino.issuemap.ios.domain.Issue;
import de.gmino.issuemap.ios.domain.MapHasMarkertype;


import de.gmino.issuemap.ios.domain.gen.MapGen;
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
			(de.gmino.geobase.ios.domain.LatLon)initLocation,
			initZoomlevel,
			layer,
			(de.gmino.geobase.ios.domain.ImageUrl)logo,
			infoText,
			mapTyp,
			website,
			impressum_url,
			email,
			(de.gmino.geobase.ios.domain.Address)postal_address,
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
