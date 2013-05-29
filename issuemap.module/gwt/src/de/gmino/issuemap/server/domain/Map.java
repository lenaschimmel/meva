// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

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

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.shared.EntitySql;

// imports for field types
import de.gmino.geobase.server.domain.ImageUrl;
import de.gmino.geobase.server.domain.LatLon;
import de.gmino.issuemap.server.domain.Issue;
import de.gmino.issuemap.server.domain.MapHasMarkertype;


import de.gmino.issuemap.server.domain.gen.MapGen;
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
			String color,
			String city,
			LatLon initLocation,
			int initZoomlevel,
			String layer,
			String headerText,
			ImageUrl logo,
			String infoText,
			String mapTyp)
	{
		super(
			id,
			ready,
			title,
			description,
			subdomain,
			color,
			city,
			(de.gmino.geobase.server.domain.LatLon)initLocation,
			initZoomlevel,
			layer,
			headerText,
			(de.gmino.geobase.server.domain.ImageUrl)logo,
			infoText,
			mapTyp
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
