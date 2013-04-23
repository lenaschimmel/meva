// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.request;

import java.io.IOException;
// imports for JSON
import org.itemscript.core.values.JsonObject;
import de.gmino.issuemap.client.request.gen.QueryIssuesByMapGen;
public class QueryIssuesByMap extends QueryIssuesByMapGen {
	// Constructors
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
	

}
