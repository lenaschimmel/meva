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
		can.setCoordinateSpaceWidth(WIDTH);
		can.setCoordinateSpaceHeight(HEIGHT);
		Context2d con = can.getContext2d();
		
		//Image img = loader.getImageByUrl("/mapicon/route.png");

		con.setFont("bold 14px sans-serif");
		TextMetrics measureText = con.measureText(route.getTitle());
		double textWidth = measureText.getWidth();
		con.setFillStyle(route.getColor());
		con.fillRect(0, 0, textWidth + 10, HEIGHT);
		con.setFillStyle("#000000");
		con.fillText(route.getTitle(), 5, HEIGHT - 5, WIDTH - 10);
	}

	@Override
	public int getWidth(Route issue) {
		return WIDTH;
	}

	@Override
	public int getHeight(Route issue) {
		return HEIGHT;
	}
	
	
}
