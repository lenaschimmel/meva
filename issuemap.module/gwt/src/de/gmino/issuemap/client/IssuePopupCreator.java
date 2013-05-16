package de.gmino.issuemap.client;

import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtPopupCreator;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.Hover_PopUp;
import de.gmino.issuemap.client.view.ShowIssue_PopUp;

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
		return new ShowIssue_PopUp(map, poi, wrapper, smartLayer);
	}

	@Override
	public Widget createTooltip(Poi poi) {
		Hover_PopUp hoverPopUp = new Hover_PopUp();
		hoverPopUp.setText(poi.getTitle(), poi.getDescription());
		return hoverPopUp;
	}

}
