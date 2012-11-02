// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.LatLonRectGen;

public class LatLonRect extends LatLonRectGen {
	// Constructors
	public LatLonRect(JsonObject json) throws IOException {
		super(json);
	}

	public LatLonRect(LatLon min, LatLon max) {
		super((de.gmino.geobase.shared.domain.LatLon) min, (de.gmino.geobase.shared.domain.LatLon) max);
	}

	public LatLonRect(double d, double e, double f, double g) {
		this(new LatLon(d, e), new LatLon(f, g));
	}

	public LatLon getRandomWithin() {
		return new LatLon(min.getLatitude() + Math.random() * (max.getLatitude() - min.getLatitude()), min.getLongitude() + Math.random() * (max.getLongitude() - min.getLongitude()));
	}

}
