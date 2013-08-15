package de.gmino.geobase.shared.domain;

import de.gmino.meva.shared.Entity;


public interface Poi extends Entity{
	LatLon getLocation();
	void setLocation(LatLon location);
	String getTitle();
	String getDescription();
}
