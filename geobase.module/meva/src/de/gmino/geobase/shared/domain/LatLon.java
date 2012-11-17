// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.LatLonGen;
import de.gmino.meva.shared.Util;

public class LatLon extends LatLonGen {
	public static final double EARTH_RADIUS_IN_M = 6371000;

	static LatLonRect[] rects = { new LatLonRect(52.296, 10.4769, 52.2243, 10.5761), new LatLonRect(52.271, 10.5128, 52.2572, 10.5294), new LatLonRect(52.422, 9.6660, 52.3260, 9.8230),
			new LatLonRect(52.683, 9.6660, 51.8420, 11.2840), new LatLonRect(51.645, 6.6910, 51.4110, 7.5590) };

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
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(this.latitude) * Math.cos(that.latitude);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = EARTH_RADIUS_IN_M * c;

		return new Distance(dist);
	}

	public static LatLon getWithRandomData() {
		return Util.randomElementFrom(rects).getRandomWithin();
	}
}
