// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.issuemap.server.request.gen.QueryIssuesByMapGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
public class QueryIssuesByMap extends QueryIssuesByMapGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryIssuesByMap()
	{
	}

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
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
