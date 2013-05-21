package de.gmino.issuemap.client;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;

import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.Hover_PopUp;
import de.gmino.issuemap.client.view.MarkerIcon;
import de.gmino.issuemap.client.view.ShowIssue_PopUp;

public class Marker_Wrapper extends AbsolutePanel { // implements MouseOverHandler, MouseOutHandler, ClickHandler { // Composite implements HasText {
	ShowIssue_PopUp detail;
	Issue mIssue;
	Map mMapObject;

	public Marker_Wrapper(Issue issue, Map mapObject) {
		this.setSize("20px", "20px");
		this.getElement().getStyle().setOverflow(Overflow.VISIBLE);
		this.mIssue=issue ;
		this.mMapObject=mapObject;
	}

}
