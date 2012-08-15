// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.android.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

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

// imports for field types
import de.gmino.geobase.android.domain.Date;


import de.gmino.geobase.android.domain.gen.DateSpanGen;
public class DateSpan extends DateSpanGen {
	// Constructors
	public DateSpan(DataInputStream dis) throws IOException
	{
		this(
			new Date(dis),
			new Date(dis));
	}
	public DateSpan(JsonObject json) throws IOException
	{
		this(
			new Date(json.get("start").asObject()),
			new Date(json.get("end").asObject()));
	}

	public DateSpan(
			Date start,
			Date end)
	{
		super(
			(de.gmino.geobase.android.domain.Date)start,
			(de.gmino.geobase.android.domain.Date)end
		);
	}
	

}
