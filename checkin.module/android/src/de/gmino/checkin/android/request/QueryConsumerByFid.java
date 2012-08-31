// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.EntityTypeName;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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


import de.gmino.checkin.android.request.gen.QueryConsumerByFidGen;
public class QueryConsumerByFid extends QueryConsumerByFidGen {
	// Constructors
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
	

}
