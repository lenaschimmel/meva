// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.server.domain.LatLonRect;
import de.gmino.geobase.server.request.gen.QueryGeoObjectGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
// imports for field types
public class QueryGeoObject extends QueryGeoObjectGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryGeoObject()
	{
		this.area = new LatLonRect();
	}

	// Constructor for SQL deseralizaiton
	public QueryGeoObject(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public QueryGeoObject(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public QueryGeoObject(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryGeoObject(
			LatLonRect area,
			int maxCount)
	{
		super(
			(de.gmino.geobase.server.domain.LatLonRect)area,
			maxCount
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
