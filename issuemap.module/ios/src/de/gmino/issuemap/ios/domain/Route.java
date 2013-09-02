// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.domain;

// gmino stuff
import de.gmino.geobase.ios.domain.Distance;
import de.gmino.geobase.ios.domain.Duration;
import de.gmino.geobase.ios.domain.LatLon;
import de.gmino.issuemap.ios.domain.gen.RouteGen;
// default imports
// imports for JSON
// imports for field types
public class Route extends RouteGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Route(long id)
	{
		super(id);
	}
	
	public Route(
			long id,
			boolean ready,
			LatLon location,
			String title,
			String description,
			String gpxUrl,
			String color,
			String characteristics,
			Distance length,
			Duration rideTime,
			int rating,
			int number_of_rating)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.ios.domain.LatLon)location,
			title,
			description,
			gpxUrl,
			color,
			characteristics,
			(de.gmino.geobase.ios.domain.Distance)length,
			(de.gmino.geobase.ios.domain.Duration)rideTime,
			rating,
			number_of_rating
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
