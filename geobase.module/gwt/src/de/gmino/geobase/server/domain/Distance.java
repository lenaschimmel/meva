// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.server.domain.gen.DistanceGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
public class Distance extends DistanceGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Distance()
	{
	}

	// Constructor for SQL deseralizaiton
	public Distance(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public Distance(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public Distance(JsonObject json) throws IOException
	{
		super(json);
	}
	public Distance(
			double meters)
	{
		super(
			meters
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
