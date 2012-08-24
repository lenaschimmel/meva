// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.android.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.android.domain.gen.LatLonGen;
public class LatLon extends LatLonGen {
	// Constructors
	public LatLon(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public LatLon(JsonObject json) throws IOException
	{
		super(json);
	}
	public LatLon(
			double latitude,
			double longitude)
	{
		super(
			latitude,
			longitude
		);
	}
	

}
