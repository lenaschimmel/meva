

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/meva/src/de/gmino/geobase/shared/domain/LatLon.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;

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

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.LatLonGen;

public class LatLon extends LatLonGen {
	public static final double EARTH_RADIUS_IN_M = 6371000;

	public LatLon(JsonObject json) throws IOException {
		super(json);
		// TODO Auto-generated constructor stub
	}

	// Constructors
	public LatLon(double latitude, double longitude) {
		super(latitude, longitude);
	}

	public Distance getDistanceTo(LatLon that) {
		double dLat = Math.toRadians(this.latitude - that.latitude);
		double dLng = Math.toRadians(this.longitude - that.longitude);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
				* Math.cos(this.latitude) * Math.cos(that.latitude);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = EARTH_RADIUS_IN_M * c;

		return new Distance(dist);
	}
}
