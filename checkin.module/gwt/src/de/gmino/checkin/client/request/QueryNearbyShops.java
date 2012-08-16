// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.client.request;

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
import java.util.Collection;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for field types
import de.gmino.geobase.client.domain.LatLon;

import de.gmino.checkin.client.request.gen.QueryNearbyShopsGen;

public class QueryNearbyShops extends QueryNearbyShopsGen {
	// Constructors
	public QueryNearbyShops(JsonObject json) throws IOException {
		this(new LatLon(json.get("location").asObject()), Double
				.parseDouble(json.get("radius").asString().stringValue()),
				Integer.parseInt(json.get("maxCount").asString().stringValue()));
	}

	public QueryNearbyShops(LatLon location, double radius, int maxCount) {
		super((de.gmino.geobase.client.domain.LatLon) location, radius,
				maxCount);
	}

	@Override
	public Collection<Long> evaluate() {
		throw new RuntimeException("No, this method does not exist here.");
	}
}
