// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.server.domain.gen.LatLonGen;

public class LatLon extends LatLonGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public LatLon(String prefix, ResultSet rs) throws SQLException {
		super(prefix, rs);
	}

	public LatLon(DataInputStream dis) throws IOException {
		super(dis);
	}

	public LatLon(JsonObject json) throws IOException {
		super(json);
	}

	public LatLon(double latitude, double longitude) {
		super(latitude, longitude);
	}

}
