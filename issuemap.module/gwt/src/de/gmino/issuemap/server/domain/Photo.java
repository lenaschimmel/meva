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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TreeSet;

// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.shared.EntitySql;

// imports for field types
import de.gmino.geobase.server.domain.ImageUrl;
import de.gmino.geobase.server.domain.Timestamp;
import de.gmino.issuemap.server.domain.Issue;


import de.gmino.issuemap.server.domain.gen.PhotoGen;
public class Photo extends PhotoGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Photo(long id)
	{
		super(id);
	}
	
	public Photo(
			long id,
			boolean ready,
			Issue issue,
			ImageUrl image,
			String user,
			Timestamp timestamp,
			boolean deleted)
	{
		super(
			id,
			ready,
			(de.gmino.issuemap.server.domain.Issue)issue,
			(de.gmino.geobase.server.domain.ImageUrl)image,
			user,
			(de.gmino.geobase.server.domain.Timestamp)timestamp,
			deleted
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
