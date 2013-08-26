package de.gmino.geobase.shared.map;

import de.gmino.geobase.shared.domain.PoiInterface;

public interface PopupCreator<PoiType extends PoiInterface, PopupType> {
	PopupType createPopup(PoiType poi);
}
