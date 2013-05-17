package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.Poi;
import de.gmino.meva.shared.EntityTypeName;

public interface SmartLayer<CanvasType, PopupType> {
	void addPoi(Poi o);
	void removePoi(Poi o);
	void addMarkerIconRenderer(EntityTypeName type, IconRenderer<? extends Poi, CanvasType> renderer);
	void addMarkerPopupCreator(EntityTypeName type, PopupCreator<? extends Poi, PopupType> creator);
	void updatePoi(Poi o);
}
