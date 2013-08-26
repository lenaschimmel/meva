package de.gmino.geobase.shared.domain;

import de.gmino.meva.shared.Entity;


public interface PoiInterface extends Entity{
	LatLon getLocation();
	void setLocation(LatLon location);
	String getTitle();
}
