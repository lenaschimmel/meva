// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;
import java.util.Date;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.TimestampGen;
// default imports
// imports for JSON
public class Timestamp extends TimestampGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Timestamp()
	{
	}

	public Timestamp(JsonObject json) throws IOException
	{
		super(json);
	}
	public Timestamp(
			long millisSinceEpoch)
	{
		super(
			millisSinceEpoch
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	public Date toDate() {
		return new Date(millisSinceEpoch);
	}

	public Timestamp addDuration(Duration d) {
		return new Timestamp(millisSinceEpoch + d.getMilliseconds());
	}
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	public Duration timeUntil(Timestamp then) {
		return new Duration(then.getMillisSinceEpoch() - millisSinceEpoch);
	}

	public Duration relativeToNow() {
		return new Duration(millisSinceEpoch - System.currentTimeMillis());
	}
}
