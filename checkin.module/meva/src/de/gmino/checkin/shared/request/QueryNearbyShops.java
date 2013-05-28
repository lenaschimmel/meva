// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.request;

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

// imports for field types
import de.gmino.geobase.shared.domain.LatLon;


import de.gmino.checkin.shared.request.gen.QueryNearbyShopsGen;
public class QueryNearbyShops extends QueryNearbyShopsGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryNearbyShops()
	{
		this.location = new LatLon();
	}

	public QueryNearbyShops(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryNearbyShops(
			LatLon location,
			double radius,
			int maxCount)
	{
		super(
			(de.gmino.geobase.shared.domain.LatLon)location,
			radius,
			maxCount
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
