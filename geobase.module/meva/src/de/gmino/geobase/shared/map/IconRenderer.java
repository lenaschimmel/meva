package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.Poi;

public interface IconRenderer<PoiType extends Poi, CanvasType> {
	void getIconHash(Hasher hash, PoiType object);
	void renderIcon(CanvasType con, PoiType object);
}
