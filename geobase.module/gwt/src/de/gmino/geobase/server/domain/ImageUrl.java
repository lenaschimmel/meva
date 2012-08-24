// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.server.domain.gen.ImageUrlGen;
public class ImageUrl extends ImageUrlGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public ImageUrl(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
	public ImageUrl(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public ImageUrl(JsonObject json) throws IOException
	{
		super(json);
	}
	public ImageUrl(
			String url)
	{
		super(
			url
		);
	}
	

}
