package de.gmino.geobase.android.map;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.BitmapTileSourceBase;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;

import android.content.Context;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.LatLonRect;
import de.gmino.geobase.shared.map.AbstractMapView;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapLayer;
import de.gmino.geobase.shared.map.MapProvider;
import de.gmino.geobase.shared.map.MarkerLayer;
import de.gmino.geobase.shared.map.SmartLayer;

public class OsmdroidMapView extends AbstractMapView implements MapListener {

	MapView inner;
	private ResourceProxy resourceProxy;
	private Context context;

	public OsmdroidMapView(Context context) {
		this.context = context;
		inner = new MapView(context, null);
		resourceProxy = new DefaultResourceProxyImpl(context.getApplicationContext());
		initView();
	}

	private void initView() {
		inner.setBuiltInZoomControls(true);
		inner.setClickable(true);
		inner.setFocusable(true);
	}

	public OsmdroidMapView(MapView view) {
		inner = view;
		initView();
	}

	@Override
	public LatLon getCenter() {
		IGeoPoint center = inner.getMapCenter();
		return new LatLon(e6td(center.getLatitudeE6()), e6td(center.getLongitudeE6()));
	}

	@Override
	public LatLonRect getVisibleArea() {
		BoundingBoxE6 box = inner.getBoundingBox();
		return new LatLonRect(e6td(box.getLatSouthE6()), e6td(box.getLonWestE6()), e6td(box.getLatNorthE6()), e6td(box.getLonEastE6()));
	}

	private double e6td(final int i) {
		return ((double) i) * 0.000001;
	}

	@Override
	public void setCenter(LatLon center, boolean animate) {
		IGeoPoint point = new GeoPoint(center.getLatitude(), center.getLongitude());
		if (animate)
			inner.getController().animateTo(point);
		else
			inner.getController().setCenter(point);
	}

	@Override
	public void setZoom(double zoom) {
		inner.getController().setZoom((int) zoom);
	}

	@Override
	public double getMinZoom() {
		return 0;
	}

	@Override
	public double getMaxZoom() {
		return 18;
	}

	@Override
	public double getZoom() {
		return inner.getZoomLevel();
	}

	@Override
	public boolean supportsFreeZoom() {
		return false;
	}

	@Override
	public void addLayer(MapLayer layer) {
		if (layer instanceof OsmdroidMapLayer)
			inner.setTileSource((OsmdroidMapLayer) layer);
		else if (layer instanceof OsmdroidOverlayLayer)
			inner.getOverlays().add(((OsmdroidOverlayLayer) layer).getOsmdroidImplemenatation());
	}

	@Override
	public void removeLayer(MapLayer layer) {
		// TODO Auto-generated method stub

	}

	@Override
	public MarkerLayer newMarkerLayer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapLayer newMapLayer(MapProvider provider) {
		OsmdroidMapLayer source = OsmdroidTileSources.getSourceByProvider(provider);
		return source;
	}

	public MapLayer newMyLocationOverlay() {
		return new OsmdroidOverlayLayer(new MyLocationOverlay(context, inner, resourceProxy), this);
	}

	@Override
	public boolean onScroll(ScrollEvent event) {
		handleEvent(0, 0, Event.move);
		return true;
	}

	@Override
	public boolean onZoom(ZoomEvent arg0) {
		handleEvent(0, 0, Event.zoom);
		return true;
	}

	@Override
	public SmartLayer newSmartLayer(String name, int zoomThreshold) {
		// FIXME FEHLT!
		return null;
	}
}
