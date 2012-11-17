// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.checkin.android.request.gen.QueryConsumerByFidGen;

public class QueryConsumerByFid extends QueryConsumerByFidGen {
	// Constructors
	public QueryConsumerByFid(DataInputStream dis) throws IOException {
		super(dis);
	}

	public QueryConsumerByFid(JsonObject json) throws IOException {
		super(json);
	}

	public QueryConsumerByFid(String fid) {
		super(fid);
	}

}
