package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.LatLonRect;

public interface MapView {
	LatLon getCenter();

	LatLonRect getVisibleArea();

	void setCenter(LatLon center, boolean animate);

	void setCenterAndZoom(LatLon center, double zoom, boolean animate);

	void setZoom(double zoom);

	double getMinZoom();

	double getMaxZoom();

	double getZoom();

	boolean supportsFreeZoom();

	void addLayer(MapLayer layer);

	void removeLayer(MapLayer layer);

	public void addEventListener(Event event, MapListener listener);

	public MarkerLayer newMarkerLayer(String name);

	public MapLayer newMapLayer(MapProvider provider);

	public SmartLayer newSmartLayer(String name);
}
