

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/gwt/src/de/gmino/geobase/server/domain/DateSpan.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

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

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// imports for GWT JSON Parser
import com.google.gwt.json.client.*;

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
		super(
			new Date(dis),
			new Date(dis));
	}
	public DateSpan(JSONObject json) throws IOException
	{
		super(
			new Date(json.get("start").isObject()),
			new Date(json.get("end").isObject()));
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
