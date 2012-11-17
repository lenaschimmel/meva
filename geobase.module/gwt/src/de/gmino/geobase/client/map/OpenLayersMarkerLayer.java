package de.gmino.geobase.client.map;

import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.core.client.JavaScriptObject;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.map.Marker;
import de.gmino.geobase.shared.map.MarkerLayer;

public class OpenLayersMarkerLayer implements MarkerLayer, OpenLayersLayer {

	private String name;
	private JavaScriptObject jso;
	private Set<Marker> markers;
	private OpenLayersMapView mapView;

	public OpenLayersMarkerLayer(String name, OpenLayersMapView mapView) {
		this.name = name;
		this.mapView = mapView;
		markers = new TreeSet<Marker>();
		jso = nCreateLayer(name);
	}

	private native JavaScriptObject nCreateLayer(String name) /*-{
																return new $wnd.OpenLayers.Layer.Markers(name);
																}-*/;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getMinZoom() {
		return 1;
	}

	@Override
	public double getMaxZoom() {
		return 17;
	}

	@Override
	public String getLicenseText() {
		return "Data (C) Greenmobile Innovations UG";
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
		if (markers.add(marker)) {
			OpenLayersMarker olm = (OpenLayersMarker) marker;
			nAddMarker(olm.jso);
		}
	}

	private native void nAddMarker(JavaScriptObject marker) /*-{
															var layer = this.@de.gmino.geobase.client.map.OpenLayersMarkerLayer::jso;
															layer.addMarker(marker);
															}-*/;

	@Override
	public void removeMarker(Marker marker) {
		if (markers.remove(marker)) {
			OpenLayersMarker olm = (OpenLayersMarker) marker;
			nRemoveMarker(olm.jso);
		}
	}

	private native void nRemoveMarker(JavaScriptObject marker) /*-{
																var layer = this.@de.gmino.geobase.client.map.OpenLayersMarkerLayer::jso;
																layer.removeMarker(marker);
																}-*/;

	@Override
	public JavaScriptObject getJso() {
		return jso;
	}

	@Override
	public Marker newMarker(LatLon location, String title, String description, ImageUrl markerImage) {
		return new OpenLayersMarker(location, title, description, markerImage, this.mapView);
	}

}
