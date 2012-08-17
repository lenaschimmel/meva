// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;
import de.gmino.meva.shared.RelationCollection;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


import de.gmino.checkin.server.domain.gen.ImageUrlGen;
public class ImageUrl extends ImageUrlGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public ImageUrl(String prefix, ResultSet rs) throws SQLException
	{
		super(
			rs.getString(prefix + "url")		);
	}
	public ImageUrl(DataInputStream dis) throws IOException
	{
		this(
				dis.readUTF());
	}
	public ImageUrl(JsonObject json) throws IOException
	{
		this(
			json.get("url").asString().stringValue());
	}

	public ImageUrl(
			String url)
	{
		super(
			url
		);
	}
	

}
