// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.client.domain.gen.DurationGen;

public class Duration extends DurationGen {
	// Constructors
	public Duration(JsonObject json) throws IOException {
		this(Long.parseLong(json.get("milliseconds").asString().stringValue()));
	}

	public Duration(long milliseconds) {
		super(milliseconds);
	}

}
