package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.PoiInterface;
import de.gmino.meva.shared.TypeName;

public interface SmartLayer<CanvasType, PopupType> {
	void addPoi(PoiInterface o);
	void removePoi(PoiInterface o);
	void addMarkerIconRenderer(TypeName type, IconRenderer<? extends PoiInterface, CanvasType> renderer);
	void addMarkerPopupCreator(TypeName type, PopupCreator<? extends PoiInterface, PopupType> creator);
	void updatePoi(PoiInterface o);
}
