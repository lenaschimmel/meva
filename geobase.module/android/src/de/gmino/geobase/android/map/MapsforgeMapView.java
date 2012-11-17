package de.gmino.geobase.android.map;

import org.mapsforge.android.maps.MapView;
import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.GeoPoint;

import android.content.Context;
import android.view.View;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.LatLonRect;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapLayer;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.geobase.shared.map.MapProvider;
import de.gmino.geobase.shared.map.MarkerLayer;


public class MapsforgeMapView implements de.gmino.geobase.shared.map.MapView  {

	MapView inner;
	
	public MapsforgeMapView(Context context) {
		inner = new MapView(context);
		initView();
	}

	private void initView() {
		inner.setBuiltInZoomControls(true);
		inner.setClickable(true);
		inner.setFocusable(true);
	}	
	
	public MapsforgeMapView(MapView view) {
		inner = view;
		initView();
	}
	
	public View getAndroidView()
	{
		return inner;
	}
	
	@Override
	public LatLon getCenter() {
		GeoPoint center = inner.getMapViewPosition().getCenter();
		return new LatLon(center.latitude, center.longitude);
	}

	@Override
	public LatLonRect getVisibleArea() {
		BoundingBox bb = inner.getMapViewPosition().getBoundingBox();
		GeoPoint center = bb.getCenterPoint();
		double halfLatSpan = bb.getLatitudeSpan() * 0.5;
		double halfLonSpan = bb.getLongitudeSpan() * 0.5;
		return new LatLonRect(center.latitude - halfLatSpan, center.longitude - halfLonSpan, center.latitude + halfLatSpan, center.longitude + halfLonSpan);
	}

	@Override
	public void setCenter(LatLon center, boolean animate) {
		inner.getMapViewPosition().setCenter(new GeoPoint(center.getLatitude(), center.getLongitude()));
	}

	@Override
	public void setCenterAndZoom(LatLon center, double zoom, boolean animate) {
		setCenter(center, animate);
		setZoom(zoom);
	}

	@Override
	public void setZoom(double zoom) {
		inner.getMapViewPosition().setZoomLevel((byte)zoom);
	}

	@Override
	public double getMinZoom() {
		return 0;
	}

	@Override
	public double getMaxZoom() {
		return 28;
	}

	@Override
	public double getZoom() {
		return inner.getMapViewPosition().getZoomLevel();
	}

	@Override
	public boolean supportsFreeZoom() {
		return false;
	}

	@Override
	public void addLayer(MapLayer layer) {
		
	}

	@Override
	public void removeLayer(MapLayer layer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addEventListener(Event event, MapListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public MarkerLayer newMarkerLayer(String name) {
		return new MapsforgeOverlayLayer(this, name);
	}

	@Override
	public MapLayer newMapLayer(MapProvider provider) {
		// TODO Auto-generated method stub
		return null;
	}

}
