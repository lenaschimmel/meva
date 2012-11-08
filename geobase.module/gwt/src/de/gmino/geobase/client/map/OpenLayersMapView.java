package de.gmino.geobase.client.map;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.JavaScriptObject;

import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.LatLonRect;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapLayer;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.geobase.shared.map.MapView;

public class OpenLayersMapView implements MapView {

	JavaScriptObject map;
	Map<Event,Collection<MapListener>> eventListeners;
	boolean clickListenerEnabled = false;
	
	public OpenLayersMapView(String elementName) {
		map = nCreateMap(elementName);
		eventListeners = new TreeMap<Event, Collection<MapListener>>();
	}
	
	private native JavaScriptObject nCreateMap(String elementName) /*-{
		var map = new $wnd.OpenLayers.Map (elementName);
        var layer = new $wnd.OpenLayers.Layer.OSM( "Simple OSM Map");
        map.addLayer(layer);
        map.pro1 = new $wnd.OpenLayers.Projection("EPSG:4326");
        map.pro2 =  map.getProjectionObject();
        map.setCenter(new $wnd.OpenLayers.LonLat(-71.147, 42.472).transform(map.pro1, map.pro2), 12);    
		map.doTransform = function(lonlat)
		{
			return lonlat.transform(map.pro1, map.pro2);
		}
		map.undoTransform = function(lonlat)
		{
			return lonlat.transform(map.pro2, map.pro1);
		}
		return map;
	}-*/;	
	
	@Override
	public LatLon getCenter() {
		double lat = nGetCenterLat();
		double lon = nGetCenterLon();
		return new LatLon(lat, lon);
	}
	
	private native double nGetCenterLat() /*-{
   		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
   		return map.getCenter().lat;
	}-*/;	
	
	private native double nGetCenterLon() /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		return map.getCenter().lon;
	}-*/;


	@Override
	public LatLonRect getVisibleArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCenter(LatLon center, boolean animate) {
		nSetCenter(center.getLatitude(), center.getLongitude());
	}
	
	private native void nSetCenter(double lat, double lon) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;	
		map.setCenter(new $wnd.OpenLayers.LonLat(lon, lat).transform(map.pro1, map.pro2));
	}-*/;

	@Override
	public void setCenterAndZoom(LatLon center, double zoom, boolean animate) {
		setCenter(center, animate);
		setZoom(zoom);
	}

	@Override
	public native void setZoom(double zoom)  /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		map.zoomTo(zoom);
	}-*/;


	@Override
	public double getMinZoom() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double getMaxZoom() {
		// TODO Auto-generated method stub
		return 166;
	}

	@Override
	public double getZoom() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean supportsFreeZoom() {
		return false;
	}

	@Override
	public void addLayer(MapLayer layer) {
		if (layer instanceof OpenLayersLayer) {
			OpenLayersLayer oll = (OpenLayersLayer) layer;
			nAddLayer((oll).getJso());
		}
		else
			throw new RuntimeException("Layer is not supported by OpenLayers.");
	}

	private native void nAddLayer(JavaScriptObject jso) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;	
		map.addLayer(jso);
	}-*/;

	@Override
	public void removeLayer(MapLayer layer) {
		if (layer instanceof OpenLayersLayer) {
			OpenLayersLayer oll = (OpenLayersLayer) layer;
			nRemoveLayer((oll).getJso());
		}
		else
			throw new RuntimeException("Layer is not supported by OpenLayers.");
	}

	private native void nRemoveLayer(JavaScriptObject jso) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;	
		map.removeLayer(jso);
	}-*/;
	
	public void addEventListener(Event event, MapListener listener)
	{
		if(!clickListenerEnabled)
		{
			nEnableClickListener();
			clickListenerEnabled = true;
		}
		
		Collection<MapListener> listeners = eventListeners.get(event);
		if(listeners == null)
		{
			listeners = new LinkedList<MapListener>();
			eventListeners.put(event, listeners);
		}
		listeners.add(listener);
	}
	
	private native void nEnableClickListener() /*-{
		var that = this;
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;	
		clickControl = $wnd.OpenLayers.Class($wnd.OpenLayers.Control, {                
                defaultHandlerOptions: {
                    'single': true,
                    'double': true,
                    'pixelTolerance': 0,
                    'stopSingle': false,
                    'stopDouble': false
                },

                initialize: function(options) {
                    this.handlerOptions = $wnd.OpenLayers.Util.extend(
                        {}, this.defaultHandlerOptions
                    );
                    $wnd.OpenLayers.Control.prototype.initialize.apply(
                        this, arguments
                    ); 
                    this.handler = new $wnd.OpenLayers.Handler.Click(
                        this, {
                            'mousedown': this.trigger,
                            'mouseup': this.trigger,
                            'click': this.trigger,
                            'rightclick': this.trigger,
                            'dblclick': this.trigger,
                            'dblrightclick': this.trigger
                        }, this.handlerOptions
                    );
                }, 

                trigger: function(e) {
                	var lonlat = map.undoTransform(map.getLonLatFromPixel(e.xy));
                    that.@de.gmino.geobase.client.map.OpenLayersMapView::handleEvent(DDLjava/lang/String;)(lonlat.lat, lonlat.lon, e.type);
                }

            });
            var map;
         	
         	var click = new clickControl();
            map.addControl(click);
            click.activate();
	}-*/;
	
	private void handleEvent(double lat, double lon, String type)
	{
		Event e = Event.valueOf(type);
		Collection<MapListener> listeners = eventListeners.get(e);
		if(listeners != null)
		{
			LatLon location = new LatLon(lat ,lon);
			for(MapListener listener : listeners)
				listener.onEvent(location, e);
		}
	}
}