package de.gmino.geobase.android.map;

import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.BitmapTileSourceBase;

import de.gmino.geobase.shared.map.MapLayer;

public class OsmdroidMapLayer extends BitmapTileSourceBase implements MapLayer {

	private String readableName;

	@Override
	public String getTileRelativeFilenameString(MapTile tile) {
		return "http://gmino.de:8090/"+mName+"/"+tile.getZoomLevel()+"/"+tile.getX()+"/"+tile.getY()+".png";
	}
	
	public OsmdroidMapLayer(String urlName, String readableName) {
		super(urlName, null, 0, 17, 256, "png");
		this.readableName = readableName;
	}

	@Override
	public String localizedName(ResourceProxy proxy) {
		return readableName;
	}

	@Override
	public String getName() {
		return readableName;
	}

	@Override
	public double getMinZoom() {
		return 0;
	}

	@Override
	public double getMaxZoom() {
		// TODO Auto-generated method stub
		return 17;
	}

	@Override
	public String getLicenseText() {
		return "(C) OpenStreetMap and Contributors";
	}

	@Override
	public void setVisibility(boolean visible) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setOpacity(double opacity) {
		// TODO Auto-generated method stub
	}
}