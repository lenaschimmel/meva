// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.client.domain.gen.TimestampGen;

public class Timestamp extends TimestampGen {
	// Constructors
	public Timestamp(JsonObject json) throws IOException {
		this(Long.parseLong(json.get("millisSinceEpoch").asString().stringValue()));
	}

	public Timestamp(long millisSinceEpoch) {
		super(millisSinceEpoch);
	}

}
