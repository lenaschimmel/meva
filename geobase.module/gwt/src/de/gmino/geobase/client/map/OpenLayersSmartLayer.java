package de.gmino.geobase.client.map;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.map.IconRenderer;
import de.gmino.geobase.shared.map.Marker;
import de.gmino.geobase.shared.map.PopupCreator;
import de.gmino.geobase.shared.map.SmartLayer;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityTypeName;

public class OpenLayersSmartLayer implements SmartLayer<Canvas, Widget> {

	private String name;
	private JavaScriptObject vectorLayerJso;
	private JavaScriptObject popupLayerJso;
	private Set<Marker> markers;
	private OpenLayersMapView mapView;
	private TreeMap<EntityTypeName, GwtIconRenderer> rendererMap;
	private TreeMap<EntityTypeName, GwtPopupCreator> popupCreatorMap;
	private Set<Poi> pois;

	public OpenLayersSmartLayer(String name, OpenLayersMapView mapView) {
		this.name = name;
		this.mapView = mapView;
		this.rendererMap = new TreeMap<EntityTypeName, GwtIconRenderer>();
		this.popupCreatorMap = new TreeMap<EntityTypeName, GwtPopupCreator>();
		this.pois = new TreeSet<Poi>();
		markers = new TreeSet<Marker>();
		vectorLayerJso = nCreateVectorLayer(name);
		popupLayerJso  = nCreatePopupLayer(name);
		mapView.addLayerJso(vectorLayerJso);
		mapView.addLayerJso(popupLayerJso);
	}

	// TODO: This is a marker layer, not a vector layer
	private native JavaScriptObject nCreateVectorLayer(String name) 
	/*-{
		return new $wnd.OpenLayers.Layer.Vector(name);
	}-*/;	
	
	private native JavaScriptObject nCreatePopupLayer(String name) 
	/*-{
		return new $wnd.OpenLayers.Layer(name);
	}-*/;

	@Override
	public void addMarkerIconRenderer(EntityTypeName type,
			IconRenderer<? extends Poi, Canvas> renderer) {
		rendererMap.put(type, (GwtIconRenderer) renderer);
	}

	@Override
	public void addMarkerPopupCreator(EntityTypeName type,
			PopupCreator<? extends Poi, Widget> creator) {
		popupCreatorMap.put(type, (GwtPopupCreator) creator);
	}

	@Override
	public void addPoi(Poi o) {
		if(pois.add(o))
		{
			Entity oAsEntity = (Entity)o;
			GwtIconRenderer<? super Poi> renderer = rendererMap.get(oAsEntity.getType());
			if(renderer == null)
				throw new RuntimeException("No IconRenderer defined for " + oAsEntity.getType());
			String iconUrl = renderer.getIconUrl(o);
			System.out.println("At " + o.getLocation() + ": " + iconUrl);
			nAddMarker(iconUrl, o.getLocation().getLatitude(), o.getLocation().getLongitude());
		}
	}

	private native void nAddMarker(String iconUrl, double latitude, double longitude) 
	/*-{
		var layer = this.@de.gmino.geobase.client.map.OpenLayersSmartLayer::vectorLayerJso;
		//var map   = this.@de.gmino.geobase.client.map.OpenLayersSmartLayer::mapView;
		
		var style_mark = $wnd.OpenLayers.Util.extend({}, $wnd.OpenLayers.Feature.Vector.style['default']);
            
        // if graphicWidth and graphicHeight are both set, the aspect ratio
        // of the image will be ignored
        style_mark.graphicWidth = 24;
        style_mark.graphicHeight = 20;
        style_mark.graphicXOffset = 10; // default is -(style_mark.graphicWidth/2);
        style_mark.graphicYOffset = -style_mark.graphicHeight;
        style_mark.externalGraphic = iconUrl;
        
        var point = new $wnd.OpenLayers.Geometry.Point(longitude, latitude);
        //point.transform(map.map.pro1, map.map.pro2);
        var pointFeature = new $wnd.OpenLayers.Feature.Vector(point,null,style_mark);
		
		layer.addFeatures([pointFeature]);
	}-*/;

	@Override
	public void removePoi(Poi o) {
		if(pois.remove(o))
		{
			
		}
	}
}
