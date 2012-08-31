// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.request;

// gmino stuff
import java.io.IOException;
import java.util.Collection;

import org.itemscript.core.values.JsonObject;

import de.gmino.checkin.shared.request.gen.QueryConsumerByFidGen;
public class QueryConsumerByFid extends QueryConsumerByFidGen {
	// Constructors
	public QueryConsumerByFid(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryConsumerByFid(
			String fid)
	{
		super(
			fid
		);
	}
	
}
