// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// imports for field types
import de.gmino.geobase.shared.domain.LatLon;


import de.gmino.geobase.shared.request.gen.QueryNearbyShopsGen;
public abstract class QueryNearbyShops extends QueryNearbyShopsGen {
	// Constructors
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
	

}
