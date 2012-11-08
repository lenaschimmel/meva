

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/meva/src/de/gmino/geobase/shared/map/MapView.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

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
}
