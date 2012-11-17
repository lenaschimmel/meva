// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.android.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.android.domain.gen.TimestampGen;

public class Timestamp extends TimestampGen {
	// Constructors
	public Timestamp(DataInputStream dis) throws IOException {
		super(dis);
	}

	public Timestamp(JsonObject json) throws IOException {
		super(json);
	}

	public Timestamp(long millisSinceEpoch) {
		super(millisSinceEpoch);
	}

}
