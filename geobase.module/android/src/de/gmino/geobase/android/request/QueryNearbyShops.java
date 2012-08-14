// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.android.request;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;

import de.gmino.geobase.android.domain.LatLon;
import de.gmino.geobase.android.request.gen.QueryNearbyShopsGen;
public class QueryNearbyShops extends QueryNearbyShopsGen {
	// Constructors
	public QueryNearbyShops(DataInputStream dis) throws IOException
	{
		super(
			new LatLon(dis),
			dis.readDouble(),
			dis.readInt());
	}
	public QueryNearbyShops(
			LatLon location,
			double radius,
			int maxCount)
	{
		super(
			(de.gmino.geobase.android.domain.LatLon)location,
			radius,
			maxCount
		);
	}
	
	@Override
	public Collection<Long> evaluate() {
		throw new RuntimeException("Cannot evaluate a query locally on Android. Please use RequestEntitiesByQuery.");
	}
	

}
