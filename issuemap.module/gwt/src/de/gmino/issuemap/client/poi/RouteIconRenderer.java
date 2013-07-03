package de.gmino.issuemap.client.poi;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.TextMetrics;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.Route;

public class RouteIconRenderer extends GwtIconRenderer<Route> {
	
	private static final int HEIGHT = 22;
	private static final int WIDTH = 250;
	ImageUrlLoader loader = ImageUrlLoader.getInstance();
	
	public void getIconHash(de.gmino.geobase.shared.map.Hasher hash, Route route) 
	{
		hash.hashObject(route.getTitle());
	}
	
	@Override
	public void renderIcon(Canvas can, Route route) {
		can.setCoordinateSpaceWidth(getWidth(route));
		can.setCoordinateSpaceHeight(getHeight(route));
		Context2d con = can.getContext2d();
		
		Image img = loader.getImageByUrl("/mapicon/cycleway.png");
		
		
			con.setFillStyle(route.getColor());
		
		
		int imageWidth = getWidth(route);
		int imageHeight = getHeight(route);

		double x = 0.11		* imageWidth;
		double y = 0.1081	* imageHeight;
		double w = 0.77		* imageWidth;
		double h = 0.66		* imageHeight;
		con.fillRect(x, y, w, h);
		
		con.setFillStyle("#000000");
			
		final ImageElement face = ImageElement.as(img.getElement());
		con.drawImage(face, 0, 0, imageWidth, imageHeight);
	}

	@Override
	public int getWidth(Route route) {
		return 50;
	}

	@Override
	public int getHeight(Route route) {
		return 58;
	}
	
	
	
}
