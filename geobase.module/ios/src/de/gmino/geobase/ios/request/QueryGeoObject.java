// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.ios.request;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.ios.domain.LatLonRect;
import de.gmino.geobase.ios.request.gen.QueryGeoObjectGen;
// default imports
// imports for JSON
// imports for field types
public class QueryGeoObject extends QueryGeoObjectGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public QueryGeoObject()
	{
		this.area = new LatLonRect();
	}

	public QueryGeoObject(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryGeoObject(
			LatLonRect area,
			int maxCount)
	{
		super(
			(de.gmino.geobase.ios.domain.LatLonRect)area,
			maxCount
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
