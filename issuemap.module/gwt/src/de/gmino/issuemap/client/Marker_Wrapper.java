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

public class Marker_Wrapper extends AbsolutePanel implements MouseOverHandler, MouseOutHandler, ClickHandler { // Composite implements HasText {

	Hover_PopUp hover = new Hover_PopUp();
	Detail_PopUp detail = new Detail_PopUp();
	MarkerIcon mIcon;
	Issue mIssue;
	private int zIndex = 1500;

	public Marker_Wrapper(Issue issue) {
		this.setSize("20px", "20px");
		this.getElement().getStyle().setOverflow(Overflow.VISIBLE);
		this.mIssue=issue ;
		mIcon = new MarkerIcon();
		mIcon.getElement().getStyle().setZIndex(getAbsoluteTop());
		add(mIcon);
		mIcon.addDomHandler(this,  MouseOverEvent.getType());
		mIcon.addDomHandler(this,  MouseOutEvent.getType());
		mIcon.addDomHandler(this,  ClickEvent.getType());

	}

	public void onMouseOver(MouseOverEvent e) {
		hover.setText(mIssue.getTitle(), mIssue.getDescription());
		add(hover);
		hover.addDomHandler(this,  ClickEvent.getType());
		hover.getElement().getStyle().setZIndex(zIndex ++);
	}
	
	public void onMouseOut(MouseOutEvent e) {
		remove(hover);
	}
	
	public void onClick(ClickEvent e) {
		detail.setText(mIssue.getTitle(), mIssue.getDescription());
		add(detail);
		IssuemapGwt.setMapPosition(mIssue.getLocation());
		
	}
}
