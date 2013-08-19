package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.Poi;
import de.gmino.meva.shared.TypeName;

public interface SmartLayer<CanvasType, PopupType> {
	void addPoi(Poi o);
	void removePoi(Poi o);
	void addMarkerIconRenderer(TypeName type, IconRenderer<? extends Poi, CanvasType> renderer);
	void addMarkerPopupCreator(TypeName type, PopupCreator<? extends Poi, PopupType> creator);
	void updatePoi(Poi o);
}
