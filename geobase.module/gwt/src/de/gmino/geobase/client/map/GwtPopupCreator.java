package de.gmino.geobase.client.map;

import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.PoiInterface;
import de.gmino.geobase.shared.map.PopupCreator;

public interface GwtPopupCreator<PoiType extends PoiInterface> extends PopupCreator<PoiType, Widget>{

	Widget createTooltip(PoiType poi);

}
