package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;

public interface Marker extends Comparable<Marker> {
	String getTitle();
	String getDescription();
	void setLocation(LatLon location);
	LatLon getLocation();
	ImageUrl getIconUrl();
	int getInternalId();
}
