package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;

public interface MarkerLayer extends MapLayer {
	void addMarker(Marker marker);

	void removeMarker(Marker marker);

	Marker newMarker(LatLon location, String title, String description, ImageUrl markerImage);
}
