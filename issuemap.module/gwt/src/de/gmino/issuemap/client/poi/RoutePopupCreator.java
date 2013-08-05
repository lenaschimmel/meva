package de.gmino.issuemap.client.poi;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtPopupCreator;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Route;
import de.gmino.issuemap.client.view.Hover_PopUp;
import de.gmino.issuemap.client.view.ShowRoute_PopUp;

public class RoutePopupCreator implements GwtPopupCreator<Route> {

	private Map map;
	OpenLayersSmartLayer smartLayer;

	public RoutePopupCreator(Map map, OpenLayersSmartLayer smartLayer) {
		super();
		this.map = map;
		this.smartLayer = smartLayer;
	}

	@Override
	public Widget createPopup(Route poi) {
		Marker_Wrapper wrapper = new Marker_Wrapper(poi, map);
		ShowRoute_PopUp popUp = new ShowRoute_PopUp(map, poi, wrapper, smartLayer);
		wrapper.add(popUp);
		return wrapper;
	}

	@Override
	public Widget createTooltip(Route poi) {
		int height = 40;
		Hover_PopUp hoverPopUp = new Hover_PopUp(height);
		hoverPopUp.setText(poi.getTitle(), poi.getDescription());
		hoverPopUp.getElement().getStyle().setPosition(Position.ABSOLUTE);
		hoverPopUp.getElement().getStyle().setTop(-0.85   * 58, Unit.PX);
		hoverPopUp.getElement().getStyle().setLeft(0.3825 * 50, Unit.PX);
		return hoverPopUp;
	}

}
