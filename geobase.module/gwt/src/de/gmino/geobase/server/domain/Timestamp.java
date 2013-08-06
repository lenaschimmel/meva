// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.server.domain.gen.TimestampGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
public class Timestamp extends TimestampGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Timestamp()
	{
	}

	// Constructor for SQL deseralizaiton
	public Timestamp(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public Timestamp(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public Timestamp(JsonObject json) throws IOException
	{
		super(json);
	}
	public Timestamp(
			long millisSinceEpoch)
	{
		super(
			millisSinceEpoch
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
