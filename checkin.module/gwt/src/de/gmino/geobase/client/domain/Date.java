

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/gwt/src/de/gmino/geobase/client/domain/Date.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.domain;

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


import de.gmino.geobase.client.domain.gen.DateGen;
public class Date extends DateGen {
	// Constructors
	public Date(JSONObject json) throws IOException
	{
		super(
			Short.parseShort(json.get("day").isString().stringValue()),
			Short.parseShort(json.get("month").isString().stringValue()),
			Short.parseShort(json.get("year").isString().stringValue()));
	}

	public Date(
			short day,
			short month,
			short year)
	{
		super(
			day,
			month,
			year
		);
	}
	

}
