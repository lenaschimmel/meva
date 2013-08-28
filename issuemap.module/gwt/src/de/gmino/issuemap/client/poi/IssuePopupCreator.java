package de.gmino.issuemap.client.poi;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtPopupCreator;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.view.Hover_PopUp;
import de.gmino.issuemap.client.view.ShowEvent_PopUp;
import de.gmino.issuemap.client.view.ShowPoi_PopUp;
import de.gmino.issuemap.client.view.Show_PopUp;

public class IssuePopupCreator implements GwtPopupCreator<Poi> {

	private Map map;
	OpenLayersSmartLayer smartLayer;

	public IssuePopupCreator(Map map, OpenLayersSmartLayer smartLayer) {
		super();
		this.map = map;
		this.smartLayer = smartLayer;
	}

	@Override
	public Widget createPopup(Poi poi) {
		Show_PopUp popup = new Show_PopUp(map, smartLayer);
		popup.setPoi(poi);
		return popup;
	}

	@Override
	public Widget createTooltip(Poi poi) {
		Hover_PopUp hoverPopUp = new Hover_PopUp((int) (0.66*poi.getMarkertype().getImageHeight()));
		hoverPopUp.setText(poi.getTitle());
		hoverPopUp.getElement().getStyle().setPosition(Position.ABSOLUTE);
		hoverPopUp.getElement().getStyle().setTop(-0.85*poi.getMarkertype().getImageHeight(), Unit.PX);
		hoverPopUp.getElement().getStyle().setLeft(0.3825*poi.getMarkertype().getImageWidth(), Unit.PX);
		return hoverPopUp;
	}
}
