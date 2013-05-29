// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.client.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.ValueQuery;
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


import de.gmino.checkin.client.request.gen.QueryPerformCheckinGen;
public class QueryPerformCheckin extends QueryPerformCheckinGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryPerformCheckin()
	{
	}

	public QueryPerformCheckin(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryPerformCheckin(
			String scanCode)
	{
		super(
			scanCode
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
