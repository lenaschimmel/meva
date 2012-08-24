// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.request;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.checkin.shared.request.gen.QueryNearbyShopsGen;
import de.gmino.geobase.shared.domain.LatLon;
public abstract class QueryNearbyShops extends QueryNearbyShopsGen {
	// Constructors
	public QueryNearbyShops(JsonObject json) throws IOException
	{
		this(
			new LatLon(json.get("location").asObject()),
			Double.parseDouble(json.get("radius").asString().stringValue()),
			Integer.parseInt(json.get("maxCount").asString().stringValue()));
	}

	public QueryNearbyShops(
			LatLon location,
			double radius,
			int maxCount)
	{
		super(
			(de.gmino.geobase.shared.domain.LatLon)location,
			radius,
			maxCount
		);
	}
	

}
