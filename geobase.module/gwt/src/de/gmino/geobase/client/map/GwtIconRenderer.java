package de.gmino.geobase.client.map;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.canvas.client.Canvas;

import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.map.Hasher;
import de.gmino.geobase.shared.map.IconRenderer;

public abstract class GwtIconRenderer<PoiType extends Poi> implements IconRenderer<PoiType, Canvas> {
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
			renderIcon(canvas, o);
			url = canvas.toDataUrl();
			imageCache.put(hasher.getValue(), url);
		}
		return url;
	}

	public abstract int getWidth(PoiType poi);
	
	public abstract int getHeight(PoiType poi);
	
}
