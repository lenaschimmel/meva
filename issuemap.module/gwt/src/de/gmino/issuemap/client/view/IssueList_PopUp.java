package de.gmino.issuemap.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.poi.IssueIconRenderer;

public class IssueList_PopUp extends Composite {

	private static IssueList_PopUpUiBinder uiBinder = GWT
			.create(IssueList_PopUpUiBinder.class);
	
	interface IssueList_PopUpUiBinder extends UiBinder<Widget, IssueList_PopUp> {
	}
	
	private List_Button listButton;

	public IssueList_PopUp(Map mapObject, ArrayList<Issue> data, IssueIconRenderer issueRenderer, OpenLayersSmartLayer layer) {
		initWidget(uiBinder.createAndBindUi(this));
		listButton = new List_Button(this);
		RootPanel.get("feedback").add(listButton);
		listButton.setVisible(false);
		title.getElement().getStyle().setColor(mapObject.getSecondary_color());
	    
	    for (Issue i : data)
	    	IssueItemsPanel.add(new IssueList_Item(i, issueRenderer, layer));
	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	VerticalPanel IssueItemsPanel;
	@UiField
	FlowPanel flowPanel;
	
	@UiHandler("close")
	void onClick(ClickEvent e) {
		RootPanel.get("list").getElement().getStyle().setDisplay(Display.NONE); // setZIndex(-1000);
		listButton.setVisible(true);
		IssuemapGwt.getInstance().setListVisible(false);
	}
	
	 public void setParentVisible(){
		RootPanel.get("list").getElement().getStyle().setDisplay(Display.BLOCK);
	}
}