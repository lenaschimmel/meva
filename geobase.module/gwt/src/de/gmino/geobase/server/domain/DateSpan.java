// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

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

// imports for field types
import de.gmino.geobase.server.domain.Date;


import de.gmino.geobase.server.domain.gen.DateSpanGen;
public class DateSpan extends DateSpanGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public DateSpan(String prefix, ResultSet rs) throws SQLException
	{
		super(
			new Date(prefix + "start_", rs),
			new Date(prefix + "end_", rs)
		);
	}
	public DateSpan(DataInputStream dis) throws IOException
	{
		this(
			new Date(dis),
			new Date(dis));
	}
	public DateSpan(JsonObject json) throws IOException
	{
		this(
			new Date(json.get("start").asObject()),
			new Date(json.get("end").asObject()));
	}

	public DateSpan(
			Date start,
			Date end)
	{
		super(
			(de.gmino.geobase.server.domain.Date)start,
			(de.gmino.geobase.server.domain.Date)end
		);
	}
	

}
