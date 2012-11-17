package de.gmino.cycleway.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

import de.gmino.cycleway.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.map.OpenLayersMapView;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.geobase.shared.map.MapView;
import de.gmino.geobase.shared.map.Marker;
import de.gmino.geobase.shared.map.MarkerLayer;
import de.gmino.geobase.shared.map.MarkerListener;
import de.gmino.geobase.shared.map.components.MevaMarkersReadOnly;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.Requests;

public class CyclewayGwt implements EntryPoint {

	private MapView map;
	private MarkerLayer markerLayer;
	private MarkerListener markerClickListener;
	private MapListener mapDoubleClickListener;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());
		Requests.setImplementation(new NetworkRequestsImplAsyncJson(Util
				.getBaseUrl()));

		// Create the map
		map = new OpenLayersMapView("map");
		map.setCenterAndZoom(new LatLon(52.27617, 10.53216), 15, false);
		markerLayer = map.newMarkerLayer("Coupony-Partner");
		map.addLayer(markerLayer);

		this.markerClickListener = new MarkerListener(){@Override
		public void onEvent(Marker marker, Event event) {
			if(event == Event.click)
			{
				Window.alert("Click made it through: " + marker.getTitle());
			}
		}};
		
		this.mapDoubleClickListener = new MapListener() {
			
			@Override
			public void onEvent(de.gmino.geobase.shared.domain.LatLon location, Event event) {
				Window.alert("Double Click at " + location);
			}
		};
		map.addEventListener(Event.dblclick, mapDoubleClickListener);
		
		new MevaMarkersReadOnly(map);
	}

}
