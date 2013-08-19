package de.gmino.issuemap.client.poi;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.map.Hasher;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.ElectricalSubstation;

public class DecentralizedGenerationIconRenderer extends GwtIconRenderer<Poi> {
	
	private static final String COLOR_SUN = "#DDDD33";
	private static final String COLOR_WIND = "#43AE43";
	private static final String COLOR_TRAFO = "#3852AE";
	ImageUrlLoader loader = ImageUrlLoader.getInstance();
	
	public void getIconHash(de.gmino.geobase.shared.map.Hasher hash, Poi poi) 
	{
		if (poi instanceof DecentralizedGeneration) {
			DecentralizedGeneration gen = (DecentralizedGeneration) poi;
			hash.hashObject(gen.getUnitType());
			hash.hashInt(gen.getPowerLevel());
			if(gen.station != null)
				hash.hashObject(gen.station.color);
		}
		else
		{
			ElectricalSubstation sub = (ElectricalSubstation) poi;
			hash.hashFloat(sub.getLow_voltage());
			hash.hashFloat(sub.getHigh_voltage());
			hash.hashObject(sub.color);
		}
	}
	
	@Override
	public void renderIcon(Canvas can, Poi poi) {
		can.setCoordinateSpaceWidth(getWidth(poi));
		can.setCoordinateSpaceHeight(getHeight(poi));
		Context2d con = can.getContext2d();
		
		Image img;
		if (poi instanceof DecentralizedGeneration)
		{
			DecentralizedGeneration gen = (DecentralizedGeneration) poi;
			if(gen.getUnitType().equals("pv"))
			{
				img = loader.getImageByUrl("/mapicon/sun.png");
				con.setFillStyle(COLOR_SUN);
			}
			else
			{
				img = loader.getImageByUrl("/mapicon/windtourbine.png");
				con.setFillStyle(COLOR_WIND);
			}
			if(gen.station != null)
				con.setFillStyle(gen.station.color);
		}
		else
		{
			ElectricalSubstation sta = (ElectricalSubstation) poi;
			img = loader.getImageByUrl("/mapicon/trafo.png");
			//con.setFillStyle(COLOR_TRAFO);
			con.setFillStyle(sta.color);
		}
		
		int imageWidth = getWidth(poi);
		int imageHeight = getHeight(poi);

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
	public int getWidth(Poi poi) {
		if (poi instanceof DecentralizedGeneration)
		{
			DecentralizedGeneration gen = (DecentralizedGeneration) poi;
			return 10 * gen.getPowerLevel() + 15;
		}
		else
			return 48;
	}

	@Override
	public int getHeight(Poi poi) {
		if (poi instanceof DecentralizedGeneration)
		{
			DecentralizedGeneration gen = (DecentralizedGeneration) poi;
			return 11 * gen.getPowerLevel() + 17;
		}
		else
			return 56;
	}

	@Override
	public void renderSmallIcon(Canvas canvas, Poi poi) {
		if (poi instanceof DecentralizedGeneration)
		{
			DecentralizedGeneration gen = (DecentralizedGeneration) poi;
			String color;
			if(gen.getUnitType().equals("pv"))
				color = COLOR_SUN;
			else
				color = COLOR_WIND;
			if(gen.station != null)
				color = gen.station.color;
			drawDefaultCircle(canvas, color, 4 *  gen.getPowerLevel());
		}
		else
		{
			//ElectricalSubstation sub = (ElectricalSubstation) poi;
			//drawDefaultCircle(canvas, sub.color);
			renderIcon(canvas, poi);
		}
	}

	@Override
	public void getSmallIconHash(Hasher hash, Poi poi) {
		if (poi instanceof DecentralizedGeneration)
		{
			DecentralizedGeneration gen = (DecentralizedGeneration) poi; 
			if(gen.getUnitType().equals("pv"))
				hash.hashInt(123);
			else
				hash.hashInt(346);
			hash.hashInt(gen.getPowerLevel());
			if(gen.station != null)
				hash.hashObject(gen.station.color);
		}
		else
		{
			ElectricalSubstation sub = (ElectricalSubstation)poi;
			hash.hashInt(644);
			hash.hashObject(sub.color);
		}
		
	}
}
