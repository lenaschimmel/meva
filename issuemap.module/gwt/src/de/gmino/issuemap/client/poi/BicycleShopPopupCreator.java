package de.gmino.issuemap.client.poi;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtPopupCreator;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.popup.Hover_PopUp;
import de.gmino.issuemap.client.view.popup.ShowBicycleShop_PopUp;

public class BicycleShopPopupCreator implements GwtPopupCreator<BicycleShop> {

	private Map map;
	OpenLayersSmartLayer smartLayer;

	public BicycleShopPopupCreator(Map map, OpenLayersSmartLayer smartLayer) {
		super();
		this.map = map;
		this.smartLayer = smartLayer;
	}

	@Override
	public Widget createPopup(BicycleShop poi) {
		Marker_Wrapper wrapper = new Marker_Wrapper(poi, map);
		ShowBicycleShop_PopUp showBicycleShop_PopUp = new ShowBicycleShop_PopUp(map, poi, wrapper, smartLayer);
		wrapper.add(showBicycleShop_PopUp);
		return wrapper;
	}

	@Override
	public Widget createTooltip(BicycleShop poi) {
		int height = 44;
		int width = 38;
		Hover_PopUp hoverPopUp = new Hover_PopUp((int) (0.66*height));
		if (poi.getTitle().equals("")) {
			hoverPopUp.setText("Fahradwerkstatt (Name unbekannt)");
		} else {
			hoverPopUp.setText(poi.getTitle());
		}
		
		hoverPopUp.getElement().getStyle().setPosition(Position.ABSOLUTE);
		hoverPopUp.getElement().getStyle().setTop(-0.85   * height, Unit.PX);
		hoverPopUp.getElement().getStyle().setLeft(0.3825 * width, Unit.PX);
		return hoverPopUp;
	}

}
