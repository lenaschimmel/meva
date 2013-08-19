// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

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
import de.gmino.meva.server.EntitySql;

// imports for field types
import de.gmino.geobase.server.domain.LatLon;
import de.gmino.geobase.server.domain.Timestamp;
import de.gmino.issuemap.server.domain.Comment;
import de.gmino.issuemap.server.domain.Map;
import de.gmino.issuemap.server.domain.Markertype;
import de.gmino.issuemap.server.domain.Photo;


import de.gmino.issuemap.server.domain.gen.PoiGen;
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
			Map map_instance,
			Timestamp creationTimestamp,
			int rating,
			int number_of_rating,
			boolean resolved,
			boolean deleted)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.server.domain.LatLon)location,
			title,
			(de.gmino.issuemap.server.domain.Markertype)markertype,
			(de.gmino.issuemap.server.domain.Map)map_instance,
			(de.gmino.geobase.server.domain.Timestamp)creationTimestamp,
			rating,
			number_of_rating,
			resolved,
			deleted
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
