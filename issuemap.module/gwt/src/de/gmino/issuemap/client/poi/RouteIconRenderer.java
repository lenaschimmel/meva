package de.gmino.issuemap.client.poi;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.Route;

public class RouteIconRenderer extends GwtIconRenderer<Route> {
	
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
		
		Image img = loader.getImageByUrl("/mapicon/workshop.png");
		
		con.setFillStyle("#ef4122");
		
		
		int imageWidth = getWidth(route);
		int imageHeight = getHeight(route);

		double x = 0.11		* imageWidth;
		double y = 0.1081	* imageHeight;
		double w = 0.77		* imageWidth;
		double h = 0.66		* imageHeight;
		con.fillRect(x, y, w, h);
		con.beginPath();
		con.moveTo(0.5 * imageWidth, 0.92 * imageHeight);
		con.lineTo(0.7 * imageWidth, 0.75 * imageHeight);
		con.lineTo(0.3 * imageWidth, 0.75 * imageHeight);
		con.lineTo(0.5 * imageWidth, 0.92 * imageHeight);
		con.fill();
		con.setFillStyle("#000000");
			
		final ImageElement face = ImageElement.as(img.getElement());
		con.drawImage(face, 0, 0, imageWidth, imageHeight);
	}

	@Override
	public int getWidth(Route issue) {
		return 41;
	}

	@Override
	public int getHeight(Route issue) {
		return 41;
	}
	
	
}
