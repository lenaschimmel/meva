package de.gmino.issuemap.client.poi;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.AbsolutePanel;

import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.ShowIssue_PopUp;

public class Marker_Wrapper extends AbsolutePanel { // implements MouseOverHandler, MouseOutHandler, ClickHandler { // Composite implements HasText {
	ShowIssue_PopUp detail;
	Poi mIssue;
	Map mMapObject;

	public Marker_Wrapper(Poi issue, Map mapObject) {
		this.setSize("20px", "20px");
		this.getElement().getStyle().setOverflow(Overflow.VISIBLE);
		this.mIssue=issue ;
		this.mMapObject=mapObject;
	}

}
