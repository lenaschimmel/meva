package de.gmino.issuemap.client.view;

import java.util.ArrayList;
import java.util.Iterator;

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
	private ArrayList<IssueList_Item> items;
	private OpenLayersSmartLayer layer;
	private IssueIconRenderer renderer;

	public IssueList_PopUp(Map mapObject, IssueIconRenderer issueRenderer, OpenLayersSmartLayer layer) {
		initWidget(uiBinder.createAndBindUi(this));
		this.renderer = issueRenderer;
		this.layer = layer;
		listButton = new List_Button(this);
		RootPanel.get("feedback").add(listButton);
		RootPanel.get("list").getElement().getStyle().setDisplay(Display.NONE); // setZIndex(-1000);
		IssuemapGwt.getInstance().setListVisible(false);
		title.getElement().getStyle().setColor(mapObject.getSecondary_color());
		items = new ArrayList<IssueList_Item>();
	}
	
	public void updateData(ArrayList<Issue> data)
	{
		ArrayList<IssueList_Item> newItems = new ArrayList<IssueList_Item>();
		Iterator<IssueList_Item> it = items.iterator();
		for(Issue i : data)
		{
			if(it.hasNext())
			{
				IssueList_Item item = it.next();
				item.setIssue(i);
				newItems.add(item);	
			}
			else
			{
				final IssueList_Item item = new IssueList_Item(i, renderer, layer);
				newItems.add(item);		    	
				IssueItemsPanel.add(item);
			}
		}
		
		while(it.hasNext())
		{
			IssueItemsPanel.remove(it.next());
			it.remove();
		}
		
		items = newItems;
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
