package de.gmino.geobase.android.map;

import org.osmdroid.views.overlay.Overlay;

import de.gmino.geobase.shared.map.MapLayer;
import de.gmino.geobase.shared.map.MapView;

public class OsmdroidOverlayLayer implements MapLayer {

	private Overlay inner;
	private MapView mapView;

	public OsmdroidOverlayLayer(Overlay ov, MapView mapView) {
		inner = ov;
		this.mapView = mapView;
	}

	@Override
	public String getName() {
		return "My Location";
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
	public String getLicenseText() {
		return null;
	}

	@Override
	public void setVisibility(boolean visible) {
		inner.setEnabled(visible);
	}

	@Override
	public void setOpacity(double opacity) {
		// TODO Auto-generated method stub
	}

	public Overlay getOsmdroidImplemenatation() {
		return inner;
	}

}
