// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.server.domain.gen.LatLonRectGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
// imports for field types
public class LatLonRect extends LatLonRectGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public LatLonRect()
	{
		this.min = new LatLon();
		this.max = new LatLon();
	}

	// Constructor for SQL deseralizaiton
	public LatLonRect(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public LatLonRect(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public LatLonRect(JsonObject json) throws IOException
	{
		super(json);
	}
	public LatLonRect(
			LatLon min,
			LatLon max)
	{
		super(
			(de.gmino.geobase.server.domain.LatLon)min,
			(de.gmino.geobase.server.domain.LatLon)max
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
