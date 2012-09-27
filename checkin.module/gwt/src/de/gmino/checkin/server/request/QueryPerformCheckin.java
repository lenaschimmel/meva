// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.Query;
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
import java.util.LinkedList;

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


import de.gmino.checkin.server.request.gen.QueryPerformCheckinGen;
public class QueryPerformCheckin extends QueryPerformCheckinGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public QueryPerformCheckin(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public QueryPerformCheckin(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public QueryPerformCheckin(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryPerformCheckin(
			String scanCode)
	{
		super(
			scanCode
		);
	}
	
	@Override
	public Collection<Value> evaluate() {
		Collection<Value> ret = new LinkedList<Value>();
		ret.add(new ReplyPerformCheckin(false, "Wir haben das noch nicht fertig programmiert. Sorry!", null, null, null));
		return ret;
	}

}
