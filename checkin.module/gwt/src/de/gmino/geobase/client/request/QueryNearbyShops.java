

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/gwt/src/de/gmino/geobase/client/request/QueryNearbyShops.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.Query;
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
import java.util.Collection;

// imports for GWT JSON Parser
import com.google.gwt.json.client.*;

// imports for field types
import de.gmino.geobase.client.domain.LatLon;


import de.gmino.geobase.client.request.gen.QueryNearbyShopsGen;
public class QueryNearbyShops extends QueryNearbyShopsGen {
	// Constructors
	public QueryNearbyShops(JSONObject json) throws IOException
	{
		super(
			new LatLon(json.get("location").isObject()),
			Double.parseDouble(json.get("radius").isString().stringValue()),
			Integer.parseInt(json.get("maxCount").isString().stringValue()));
	}

	public QueryNearbyShops(
			LatLon location,
			double radius,
			int maxCount)
	{
		super(
			(de.gmino.geobase.client.domain.LatLon)location,
			radius,
			maxCount
		);
	}

	@Override
	public Collection<Long> evaluate() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
