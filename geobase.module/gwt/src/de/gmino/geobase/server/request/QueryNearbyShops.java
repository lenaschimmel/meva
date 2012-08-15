// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import org.itemscript.core.values.JsonObject;

import com.google.gwt.json.client.JSONObject;

import de.gmino.geobase.server.domain.LatLon;
import de.gmino.geobase.server.request.gen.QueryNearbyShopsGen;

public class QueryNearbyShops extends QueryNearbyShopsGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public QueryNearbyShops(String prefix, ResultSet rs) throws SQLException {
		super(new LatLon(prefix + "location_", rs), rs.getDouble(prefix
				+ "radius"), rs.getInt(prefix + "maxCount"));
	}

	public QueryNearbyShops(DataInputStream dis) throws IOException {
		super(new LatLon(dis), dis.readDouble(), dis.readInt());
	}

	public QueryNearbyShops(JsonObject json) throws IOException {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public QueryNearbyShops(LatLon location, double radius, int maxCount) {
		super((de.gmino.geobase.server.domain.LatLon) location, radius,
				maxCount);
	}

	@Override
	public Collection<Long> evaluate() {
		try {
			Connection con = null;
			Statement stat = con.createStatement();
			double offLat = radius / 111111.0;
			double offLon = radius / (111111.0 * Math.cos(location.getLatitude() * Math.PI / 180.0));
			double maxLat = location.getLatitude() + offLat;
			double minLat = location.getLatitude() - offLat;
			double maxLon = location.getLongitude() + offLon;
			double minLon = location.getLongitude() - offLon;
			ResultSet result = stat.executeQuery("SELECT id FROM Shop "
					+ "WHERE location_latitude < '" + maxLat
					+ "' AND location_latitude > '" + minLat
					+ "' AND location_longitude < '" + maxLon
					+ "' AND location_longitude > '" + minLon + "' LIMIT " + maxCount +";");
			Collection<Long> ids = new LinkedList<Long>();
			while(result.next())
				ids.add(result.getLong(1));
			return ids;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
