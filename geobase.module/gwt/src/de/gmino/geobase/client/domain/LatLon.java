// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import com.google.gwt.json.client.JSONObject;

import de.gmino.geobase.client.domain.gen.LatLonGen;

public class LatLon extends LatLonGen {
	// Constructors
	public LatLon(JSONObject json) throws IOException {
		super(Double.parseDouble(json.get("latitude").isString().stringValue()), Double.parseDouble(json.get("longitude").isString().stringValue()));
	}

	public LatLon(JsonObject json) throws IOException {
		super(json);
	}

	public LatLon(double latitude, double longitude) {
		super(latitude, longitude);
	}

}
