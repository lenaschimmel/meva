package de.gmino.geobase.client.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.UnsafeNativeLong;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.map.IconRenderer;
import de.gmino.geobase.shared.map.Marker;
import de.gmino.geobase.shared.map.PopupCreator;
import de.gmino.geobase.shared.map.SmartLayer;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityTypeName;

public class OpenLayersSmartLayer implements SmartLayer<Canvas, Widget>, OpenLayersLayer {

	private static int zoomThreshold = 12;
	private String name;
	private JavaScriptObject vectorLayerJso;
	private JavaScriptObject popupLayerJso;
	private Set<Marker> markers;
	private OpenLayersMapView mapView;
	private TreeMap<EntityTypeName, GwtIconRenderer> rendererMap;
	private TreeMap<EntityTypeName, GwtPopupCreator> popupCreatorMap;
	private TreeMap<Long, Poi> pois;
	private TreeMap<Poi, JavaScriptObject> poiJsos;
	private TreeMap<Poi, DivElement> poiTooltipDivs;
	private Widget currentPopup;
	private int currentZoomLevel;

	public OpenLayersSmartLayer(String name, OpenLayersMapView mapView, int zoomThreshold) {
		this.zoomThreshold = zoomThreshold; 
		this.name = name;
		this.mapView = mapView;
		this.rendererMap = new TreeMap<EntityTypeName, GwtIconRenderer>();
		this.popupCreatorMap = new TreeMap<EntityTypeName, GwtPopupCreator>();
		this.pois = new TreeMap<Long, Poi>();
		this.poiJsos = new TreeMap<Poi, JavaScriptObject>();
		this.poiTooltipDivs = new TreeMap<Poi, DivElement>();
		markers = new TreeSet<Marker>();
		vectorLayerJso = nCreateVectorLayer(name, mapView.getMapJso());
		popupLayerJso = nCreatePopupLayer(name);
		mapView.addLayerJso(vectorLayerJso);
		mapView.addLayerJso(popupLayerJso);
		mapView.addLayer(this);
	}

	public void selectedPoi(long poiId) {
		if(currentZoomLevel <= zoomThreshold)
			return;
		Poi poi = pois.get(poiId);
		if (poiTooltipDivs.containsKey(poi)) {
			System.out.println("Tooltip already there");
			return;
		}
		Entity oAsEntity = (Entity) poi;
		GwtPopupCreator<Poi> creator = popupCreatorMap.get(oAsEntity.getType());
		if(creator == null)
			throw new RuntimeException("No PopupCreator for tyoe " + oAsEntity.getType().toString());
		Widget widget = creator.createTooltip(poi);
		DivElement div = mapView.createPopup(poi.getLocation(), poi.getId()
				+ "", 1, 1);
		poiTooltipDivs.put(poi, div);
		HTMLPanel.wrap(div).add(widget);
	}
	

	public void deselectedPoi(long poiId) {
		Poi poi = pois.get(poiId);
		DivElement div = poiTooltipDivs.get(poi);
		if (div == null) {
			System.out.println("Tooltip already gone");
			return;
		}
		div.removeFromParent();
		poiTooltipDivs.remove(poi);
	}

	public void clickedPoi(long poiId) {
		final Poi poi = pois.get(poiId);
		Entity oAsEntity = (Entity) poi;
		GwtPopupCreator<Poi> creator = popupCreatorMap.get(oAsEntity.getType());
		if(creator == null)
			throw new RuntimeException("No PopupCreator for tyoe " + oAsEntity.getType().toString());
		Widget widget = creator.createPopup(poi);

		if (currentPopup != null) {
			currentPopup.removeFromParent();
			currentPopup = null;
		}

		// TODO: This div will never be removed, but because of its size it
		// doesn't really matter.
		DivElement div = mapView.createPopup(poi.getLocation(), poi.getId()
				+ "", 1, 1);
		HTMLPanel.wrap(div).add(widget);
		final Widget inner = ((AbsolutePanel) widget).getWidget(0);
	
		currentPopup = widget;
		
		// the popup width is not always computeted correctly the first time and simple deferring doesn't help. Therefore, we ask for the width until it seems about right before using ist.
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				int offsetWidth = inner.getOffsetWidth();
				int offsetHeight = inner.getOffsetHeight();
				
				if(offsetWidth < 160)
					return true;
				
				mapView.panRectIntoMap(poi.getLocation(), offsetWidth,
						offsetHeight,  22, 38, true);
					
				return false;
			}
		}, 100);
	}

	// TODO: This is a marker layer, not a vector layer
	@UnsafeNativeLong
	private native JavaScriptObject nCreateVectorLayer(String name,
			JavaScriptObject mapJso)
	/*-{
		var layer = new $wnd.OpenLayers.Layer.Vector(name);

		var that = this;

		function selected(evt) {
			that.@de.gmino.geobase.client.map.OpenLayersSmartLayer::selectedPoi(J)(evt.feature.poiId);
		}
		layer.events.register("featureselected", layer, selected);
		function deselected(evt) {
			that.@de.gmino.geobase.client.map.OpenLayersSmartLayer::deselectedPoi(J)(evt.feature.poiId);
		}
		layer.events.register("featureunselected", layer, deselected);

		var control = new $wnd.OpenLayers.Control.SelectFeature(
				layer,
				{
					hover : true,
					highlightOnly : false,
					callbacks : {
						click : function(feat) {
							that.@de.gmino.geobase.client.map.OpenLayersSmartLayer::clickedPoi(J)(feat.poiId);
						}
					}
				});
		mapJso.addControl(control);
		control.activate();

		return layer;
	}-*/;

	private native JavaScriptObject nCreatePopupLayer(String name)
	/*-{
		var layer = new $wnd.OpenLayers.Layer(name);
		return layer;
	}-*/;

	@Override
	public void addMarkerIconRenderer(EntityTypeName type,
			IconRenderer<? extends Poi, Canvas> renderer) {
		rendererMap.put(type, (GwtIconRenderer<? extends Poi>) renderer);
	}

	@Override
	public void addMarkerPopupCreator(EntityTypeName type,
			PopupCreator<? extends Poi, Widget> creator) {
		popupCreatorMap.put(type, (GwtPopupCreator<? extends Poi>) creator);
	}

	@Override
	public void addPoi(Poi o) {
		if (pois.put(o.getId(), o) == null) {
			GwtIconRenderer<? super Poi> renderer = getRendererForPoi(o);
			String iconUrl;
			int width, height;
			if(currentZoomLevel > zoomThreshold)
			{
				iconUrl = renderer.getIconUrl(o);
				width = renderer.getWidth(o);
				height = renderer.getHeight(o);
			}
			else
			{
				iconUrl = renderer.getSmallIconUrl(o);
				width = 8;
				height = 8;
			}
			JavaScriptObject jso = nAddMarker(iconUrl, o.getLocation()
					.getLatitude(), o.getLocation().getLongitude(),
					mapView.getMapJso(), o.getId(), width,
					height);
			poiJsos.put(o, jso);
		}
	}

	public GwtIconRenderer<? super Poi> getRendererForPoi(Poi o) {
		Entity oAsEntity = (Entity) o;
		GwtIconRenderer<? super Poi> renderer = rendererMap.get(oAsEntity
				.getType());
		if (renderer == null)
			throw new RuntimeException("No IconRenderer defined for "
					+ oAsEntity.getType());
		return renderer;
	}

	@UnsafeNativeLong
	private native JavaScriptObject nAddMarker(String iconUrl, double latitude,
			double longitude, JavaScriptObject mapJso, long poiId, int width,
			int height)
	/*-{
		var layer = this.@de.gmino.geobase.client.map.OpenLayersSmartLayer::vectorLayerJso;

		var style_mark = $wnd.OpenLayers.Util.extend({},
				$wnd.OpenLayers.Feature.Vector.style['default']);

		// if graphicWidth and graphicHeight are both set, the aspect ratio
		// of the image will be ignored
		style_mark.graphicWidth = width;
		style_mark.graphicHeight = height;
		style_mark.graphicXOffset = -(style_mark.graphicWidth / 2);
		style_mark.graphicYOffset = -0.93 * (style_mark.graphicHeight);
		style_mark.externalGraphic = iconUrl;
		style_mark.graphicOpacity = 1;

		var point = new $wnd.OpenLayers.Geometry.Point(longitude, latitude);
		// var proj = new $wnd.OpenLayers.Projection("EPSG:4326");
		// point = point.transform(proj, mapJso.getProjectionObject());
		point = point.transform(mapJso.pro1, mapJso.pro2);
		var pointFeature = new $wnd.OpenLayers.Feature.Vector(point, null,
				style_mark);
		pointFeature.poiId = poiId;
		layer.addFeatures([ pointFeature ]);
		return pointFeature;
	}-*/;

	@Override
	public void removePoi(Poi o) {
		if (pois.remove(o.getId()) != null) {
			JavaScriptObject jso = poiJsos.get(o);
			if (jso != null) {
				nRemoveFeature(jso);
			}
		}
	}

	@Override
	public void updatePoi(Poi o) {
		removePoi(o);
		addPoi(o);
	}

	private native void nRemoveFeature(JavaScriptObject featureJso)
	/*-{
		var layer = this.@de.gmino.geobase.client.map.OpenLayersSmartLayer::vectorLayerJso;
		layer.removeFeatures([ featureJso ]);
	}-*/;
	

	public OpenLayersMapView getMapView() {
		return mapView;
	}
	

	public JavaScriptObject getLayerJso() {
		return vectorLayerJso;
	}
	
	public void setZoomLevel(int newZoomLevel)
	{
		int previousZoomLevel = currentZoomLevel;
		currentZoomLevel = newZoomLevel;
		if((previousZoomLevel <= zoomThreshold && newZoomLevel > zoomThreshold) || ( newZoomLevel <= zoomThreshold && previousZoomLevel > zoomThreshold))
		{
			Collection<Poi> poisCopy = new ArrayList<Poi>(pois.values());
			for(Poi poi : poisCopy)
				updatePoi(poi);
		}
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
		return "No License Text";
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
	public JavaScriptObject getJso() {
		return vectorLayerJso;
	}
}
