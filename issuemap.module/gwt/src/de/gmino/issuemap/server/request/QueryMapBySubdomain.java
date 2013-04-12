// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.request;

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



import de.gmino.issuemap.server.SqlHelper;
import de.gmino.issuemap.server.request.gen.QueryMapBySubdomainGen;
public class QueryMapBySubdomain extends QueryMapBySubdomainGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public QueryMapBySubdomain(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public QueryMapBySubdomain(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public QueryMapBySubdomain(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryMapBySubdomain(
			String subdomain)
	{
		super(
			subdomain
		);
	}
	
	@Override
	public Collection<Long> evaluate() {

		try {
			Connection con = SqlHelper.getConnection();
			Statement stat = con.createStatement();

			ResultSet result = stat.executeQuery("SELECT id FROM Map " + "WHERE subdomain = '" + subdomain + "';");
			Collection<Long> ids = new LinkedList<Long>();
			while (result.next())
				ids.add(result.getLong(1));
			return ids;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
