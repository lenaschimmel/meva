// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.meva.ios.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.TreeMap;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;


import de.gmino.meva.ios.domain.gen.DateGen;
@SuppressWarnings("unused")
public class Date extends DateGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Date()
	{
	}

	public Date(JsonObject json) throws IOException
	{
		super(json);
	}
	public Date(
			long millisSinceEpoch)
	{
		super(
			millisSinceEpoch
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
