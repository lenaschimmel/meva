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

// imports for Key-Value-Set
import de.gmino.meva.shared.ValueWrapper;
import de.gmino.meva.shared.domain.KeyValueDef;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;
import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.standard.StandardConfig;

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
import de.gmino.geobase.android.domain.LatLon;
import de.gmino.geobase.android.domain.Timestamp;
import de.gmino.issuemap.android.domain.Comment;
import de.gmino.issuemap.android.domain.Map;
import de.gmino.issuemap.android.domain.Markertype;
import de.gmino.issuemap.android.domain.Photo;
import de.gmino.meva.android.domain.KeyValueSet;


import de.gmino.issuemap.android.domain.gen.PoiGen;
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
			boolean deleted,
			User creator)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.android.domain.LatLon)location,
			title,
			(de.gmino.issuemap.android.domain.Markertype)markertype,
			(de.gmino.meva.android.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.android.domain.Map)map_instance,
			(de.gmino.geobase.android.domain.Timestamp)creationTimestamp,
			(de.gmino.geobase.android.domain.Timestamp)resolvedTimestamp,
			rating,
			number_of_rating,
			marked,
			deleted,
			(de.gmino.issuemap.android.domain.User)creator
		);
		loadValueSet();
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
