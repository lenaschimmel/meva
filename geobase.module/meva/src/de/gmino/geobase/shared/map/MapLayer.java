package de.gmino.geobase.shared.map;

public interface MapLayer {
	String getName();
	double getMinZoom();
	double getMaxZoom();
	String getLicenseText();
	void setVisibility(boolean visible);
	void setOpacity(double opacity);
}