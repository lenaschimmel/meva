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


import de.gmino.geobase.client.domain.gen.LatLonGen;
public class LatLon extends LatLonGen {
	// Constructors
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
