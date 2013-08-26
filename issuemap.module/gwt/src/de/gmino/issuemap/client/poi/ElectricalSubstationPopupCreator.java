package de.gmino.issuemap.client.poi;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtPopupCreator;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.domain.ElectricalSubstation;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.Hover_PopUp;
import de.gmino.issuemap.client.view.ShowElectricalSubstation_PopUp;

public class ElectricalSubstationPopupCreator implements GwtPopupCreator<ElectricalSubstation> {

	private Map map;
	OpenLayersSmartLayer smartLayer;

	public ElectricalSubstationPopupCreator(Map map, OpenLayersSmartLayer smartLayer) {
		super();
		this.map = map;
		this.smartLayer = smartLayer;
	}

	@Override
	public Widget createPopup(ElectricalSubstation poi) {
		Marker_Wrapper wrapper = new Marker_Wrapper(poi, map);
		ShowElectricalSubstation_PopUp substation_Popup = new ShowElectricalSubstation_PopUp(map, poi, wrapper, smartLayer);
		wrapper.add(substation_Popup);
		return wrapper;
	}

	@Override
	public Widget createTooltip(ElectricalSubstation poi) {
		int height = 44;
		int width = 38;
		Hover_PopUp hoverPopUp = new Hover_PopUp((int) (0.66*height));
		if (poi.getTitle().equals("")) {
			hoverPopUp.setText("Umspannwerk");
		} else {
			hoverPopUp.setText(poi.getTitle());
		}
		
		hoverPopUp.getElement().getStyle().setPosition(Position.ABSOLUTE);
		hoverPopUp.getElement().getStyle().setTop(-0.85   * height, Unit.PX);
		hoverPopUp.getElement().getStyle().setLeft(0.3825 * width, Unit.PX);
		return hoverPopUp;
	}


}
