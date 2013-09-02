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
import de.gmino.geobase.android.domain.ImageUrl;
import de.gmino.geobase.android.domain.LatLon;
import de.gmino.geobase.android.domain.Timestamp;
import de.gmino.issuemap.android.domain.Comment;
import de.gmino.issuemap.android.domain.Map;
import de.gmino.issuemap.android.domain.Markertype;
import de.gmino.issuemap.android.domain.Photo;
import de.gmino.meva.android.domain.KeyValueSet;


import de.gmino.issuemap.android.domain.gen.IssueGen;
@SuppressWarnings("unused")
public class Issue extends IssueGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Issue(long id)
	{
		super(id);
	}
	
	public Issue(
			long id,
			boolean ready,
			LatLon location,
			String title,
			String description,
			Markertype markertype,
			KeyValueSet keyvalueset,
			Map map_instance,
			Timestamp creationTimestamp,
			int rating,
			int number_of_rating,
			boolean resolved,
			boolean deleted,
			ImageUrl primary_picture,
			Timestamp eventTimestamp,
			double price,
			String organizer,
			String email,
			String phone)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.android.domain.LatLon)location,
			title,
			description,
			(de.gmino.issuemap.android.domain.Markertype)markertype,
			(de.gmino.meva.android.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.android.domain.Map)map_instance,
			(de.gmino.geobase.android.domain.Timestamp)creationTimestamp,
			rating,
			number_of_rating,
			resolved,
			deleted,
			(de.gmino.geobase.android.domain.ImageUrl)primary_picture,
			(de.gmino.geobase.android.domain.Timestamp)eventTimestamp,
			price,
			organizer,
			email,
			phone
		);
		loadValueSet();
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
