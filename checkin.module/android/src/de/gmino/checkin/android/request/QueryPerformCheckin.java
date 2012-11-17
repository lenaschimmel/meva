// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.checkin.android.request.gen.QueryPerformCheckinGen;

public class QueryPerformCheckin extends QueryPerformCheckinGen {
	// Constructors
	public QueryPerformCheckin(DataInputStream dis) throws IOException {
		super(dis);
	}

	public QueryPerformCheckin(JsonObject json) throws IOException {
		super(json);
	}

	public QueryPerformCheckin(String scanCode) {
		super(scanCode);
	}

}
