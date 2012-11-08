package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.LatLon;

public interface MapListener {
	void onEvent(LatLon location, Event event);
}
