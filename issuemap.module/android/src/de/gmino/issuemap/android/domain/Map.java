// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.android.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.TreeMap;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TreeSet;

// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;

// imports for field types
import de.gmino.geobase.android.domain.Address;
import de.gmino.geobase.android.domain.ImageUrl;
import de.gmino.geobase.android.domain.LatLon;
import de.gmino.issuemap.android.domain.DecentralizedGeneration;
import de.gmino.issuemap.android.domain.Markertype;
import de.gmino.issuemap.android.domain.Poi;
import de.gmino.issuemap.android.domain.User;


import de.gmino.issuemap.android.domain.gen.MapGen;
@SuppressWarnings("unused")
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
			String description,
			String subdomain,
			String primary_color,
			String secondary_color,
			String background_color,
			String resolved_color,
			String city,
			LatLon initLocation,
			int initZoomlevel,
			String layer,
			String headerText,
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
			description,
			subdomain,
			primary_color,
			secondary_color,
			background_color,
			resolved_color,
			city,
			(de.gmino.geobase.android.domain.LatLon)initLocation,
			initZoomlevel,
			layer,
			headerText,
			(de.gmino.geobase.android.domain.ImageUrl)logo,
			infoText,
			mapTyp,
			website,
			impressum_url,
			email,
			(de.gmino.geobase.android.domain.Address)postal_address,
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
