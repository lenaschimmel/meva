package de.gmino.geobase.client.map;

import com.google.gwt.core.client.JavaScriptObject;

import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.LatLonRect;
import de.gmino.geobase.shared.map.LayerList;
import de.gmino.geobase.shared.map.MapView;

public class OpenLayersMapView implements MapView {

	JavaScriptObject map;
	
	public OpenLayersMapView() {
		map = nCreateMap();
	}
	
	private native JavaScriptObject nCreateMap() /*-{
		var map = new $wnd.OpenLayers.Map ('map');
		var layer = new $wnd.OpenLayers.Layer.OSM( "Simple OSM Map");
            map.addLayer(layer);
            map.setCenter(
                new $wnd.OpenLayers.LonLat(-71.147, 42.472).transform(
                    new $wnd.OpenLayers.Projection("EPSG:4326"),
                    map.getProjectionObject()
                ), 12
            );    
        this.pro1 = new $wnd.OpenLayers.Projection("EPSG:4326");
        this.pro2 =  map.getProjectionObject();
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
		
		map.setCenter(new $wnd.OpenLayers.LonLat(lon, lat).transform(this.pro1, this.pro2));
	}-*/;

	@Override
	public void setCenterAndZoom(LatLon center, double zoom, boolean animate) {
		setCenter(center, animate);
		setZoom(zoom);
	}

	@Override
	public native void setZoom(double zoom)  /*-{
		var map = this.@de.gmino.geobase.client.map.OpenLayersMapView::map;
		map.setZoom(zoom);
	}-*/;


	@Override
	public double getMinZoom() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMaxZoom() {
		// TODO Auto-generated method stub
		return 0;
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
	public LayerList getLayerList() {
		// TODO Auto-generated method stub
		return null;
	}

}
