

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/meva/src/de/gmino/geobase/shared/domain/Timestamp.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;
import java.util.Date;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.TimestampGen;
public class Timestamp extends TimestampGen {
	// Constructors
	public Timestamp(JsonObject json) throws IOException
	{
		this(
			Long.parseLong(json.get("millisSinceEpoch").asString().stringValue()));
	}

	public Timestamp(
			long millisSinceEpoch)
	{
		super(
			millisSinceEpoch
		);
	}
	
	public Date toDate()
	{
		return new Date(millisSinceEpoch);
	}
	
	public Timestamp addDuration(Duration d)
	{
		return new Timestamp(millisSinceEpoch + d.getMilliseconds());
	}
	
	public static Timestamp now()
	{
		return new Timestamp(System.currentTimeMillis());
	}
	
	public Duration timeUntil(Timestamp then)
	{
		return new Duration(then.getMillisSinceEpoch() - millisSinceEpoch);
	}
}
