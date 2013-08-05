// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.meva.server.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.meva.server.domain.gen.VoidGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
public class Void extends VoidGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Void()
	{
	}

	// Constructor for SQL deseralizaiton
	public Void(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public Void(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public Void(JsonObject json) throws IOException
	{
		super(json);
	}
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
