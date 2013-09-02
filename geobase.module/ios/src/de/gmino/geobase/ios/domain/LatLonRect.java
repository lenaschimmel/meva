// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.ios.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.ios.domain.gen.LatLonRectGen;
// default imports
// imports for JSON
// imports for field types
public class LatLonRect extends LatLonRectGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public LatLonRect()
	{
		this.min = new LatLon();
		this.max = new LatLon();
	}

	public LatLonRect(JsonObject json) throws IOException
	{
		super(json);
	}
	public LatLonRect(
			LatLon min,
			LatLon max)
	{
		super(
			(de.gmino.geobase.ios.domain.LatLon)min,
			(de.gmino.geobase.ios.domain.LatLon)max
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
