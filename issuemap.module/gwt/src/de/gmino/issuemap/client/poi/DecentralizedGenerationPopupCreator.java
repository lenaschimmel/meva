package de.gmino.issuemap.client.poi;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtPopupCreator;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.Hover_PopUp;
import de.gmino.issuemap.client.view.ShowBicycleShop_PopUp;
import de.gmino.issuemap.client.view.ShowDecentralizedGeneration_PopUp;

public class DecentralizedGenerationPopupCreator implements GwtPopupCreator<DecentralizedGeneration> {

	private Map map;
	OpenLayersSmartLayer smartLayer;

	public DecentralizedGenerationPopupCreator(Map map, OpenLayersSmartLayer smartLayer) {
		super();
		this.map = map;
		this.smartLayer = smartLayer;
	}

	@Override
	public Widget createPopup(DecentralizedGeneration poi) {
		Marker_Wrapper wrapper = new Marker_Wrapper(poi, map);
		ShowDecentralizedGeneration_PopUp showBicycleShop_PopUp = new ShowDecentralizedGeneration_PopUp(map, poi, wrapper, smartLayer);
		wrapper.add(showBicycleShop_PopUp);
		return wrapper;
	}

	@Override
	public Widget createTooltip(DecentralizedGeneration poi) {
		int height = 44;
		int width = 38;
		Hover_PopUp hoverPopUp = new Hover_PopUp((int) (0.66*height));
		if (poi.getTitle().equals("")) {
			hoverPopUp.setText("Umspannwerk", poi.getDescription());
		} else {
			hoverPopUp.setText(poi.getTitle(), poi.getDescription());
		}
		
		hoverPopUp.getElement().getStyle().setPosition(Position.ABSOLUTE);
		hoverPopUp.getElement().getStyle().setTop(-0.85   * height, Unit.PX);
		hoverPopUp.getElement().getStyle().setLeft(0.3825 * width, Unit.PX);
		return hoverPopUp;
	}

}
