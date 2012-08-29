// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.EntityTypeName;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;


import de.gmino.checkin.shared.request.gen.QueryShopByCodeGen;
public abstract class QueryShopByCode extends QueryShopByCodeGen {
	// Constructors
	public QueryShopByCode(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryShopByCode(
			String scanCode)
	{
		super(
			scanCode
		);
	}
	

}
