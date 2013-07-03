package de.gmino.issuemap.client.poi;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtPopupCreator;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.Hover_PopUp;
import de.gmino.issuemap.client.view.ShowEvent_PopUp;
import de.gmino.issuemap.client.view.ShowIssue_PopUp;
import de.gmino.issuemap.client.view.ShowPoi_PopUp;

public class IssuePopupCreator implements GwtPopupCreator<Issue> {

	private Map map;
	OpenLayersSmartLayer smartLayer;

	public IssuePopupCreator(Map map, OpenLayersSmartLayer smartLayer) {
		super();
		this.map = map;
		this.smartLayer = smartLayer;
	}

	@Override
	public Widget createPopup(Issue poi) {
		Marker_Wrapper wrapper = new Marker_Wrapper(poi, map);
		String markerName = poi.getMarkertype().getMarkerName();
		long markerId = poi.getMarkertypeId();
		Composite popup;
		if (markerName.equals("Disco")
				|| markerName.equals("Theater")
				|| markerName.equals("Konzert")
				|| markerName.equals("Sport")
				|| markerName.equals("Party")) {
			popup = new ShowEvent_PopUp(map, poi, wrapper, smartLayer);
			
			//wrapper.setWidth(showEvent_PopUp.getOffsetWidth()+"px");
			//wrapper.setHeight(showEvent_PopUp.getOffsetHeight()+"px");
			//Window.alert("Size: " + showEvent_PopUp.getOffsetWidth() + " x " + showEvent_PopUp.getOffsetHeight());
		} else if(markerId >= 80 && markerId <= 94){
			popup = new ShowIssue_PopUp(map, poi, wrapper, smartLayer);
		} else {
			popup = new ShowPoi_PopUp(map, poi, wrapper, smartLayer);
		}
		wrapper.add(popup);
		return wrapper;
	}

	@Override
	public Widget createTooltip(Issue poi) {
		Hover_PopUp hoverPopUp = new Hover_PopUp((int) (0.66*poi.getMarkertype().getImageHeight()));
		hoverPopUp.setText(poi.getTitle(), poi.getDescription());
		hoverPopUp.getElement().getStyle().setPosition(Position.ABSOLUTE);
		hoverPopUp.getElement().getStyle().setTop(-0.85*poi.getMarkertype().getImageHeight(), Unit.PX);
		hoverPopUp.getElement().getStyle().setLeft(0.3825*poi.getMarkertype().getImageWidth(), Unit.PX);
		return hoverPopUp;
	}

}
