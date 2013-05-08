package de.gmino.geobase.client.map;

import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.map.PopupCreator;

public interface GwtPopupCreator<PoiType extends Poi> extends PopupCreator<PoiType, Widget>{

}
