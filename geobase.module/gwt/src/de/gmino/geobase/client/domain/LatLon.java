// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.client.domain.gen.LatLonGen;
// default imports
// imports for JSON
public class LatLon extends LatLonGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public LatLon()
	{
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
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
