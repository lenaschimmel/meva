// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.request;

import java.io.IOException;
// imports for JSON
import org.itemscript.core.values.JsonObject;
import de.gmino.issuemap.client.request.gen.QueryMapBySubdomainGen;
public class QueryMapBySubdomain extends QueryMapBySubdomainGen {
	// Constructors
	public QueryMapBySubdomain(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryMapBySubdomain(
			String subdomain)
	{
		super(
			subdomain
		);
	}
	

}
