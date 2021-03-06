package de.gmino.issuemap.client.view.popup;

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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.poi.IssueIconRenderer;
import de.gmino.issuemap.client.view.list.IssueList_Item;
import de.gmino.issuemap.client.view.list.ListView;
import de.gmino.issuemap.client.view.list.List_Button;
import de.gmino.issuemap.client.view.list.ListView.ListViewItem;

public class List_PopUp extends Composite {

	private static IssueList_PopUpUiBinder uiBinder = GWT
			.create(IssueList_PopUpUiBinder.class);
	
	interface IssueList_PopUpUiBinder extends UiBinder<Widget, List_PopUp> {
	}
	
	private List_Button listButton;
	private OpenLayersSmartLayer layer;
	private IssueIconRenderer renderer;
	private ListView<Poi> list;

	public List_PopUp(Map mapObject, IssueIconRenderer issueRenderer, OpenLayersSmartLayer smartLayer) {
		initWidget(uiBinder.createAndBindUi(this));
		this.renderer = issueRenderer;
		this.layer = smartLayer;
		flowPanel.getElement().getStyle().setBackgroundColor(mapObject.getBackground_color());
		listButton = new List_Button(this);
		RootPanel.get("feedback").add(listButton);
		RootPanel.get("list").getElement().getStyle().setDisplay(Display.NONE); // setZIndex(-1000);
		IssuemapGwt.getInstance().updateListMargin(false);
		list = new  ListView<Poi>() {
			
			@Override
			public ListViewItem<Poi> createListItem(Poi item) {
				return new IssueList_Item(item, renderer,layer);
			}
		};
		IssueItemsPanel.add(list);
	}
	
	public void updateData(ArrayList<Poi> data)
	{
		list.updateData(data);
	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	SimplePanel IssueItemsPanel;
	@UiField
	FlowPanel flowPanel;
	
	@UiHandler("close")
	void onClick(ClickEvent e) {
		RootPanel.get("list").getElement().getStyle().setDisplay(Display.NONE); // setZIndex(-1000);
		listButton.setVisible(true);
		IssuemapGwt.getInstance().updateListMargin(false);
	}
	
	 public void setParentVisible(){
		RootPanel.get("list").getElement().getStyle().setDisplay(Display.BLOCK);
	}
}
