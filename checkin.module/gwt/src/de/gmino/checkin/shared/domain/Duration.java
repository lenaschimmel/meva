

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/gwt/src/de/gmino/checkin/shared/domain/Duration.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.



// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/meva/src/de/gmino/checkin/shared/domain/Duration.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

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


import de.gmino.checkin.shared.domain.gen.DurationGen;
public class Duration extends DurationGen {
	// Constructors
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
