package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.PoiInterface;

public interface IconRenderer<PoiType extends PoiInterface, CanvasType> {
	void getIconHash(Hasher hash, PoiType object);
	void renderIcon(CanvasType con, PoiType object);
}
