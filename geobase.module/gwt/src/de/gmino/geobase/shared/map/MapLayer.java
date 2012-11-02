

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/meva/src/de/gmino/geobase/shared/map/MapLayer.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

package de.gmino.geobase.shared.map;

public interface MapLayer {
	String getName();
	double getMinZoom();
	double getMaxZoom();
	String getLicenseText();
	void setVisibility(boolean visible);
	void setOpacity(double opacity);
}