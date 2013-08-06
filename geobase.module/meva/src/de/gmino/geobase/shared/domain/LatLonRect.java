// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.LatLonRectGen;
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
			(de.gmino.geobase.shared.domain.LatLon)min,
			(de.gmino.geobase.shared.domain.LatLon)max
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	public LatLonRect(double south, double west, double north, double east) {
		this.min = new LatLon(south, west);
		this.max = new LatLon(north, east);
	}

	public LatLon getRandomWithin() {
        return new LatLon(min.getLatitude() + Math.random() * (max.getLatitude() - min.getLatitude()), min.getLongitude() + Math.random() * (max.getLongitude() - min.getLongitude()));
	}
}
