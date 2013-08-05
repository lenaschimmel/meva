// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import org.itemscript.core.values.JsonObject;

import de.gmino.issuemap.server.request.gen.QueryMapBySubdomainGen;
import de.gmino.meva.server.SqlHelper;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces

public class QueryMapBySubdomain extends QueryMapBySubdomainGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryMapBySubdomain()
	{
	}

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
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT

	@Override
	public Collection<Long> evaluate() {

		try {
			Connection con = SqlHelper.getConnection();
			Statement stat = con.createStatement();
			ResultSet result = stat.executeQuery("SELECT id FROM Map "
					+ "WHERE subdomain = '" + subdomain + "';");
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
