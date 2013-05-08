package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.Poi;

public interface PopupCreator<PoiType extends Poi, PopupType> {
	PopupType createPopup(PoiType poi);
}
