// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.android.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;
import de.gmino.meva.shared.RelationCollection;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// android
import de.gmino.meva.android.EntityAndroid;
import de.gmino.meva.android.ValueAndroid;


import de.gmino.geobase.android.domain.gen.DurationGen;
public class Duration extends DurationGen {
	// Constructors
	public Duration(DataInputStream dis) throws IOException
	{
		this(
			dis.readLong());
	}
	public Duration(JsonObject json) throws IOException
	{
		this(
			Long.parseLong(json.get("milliseconds").asString().stringValue()));
	}

	public Duration(
			long milliseconds)
	{
		super(
			milliseconds
		);
	}
	

}
