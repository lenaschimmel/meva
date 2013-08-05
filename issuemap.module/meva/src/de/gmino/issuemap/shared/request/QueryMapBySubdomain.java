// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.request;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.issuemap.shared.request.gen.QueryMapBySubdomainGen;
// default imports
// imports for JSON
public class QueryMapBySubdomain extends QueryMapBySubdomainGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryMapBySubdomain()
	{
	}

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
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
