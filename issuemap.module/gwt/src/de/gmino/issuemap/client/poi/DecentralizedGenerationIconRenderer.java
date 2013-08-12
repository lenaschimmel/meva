package de.gmino.issuemap.client.poi;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.shared.map.Hasher;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;

public class DecentralizedGenerationIconRenderer extends GwtIconRenderer<DecentralizedGeneration> {
	
	private static final String COLOR_REPAIR = "#3852AE";
	private static final String COLOR_NO_REPAIR = "#3852AE";
	ImageUrlLoader loader = ImageUrlLoader.getInstance();
	
	public void getIconHash(de.gmino.geobase.shared.map.Hasher hash, DecentralizedGeneration shop) 
	{
		hash.hashInt(4542);
	}
	
	@Override
	public void renderIcon(Canvas can, DecentralizedGeneration shop) {
		can.setCoordinateSpaceWidth(getWidth(shop));
		can.setCoordinateSpaceHeight(getHeight(shop));
		Context2d con = can.getContext2d();
		
		Image img = loader.getImageByUrl("/mapicon/generation.png");
		
		con.setFillStyle(COLOR_NO_REPAIR);
		
		
		int imageWidth = getWidth(shop);
		int imageHeight = getHeight(shop);

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
	public int getWidth(DecentralizedGeneration issue) {
		return 38;
	}

	@Override
	public int getHeight(DecentralizedGeneration issue) {
		return 44;
	}

	@Override
	public void renderSmallIcon(Canvas canvas, DecentralizedGeneration shop) {
		drawDefaultCircle(canvas, COLOR_REPAIR);
	}

	@Override
	public void getSmallIconHash(Hasher hash, DecentralizedGeneration shop) {
		hash.hashInt(12345); // to differentiate from full bicycle icons
	}
	
	
}
