package de.gmino.geobase.shared.map.components;

import java.util.Collection;

import de.gmino.geobase.shared.domain.GeoObject;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.geobase.shared.map.MapView;
import de.gmino.geobase.shared.map.Marker;
import de.gmino.geobase.shared.map.MarkerLayer;
import de.gmino.geobase.shared.request.QueryGeoObject;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class MevaMarkersReadOnly implements MapListener {
	private MapView mapView;
	private MarkerLayer overlay;

	public MevaMarkersReadOnly(MapView mapview) {
		this.mapView = mapview;
		overlay = mapView.newMarkerLayer("Meva Markers");
		mapView.addLayer(overlay);
		mapView.addEventListener(Event.zoom, this);
		mapView.addEventListener(Event.move, this);
		loadMarkers();
	}

	private void loadMarkers() {
		// Then, we send the input to the server.
		// Request all shops near you
		final LatLon myLocation = new LatLon(52.2723, 10.53547);
		EntityQuery q = new QueryGeoObject(mapView.getVisibleArea(), 100);
		Requests.getLoadedEntitiesByQuery(GeoObject.type, q, new RequestListener<GeoObject>() {
			@Override
			public void onFinished(final Collection<GeoObject> objects) {
				for (GeoObject object : objects) {
					Marker marker = overlay.newMarker(object.getLocation(), object.getTitle(), object.getDescription(), object.getMarkerImage());
					overlay.addMarker(marker);
				}
			}
		});
	}

	@Override
	public void onEvent(LatLon location, Event event) {
		if(event.isChangingView())
			loadMarkers();
	}
}
