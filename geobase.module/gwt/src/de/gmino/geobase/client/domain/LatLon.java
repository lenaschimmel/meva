// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import com.google.gwt.i18n.client.NumberFormat;

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

	static NumberFormat format = NumberFormat.getFormat("###.000,000");
	
	@Override
	public String toDecimalString()
	{
		
		return format.format(latitude) + "°, " + format.format(longitude) + "°";
	}
	
	public boolean isEmpty()
	{
		return latitude == 0 && longitude == 0;
	}
}
