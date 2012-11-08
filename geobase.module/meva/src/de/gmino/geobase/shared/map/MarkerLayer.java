package de.gmino.geobase.shared.map;

public interface MarkerLayer extends MapLayer {
	void addMarker(Marker marker);
	void removeMarker(Marker marker);
}
