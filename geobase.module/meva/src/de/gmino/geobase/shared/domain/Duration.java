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

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;


import de.gmino.geobase.shared.domain.gen.DurationGen;
public class Duration extends DurationGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Duration()
	{
	}

	public Duration(JsonObject json) throws IOException
	{
		super(json);
	}
	public Duration(
			long milliseconds)
	{
		super(
			milliseconds
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
