// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.ValueQuery;
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


import de.gmino.checkin.server.request.gen.QueryConsumerByFidGen;
public class QueryConsumerByFid extends QueryConsumerByFidGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryConsumerByFid()
	{
	}

	// Constructor for SQL deseralizaiton
	public QueryConsumerByFid(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public QueryConsumerByFid(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public QueryConsumerByFid(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryConsumerByFid(
			String fid)
	{
		super(
			fid
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
