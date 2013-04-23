// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.request;

// default imports
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

// imports for JSON
import org.itemscript.core.values.JsonObject;
// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import de.gmino.issuemap.server.SqlHelper;
import de.gmino.issuemap.server.request.gen.QueryIssuesByMapGen;
public class QueryIssuesByMap extends QueryIssuesByMapGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public QueryIssuesByMap(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public QueryIssuesByMap(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public QueryIssuesByMap(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryIssuesByMap(
			long map_instance)
	{
		super(
			map_instance
		);
	}
	
	@Override
	public Collection<Long> evaluate() {
		try {
			Connection con = SqlHelper.getConnection();
			Statement stat = con.createStatement();

			ResultSet result = stat.executeQuery("SELECT id FROM Issue " + "WHERE map_instance_id = '" + map_instance + "';");
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
