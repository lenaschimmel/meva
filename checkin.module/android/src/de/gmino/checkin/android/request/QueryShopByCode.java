// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;

import org.itemscript.core.values.JsonObject;

import de.gmino.checkin.android.request.gen.QueryShopByCodeGen;
public class QueryShopByCode extends QueryShopByCodeGen {
	// Constructors
	public QueryShopByCode(DataInputStream dis) throws IOException
	{
		super(dis);
	}
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
	
	@Override
	public Collection<Long> evaluate() {
		throw new RuntimeException("No, this method does not exist here.");
	}
}
