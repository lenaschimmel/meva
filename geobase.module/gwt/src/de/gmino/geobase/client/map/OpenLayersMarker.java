package de.gmino.geobase.client.map;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.JavaScriptObject;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.Marker;
import de.gmino.geobase.shared.map.MarkerListener;

public class OpenLayersMarker implements Marker {	
	JavaScriptObject jso;

	String title;
	String description;
	LatLon Location;
	ImageUrl icon;
	int internalId;
	static int nextId = 0;
	Map<Event,Collection<MarkerListener>> eventListeners;

	public OpenLayersMarker(LatLon location, String title, String description, ImageUrl icon, OpenLayersMapView map) {
		super();
		Location = location;
		this.title = title;
		this.description = description;
		this.icon = icon;
		this.jso = nCreateMarker(location.getLatitude(), location.getLongitude(), icon.getUrl(), 32, 32, map.map);
		this.internalId = nextId++;
		eventListeners = new TreeMap<Event, Collection<MarkerListener>>();
	}

	private native JavaScriptObject nCreateMarker(double lat, double lon, String iconUrl, int w, int h, JavaScriptObject mapJso) /*-{
		var size = new $wnd.OpenLayers.Size(w, h);
		var offset = new $wnd.OpenLayers.Pixel(-(size.w / 2), -size.h);
		var icon = new $wnd.OpenLayers.Icon(iconUrl, size, offset);
		var marker = new $wnd.OpenLayers.Marker(mapJso.doTransform(new $wnd.OpenLayers.LonLat(lon, lat)), icon);
		return marker;
	}-*/;
	
	public void addEventListener(Event event, MarkerListener listener)
	{
		Collection<MarkerListener> listeners = eventListeners.get(event);
		if(listeners == null)
		{
			listeners = new LinkedList<MarkerListener>();
			eventListeners.put(event, listeners);
			nRegisterEvent(event.toString(), event);
		}
		listeners.add(listener);
	}
	
	private native void nRegisterEvent(String name, Event eventEnum) /*-{
		var marker = this.@de.gmino.geobase.client.map.OpenLayersMarker::jso;	
		var that = this;
		marker.events.register(name, marker, function(evt) {
			that.@de.gmino.geobase.client.map.OpenLayersMarker::handleEvent(Lde/gmino/geobase/shared/map/Event;)(eventEnum);
			OpenLayers.Event.stop(evt);
		});
	}-*/;

	public void handleEvent(Event e) {
		Collection<MarkerListener> listeners = eventListeners.get(e);
		if(listeners != null)
		{
			for(MarkerListener listener : listeners)
				listener.onEvent(this, e);
		}
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setLocation(LatLon location) {
		// TODO Auto-generated method stub
	}

	@Override
	public LatLon getLocation() {
		return Location;
	}

	@Override
	public ImageUrl getIconUrl() {
		return icon;
	}

	@Override
	public int compareTo(Marker that) {
		if (that instanceof OpenLayersMarker) {
			OpenLayersMarker olm = (OpenLayersMarker) that;
			return new Integer(this.internalId).compareTo(olm.internalId);
		}
		return 0;
	}

	@Override
	public int getInternalId() {
		return internalId;
	}

}
