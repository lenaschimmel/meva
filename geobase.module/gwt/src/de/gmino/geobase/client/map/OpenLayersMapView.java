package de.gmino.geobase.client.map;

import java.util.ArrayList;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Overflow;

import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.LatLonRect;
import de.gmino.geobase.shared.map.AbstractMapView;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapLayer;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.geobase.shared.map.MapProvider;
import de.gmino.geobase.shared.map.MarkerLayer;

public class OpenLayersMapView extends AbstractMapView {

	JavaScriptObject map;
	boolean clickListenerEnabled = false;
	ArrayList<OpenLayersLayer> layers;
	int borderLeft, borderTop, borderRight, borderBottom;
	
	public OpenLayersMapView(String elementName, String layerName) {
		map = nCreateMap(elementName, layerName);
		layers = new ArrayList<OpenLayersLayer>(); 
	}

	private native JavaScriptObject nCreateMap(String elementName, String layerName) /*-{
		var that = this;
		var map = new $wnd.OpenLayers.Map(elementName, {zoomMethod: null});
		var layer = new $wnd.OpenLayers.Layer.OSM("Simple OSM Map", 
				[
					"http://a.gmino.de:8090/"+layerName+"/${z}/${x}/${y}.png",
					"http://b.gmino.de:8090/"+layerName+"/${z}/${x}/${y}.png",
					"http://c.gmino.de:8090/"+layerName+"/${z}/${x}/${y}.png",
					"http://d.gmino.de:8090/"+layerName+"/${z}/${x}/${y}.png"
				],
				{isBaseLayer: true, visibility: true, transitionEffect: "resize"}
		);
		layer.tileOptions = {crossOriginKeyword: null};

		map.addLayer(layer);
		map.pro1 = new $wnd.OpenLayers.Projection("EPSG:4326");
		map.pro2 = map.getProjectionObject();
		map.setCenter(new $wnd.OpenLayers.LonLat(-71.147, 42.472).transform(map.pro1, map.pro2), 12);
		map.doTransform = function(lonlat) {
			return lonlat.transform(map.pro1, map.pro2);
		}
		map.undoTransform = function(lonlat) {
			return lonlat.transform(map.pro2, map.pro1);
		}
			
		var zoomChanged = function()
		{
			that.@de.gmino.geobase.client.map.OpenLayersMapView::handleZoom(I)(map.getZoom());
		}
		map.events.register("zoomend", map, zoomChanged);
		
		return map;
	}-*/;

	@Override
	public LatLon getCenter() {
		double lat = nGetCenterLat();
		double lon = nGetCenterLon();
		return new LatLon(lat, lon);
	}
	
	public void setBorders(int borderLeft, int borderTop, int borderRight, int borderBottom)
	{
		this.borderLeft = borderLeft;
		this.borderTop = borderTop;
		this.borderRight = borderRight;
		this.borderBottom = borderBottom;
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
		if(animate)
			nSetCenterAnimated(center.getLatitude(), center.getLongitude());
		else
			nSetCenter(center.getLatitude(), center.getLongitude());
	}
	
	public void panLatLonToPoint(LatLon latlon, int x, int y, boolean animate) {
		nPanLatLonToPoint(latlon.getLatitude(), latlon.getLongitude(), x, y, animate);
	}
	
	public void panRectIntoMap(LatLon latlon,  int w, int h, boolean animate) {
		nPanRectIntoMap(latlon.getLatitude(), latlon.getLongitude(), w, h, borderLeft, borderTop, borderRight, borderBottom, animate);
	}
	
	public void panRectIntoMap(LatLon latlon,  int w, int h, int offsetX, int offsetY, boolean animate) {
		nPanRectIntoMap(latlon.getLatitude(), latlon.getLongitude(), w, h, borderLeft + offsetX, borderTop + offsetY, borderRight - offsetX, borderBottom - offsetY, animate);
	}
	
	private native void nSetCenter(double lat, double lon) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		map.setCenter(new $wnd.OpenLayers.LonLat(lon, lat).transform(map.pro1,
		map.pro2));
	}-*/;
	
	private native void nSetCenterAnimated(double lat, double lon) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		map.panTo(new $wnd.OpenLayers.LonLat(lon, lat).transform(map.pro1,	map.pro2));
	}-*/;

	private native void nPanLatLonToPoint(double lat, double lon, int x, int y, boolean animate) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		var pixel = map.getPixelFromLonLat(new $wnd.OpenLayers.LonLat(lon, lat).transform(map.pro1,map.pro2));
		pixel.x += map.size.w / 2 - x;
		pixel.y += map.size.h / 2 - y;
		var newLonLat = map.getLonLatFromPixel(pixel);
		map.panTo(newLonLat);
	}-*/;
	
	private native void nPanLatLonWithOffset(double lat, double lon, int x, int y, boolean animate) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		var pixel = map.getPixelFromLonLat(new $wnd.OpenLayers.LonLat(lon, lat).transform(map.pro1,map.pro2));
		pixel.x += x;
		pixel.y += y;
		var newLonLat = map.getLonLatFromPixel(pixel);
		map.panTo(newLonLat);
	}-*/;	
	
	private native int nGetMapWidth() /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		return map.size.w;
	}-*/;

	private native int nGetMapHeight() /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		return map.size.h;
	}-*/;

	public native void nPanRectIntoMap(double lat, double lon, int w, int h, int borderLeft, int borderTop, int borderRight, int borderBottom, boolean animate) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		var lonlatPixel = map.getPixelFromLonLat(new $wnd.OpenLayers.LonLat(lon, lat).transform(map.pro1,map.pro2));
		var newLonLatX = lonlatPixel.x;
		var newLonLatY = lonlatPixel.y;
		if(newLonLatX < borderLeft)
			newLonLatX = borderLeft;
		if(newLonLatY < borderTop)
			newLonLatY = borderTop;
		if(newLonLatX + w > map.size.w - borderRight)
			newLonLatX = map.size.w - borderRight - w;
		if(newLonLatY + h > map.size.h - borderBottom)
			newLonLatY = map.size.h - borderBottom - h;
	
		var centerPixel = new $wnd.OpenLayers.Pixel(map.size.w / 2, map.size.h / 2);
		centerPixel.x -= newLonLatX - lonlatPixel.x;
		centerPixel.y -= newLonLatY - lonlatPixel.y;
		var newLonLat = map.getLonLatFromPixel(centerPixel);
		map.panTo(newLonLat);
	}-*/;
	
	@Override
	public void setZoom(double zoom) {
		nSetZoom(zoom);
		handleZoom((int)zoom);
	}

	private void handleZoom(int zoom) {
		for(OpenLayersLayer layer : layers)
			if (layer instanceof OpenLayersSmartLayer) {
				OpenLayersSmartLayer smartLayer = (OpenLayersSmartLayer) layer;
				smartLayer.setZoomLevel((int)zoom);
			}
	}

	public native void nSetZoom(double zoom) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		map.zoomTo(zoom);
	}-*/;

	public DivElement createPopup(LatLon position, String id, int width, int height) {
		DivElement inner = nCreatePopup(position.getLatitude(), position.getLongitude(), id, width, height);
		DivElement middle = (DivElement)inner.getParentElement();
		DivElement outer = (DivElement)middle.getParentElement();
		outer.removeChild(middle);
		
		outer.getStyle().setOverflow(Overflow.VISIBLE);
		outer.getStyle().setBackgroundColor("transparent");
		outer.getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
		outer.getParentElement().getStyle().setBackgroundColor("transparent");
		outer.getParentElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
		outer.getParentElement().getParentElement().getStyle().setBackgroundColor("transparent");
		return outer;
	}
	
	public native DivElement nCreatePopup(double lat, double lon, String id, int width, int height) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		var popup = new $wnd.OpenLayers.Popup(id,
	       new $wnd.OpenLayers.LonLat(lon, lat).transform(map.pro1, map.pro2),
	       new $wnd.OpenLayers.Size(width,height),
	       "",
	       false);
		map.addPopup(popup);
		return popup.contentDiv;
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
			addLayerJso((oll).getJso());
			layers.add(oll);
		} else
			throw new RuntimeException("Layer is not supported by OpenLayers.");
	}

	public native void addLayerJso(JavaScriptObject jso) /*-{
														var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
														map.addLayer(jso);
														}-*/;

	@Override
	public void removeLayer(MapLayer layer) {
		if (layer instanceof OpenLayersLayer) {
			OpenLayersLayer oll = (OpenLayersLayer) layer;
			nRemoveLayer((oll).getJso());
		} else
			throw new RuntimeException("Layer is not supported by OpenLayers.");
	}

	private native void nRemoveLayer(JavaScriptObject jso) /*-{
															var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
															map.removeLayer(jso);
															}-*/;

	public void addEventListener(Event event, MapListener listener) {
		if (!clickListenerEnabled) {
			nEnableClickListener();
			clickListenerEnabled = true;
		}

		super.addEventListener(event, listener);
	}

	protected void handleEvent(double lat, double lon, String typeName) {
		try{
			super.handleEvent(lat, lon, Event.valueOf(typeName));
		} catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	private native int nGetZoom() /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		return map.getZoom(); 
	}-*/;
	
	private native void nEnableClickListener() /*-{
												var that = this;
												var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
												clickControl = $wnd.OpenLayers
												.Class(
												$wnd.OpenLayers.Control,
												{
												defaultHandlerOptions : {
												'single' : true,
												'double' : true,
												'pixelTolerance' : 0,
												'stopSingle' : false,
												'stopDouble' : true
												},

												initialize : function(options) {
												this.handlerOptions = $wnd.OpenLayers.Util
												.extend({}, this.defaultHandlerOptions);
												$wnd.OpenLayers.Control.prototype.initialize
												.apply(this, arguments);
												this.handler = new $wnd.OpenLayers.Handler.Click(
												this, {
												'mousedown' : this.trigger,
												'mouseup' : this.trigger,
												'click' : this.trigger,
												'rightclick' : this.trigger,
												'dblclick' : this.trigger,
												'dblrightclick' : this.trigger,
												'zoomed' : this.trigger
												}, this.handlerOptions);
												},

												trigger : function(e) {
												var lonlat = map.undoTransform(map
												.getLonLatFromPixel(e.xy));
												that.@de.gmino.geobase.client.map.OpenLayersMapView::handleEvent(DDLjava/lang/String;)(lonlat.lat, lonlat.lon, e.type);
												}

												});
												var map;

												var click = new clickControl();
												map.addControl(click);
												click.activate();
												}-*/;

	@Override
	public MarkerLayer newMarkerLayer(String name) {
		return new OpenLayersMarkerLayer(name, this);
	}

	@Override
	public OpenLayersSmartLayer newSmartLayer(String name, int zoomThreshold) {
		return new OpenLayersSmartLayer(name, this, zoomThreshold);
	}

	@Override
	public MapLayer newMapLayer(MapProvider provider) {
		nCreateLayer(provider.toString().toLowerCase());
		return null;
	}

	public void newMapLayer(String layerName) {
		nCreateLayer(layerName); 
	}
	
	private native void nCreateLayer(String layerName) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		var layer;
		
		var apiKey = "Ah0O_YCYQ7m711V6lrEMLXiGs_9BUbsPhSiVpJqaEFi89UheesY4EXUCrEuMbmwp";
		
		if(layerName == "Bing Road")
		{
			layer = new $wnd.OpenLayers.Layer.Bing({
                name: "Bing Road",
                key: apiKey,
                type: "Road"
            });
		}
		else if(layerName == "Bing Hybrid")
		{
			layer = new $wnd.OpenLayers.Layer.Bing({
                name: "Bing Hybrid",
                key: apiKey,
                type: "AerialWithLabels"
            });
		}
		else if(layerName == "Bing Aerial")
		{
			layer = new $wnd.OpenLayers.Layer.Bing({
                name: "Bing Aerial",
                key: apiKey,
                type: "Aerial"
            });
		}
		else 
		{
			layer = new $wnd.OpenLayers.Layer.OSM("Simple OSM Map", 
				[
					"http://a.gmino.de:8090/"+layerName+"/${z}/${x}/${y}.png",
					"http://b.gmino.de:8090/"+layerName+"/${z}/${x}/${y}.png",
					"http://c.gmino.de:8090/"+layerName+"/${z}/${x}/${y}.png",
					"http://d.gmino.de:8090/"+layerName+"/${z}/${x}/${y}.png"
				],
				{isBaseLayer: true, visibility: true, transitionEffect: "resize"}
			);
		}
		layer.tileOptions = {crossOriginKeyword: null};

		map.addLayer(layer);
		map.setBaseLayer(layer);
	}-*/;

	public JavaScriptObject getMapJso() {
		return map;
	}

	public void setBorderLeft(int borderLeft) {
		this.borderLeft = borderLeft;
	}

	public void setBorderTop(int borderTop) {
		this.borderTop = borderTop;
	}

	public void setBorderRight(int borderRight) {
		this.borderRight = borderRight;
	}

	public void setBorderBottom(int borderBottom) {
		this.borderBottom = borderBottom;
	}
}
