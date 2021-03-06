package de.gmino.geobase.client.map;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

import de.gmino.geobase.shared.domain.PoiInterface;
import de.gmino.geobase.shared.map.Hasher;
import de.gmino.geobase.shared.map.IconRenderer;

public abstract class GwtIconRenderer<PoiType extends PoiInterface> implements IconRenderer<PoiType, Canvas> {
	//void getIconHash(Hasher hash, PoiType poi);
	//void renderIcon(Context con, PoiType poi);
	
	Map<Integer, String> imageCache = new TreeMap<Integer, String>();
	Canvas canvas = Canvas.createIfSupported();
	Hasher hasher = new Hasher();
	
	public String getIconUrl(PoiType o)
	{
		hasher.reset();
		getIconHash(hasher, o);
		String url = imageCache.get(hasher.getValue());
		if(url == null)
		{
			//System.out.println("New rendering: " + o.getTitle());
			renderIcon(canvas, o);
			url = canvas.toDataUrl();
			imageCache.put(hasher.getValue(), url);
		}
		//else
			//System.out.println("Found in cache: " + o.getTitle());

		return url;
	}

	public abstract int getWidth(PoiType poi);
	
	public abstract int getHeight(PoiType poi);

	public String getSmallIconUrl(PoiType o) {
		hasher.reset();
		getSmallIconHash(hasher, o);
		String url = imageCache.get(hasher.getValue());
		if(url == null)
		{
		//	System.out.println("New rendering: " + o.getTitle());
			renderSmallIcon(canvas, o);
			url = canvas.toDataUrl();
			imageCache.put(hasher.getValue(), url);
		}
		//else
		//	System.out.println("Found in cache: " + o.getTitle());

		return url;
	}
	

	protected void drawDefaultCircle(Canvas can, String color) {
		drawDefaultCircle(can, color, 8);
	}
	
	protected void drawDefaultCircle(Canvas can, String color, int size)
	{
		can.setCoordinateSpaceWidth(32);
		can.setCoordinateSpaceHeight(32);
		Context2d con = can.getContext2d();
		
		con.setFillStyle(color);
		con.beginPath();
		con.arc(16,16, size/2, 0, Math.PI * 2.0, true);
		con.closePath();
		con.fill();
		con.setStrokeStyle("#000000");
		con.stroke();
	}


	public abstract void renderSmallIcon(Canvas canvas, PoiType o);

	public abstract void getSmallIconHash(Hasher hasher, PoiType o);
	
}
