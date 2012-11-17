package de.gmino.geobase.android.map;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.map.Marker;
import de.gmino.geobase.shared.map.MarkerLayer;

public class MapsforgeOverlayLayer implements MarkerLayer {

	String name;
	private MapsforgeMapView mapView;

	public MapsforgeOverlayLayer(MapsforgeMapView mapsforgeMapView, String name) {
		this.mapView = mapsforgeMapView;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getMinZoom() {
		return 0;
	}

	@Override
	public double getMaxZoom() {
		return 100;
	}

	@Override
	public String getLicenseText() {
		return "Data (C) Greenmobile Innovations";
	}

	@Override
	public void setVisibility(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOpacity(double opacity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMarker(Marker marker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeMarker(Marker marker) {
		// TODO Auto-generated method stub

	}

	@Override
	public Marker newMarker(LatLon location, String title, String description, ImageUrl markerImage) {
		// TODO Auto-generated method stub
		return null;
	}

}
