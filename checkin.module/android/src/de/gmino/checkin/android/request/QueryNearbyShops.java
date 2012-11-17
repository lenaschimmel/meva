// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;

import org.itemscript.core.values.JsonObject;

import de.gmino.checkin.android.request.gen.QueryNearbyShopsGen;
import de.gmino.geobase.android.domain.LatLon;

public class QueryNearbyShops extends QueryNearbyShopsGen {
	// Constructors
	public QueryNearbyShops(DataInputStream dis) throws IOException {
		this(new LatLon(dis), dis.readDouble(), dis.readInt());
	}

	public QueryNearbyShops(JsonObject json) throws IOException {
		this(new LatLon(json.get("location").asObject()), Double.parseDouble(json.get("radius").asString().stringValue()), Integer.parseInt(json.get("maxCount").asString().stringValue()));
	}

	public QueryNearbyShops(LatLon location, double radius, int maxCount) {
		super((de.gmino.geobase.android.domain.LatLon) location, radius, maxCount);
	}

	@Override
	public Collection<Long> evaluate() {
		throw new RuntimeException("No, this method does not exist here.");
	}
}
