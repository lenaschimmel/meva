// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

import de.gmino.geobase.shared.domain.Distance;
import de.gmino.geobase.shared.domain.Duration;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.shared.domain.gen.RouteGen;

public class Route extends RouteGen implements Poi {
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
			Duration rideTime)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.shared.domain.LatLon)location,
			title,
			description,
			gpxUrl,
			color,
			characteristics,
			(de.gmino.geobase.shared.domain.Distance)length,
			(de.gmino.geobase.shared.domain.Duration)rideTime
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
