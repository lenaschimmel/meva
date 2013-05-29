// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.request;

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


import de.gmino.issuemap.client.request.gen.QueryIssuesByMapGen;
public class QueryIssuesByMap extends QueryIssuesByMapGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryIssuesByMap()
	{
	}

	public QueryIssuesByMap(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryIssuesByMap(
			long map_instance)
	{
		super(
			map_instance
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
