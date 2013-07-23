// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

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

import de.gmino.geobase.shared.domain.Address;
// imports for field types
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.issuemap.shared.domain.Issue;
import de.gmino.issuemap.shared.domain.MapHasMarkertype;


import de.gmino.issuemap.shared.domain.gen.MapGen;
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
			String email,
			Address postal_address)
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
			(de.gmino.geobase.shared.domain.LatLon)initLocation,
			initZoomlevel,
			layer,
			headerText,
			(de.gmino.geobase.shared.domain.ImageUrl)logo,
			infoText,
			mapTyp,
			website,
			email,
			(de.gmino.geobase.shared.domain.Address)postal_address
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
