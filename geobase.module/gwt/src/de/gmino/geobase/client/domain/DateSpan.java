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

// imports for field types
import de.gmino.geobase.client.domain.Date;


import de.gmino.geobase.client.domain.gen.DateSpanGen;
public class DateSpan extends DateSpanGen {
	// Constructors
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
			(de.gmino.geobase.client.domain.Date)start,
			(de.gmino.geobase.client.domain.Date)end
		);
	}
	

}
