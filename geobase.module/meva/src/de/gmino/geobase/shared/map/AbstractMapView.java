package de.gmino.geobase.shared.map;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import de.gmino.geobase.shared.domain.LatLon;

public abstract class AbstractMapView implements MapView {
	Map<Event,Collection<MapListener>> eventListeners;
	
	public AbstractMapView()
	{
		eventListeners = new TreeMap<Event, Collection<MapListener>>();
	}
	
	
	protected void handleEvent(double lat, double lon, Event type)
	{
		Collection<MapListener> listeners = eventListeners.get(type);
		if(listeners != null)
		{
			LatLon location = new LatLon(lat ,lon);
			for(MapListener listener : listeners)
				listener.onEvent(location, type);
		}
	}
	
	public void addEventListener(Event event, MapListener listener)
	{
		
		
		Collection<MapListener> listeners = eventListeners.get(event);
		if(listeners == null)
		{
			listeners = new LinkedList<MapListener>();
			eventListeners.put(event, listeners);
		}
		listeners.add(listener);
	}
	
	@Override
	public void setCenterAndZoom(LatLon center, double zoom, boolean animate) {
		setCenter(center, animate);
		setZoom(zoom);
	}
}
