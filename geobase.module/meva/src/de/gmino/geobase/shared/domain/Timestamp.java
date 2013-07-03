// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;


import de.gmino.geobase.shared.domain.gen.TimestampGen;
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
