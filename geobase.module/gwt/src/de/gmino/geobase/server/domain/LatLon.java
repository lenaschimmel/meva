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


import de.gmino.geobase.server.domain.gen.LatLonGen;
public class LatLon extends LatLonGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public LatLon(String prefix, ResultSet rs) throws SQLException
	{
		super(
			rs.getDouble(prefix + "latitude"),
			rs.getDouble(prefix + "longitude")
		);
	}
	public LatLon(DataInputStream dis) throws IOException
	{
		super(
			dis.readDouble(),
			dis.readDouble());
	}
	public LatLon(JSONObject json) throws IOException
	{
		super(
			Double.parseDouble(json.get("latitude").isString().stringValue()),
			Double.parseDouble(json.get("longitude").isString().stringValue()));
	}

	public LatLon(
			double latitude,
			double longitude)
	{
		super(
			latitude,
			longitude
		);
	}
	

}
