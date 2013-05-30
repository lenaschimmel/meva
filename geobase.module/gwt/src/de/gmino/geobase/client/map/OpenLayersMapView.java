package de.gmino.geobase.client.map;

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
import de.gmino.geobase.shared.map.SmartLayer;

public class OpenLayersMapView extends AbstractMapView {

	JavaScriptObject map;
	boolean clickListenerEnabled = false;

	public OpenLayersMapView(String elementName, String layerName) {
		map = nCreateMap(elementName, layerName);
	}

	private native JavaScriptObject nCreateMap(String elementName, String layerName) /*-{
		var map = new $wnd.OpenLayers.Map(elementName);
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
		if(animate)
			nSetCenterAnimated(center.getLatitude(), center.getLongitude());
		else
			nSetCenter(center.getLatitude(), center.getLongitude());
	}
	
	public void panLatLonToPoint(LatLon latlon, int x, int y, boolean animate) {
		nPanLatLonToPoint(latlon.getLatitude(), latlon.getLongitude(), x, y, animate);
	}
	
	public void panRectIntoMap(LatLon latlon,  int w, int h, int border, boolean animate) {
		nPanRectIntoMap(latlon.getLatitude(), latlon.getLongitude(), w, h, border, animate);
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

	public native void nPanRectIntoMap(double lat, double lon, int w, int h, int border, boolean animate) /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		var lonlatPixel = map.getPixelFromLonLat(new $wnd.OpenLayers.LonLat(lon, lat).transform(map.pro1,map.pro2));
		var newLonLatX = lonlatPixel.x;
		var newLonLatY = lonlatPixel.y;
		if(newLonLatX < border)
			newLonLatX = border;
		if(newLonLatY < border)
			newLonLatY = border;
		if(newLonLatX + w > map.size.w - border)
			newLonLatX = map.size.w - border - w;
		if(newLonLatY + h > map.size.h - border)
			newLonLatY = map.size.h - border - h;
	
		var centerPixel = new $wnd.OpenLayers.Pixel(map.size.w / 2, map.size.h / 2);
		centerPixel.x -= newLonLatX - lonlatPixel.x;
		centerPixel.y -= newLonLatY - lonlatPixel.y;
		var newLonLat = map.getLonLatFromPixel(centerPixel);
		map.panTo(newLonLat);
	}-*/;
	
	@Override
	public native void setZoom(double zoom) /*-{
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
												'dblrightclick' : this.trigger
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
	public OpenLayersSmartLayer newSmartLayer(String name) {
		return new OpenLayersSmartLayer(name, this);
	}

	@Override
	public MapLayer newMapLayer(MapProvider provider) {
		// TODO support Layers
		return null;
	}

	public JavaScriptObject getMapJso() {
		return map;
	}
}
