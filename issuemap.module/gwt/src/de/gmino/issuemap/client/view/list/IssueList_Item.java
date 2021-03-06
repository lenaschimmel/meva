package de.gmino.issuemap.client.view.list;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.poi.IssueIconRenderer;
import de.gmino.issuemap.client.view.list.ListView.ListViewItem;

public class IssueList_Item extends Composite implements ListViewItem<Poi> {

	private static IssueList_ItemUiBinder uiBinder = GWT
			.create(IssueList_ItemUiBinder.class);
	
	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");

	interface IssueList_ItemUiBinder extends UiBinder<Widget, IssueList_Item> {
	}

	private Poi issue;
	private OpenLayersSmartLayer layer;

	private IssueIconRenderer renderer;
	
	public IssueList_Item(Poi issue, IssueIconRenderer renderer, OpenLayersSmartLayer layer) {
		initWidget(uiBinder.createAndBindUi(this));
		labelTitle.getElement().getStyle().setColor(issue.getMap_instance().getSecondary_color());
		date.getElement().getStyle().setColor(issue.getMap_instance().getSecondary_color());
		type.getElement().getStyle().setColor(issue.getMap_instance().getSecondary_color());
		this.renderer = renderer;
		this.layer = layer;
		setDataItem(issue);
	}
	
	@UiField
	Label labelTitle;
	@UiField
	Label date;
	@UiField
	Label type;
	@UiField
	Image imageMarkerIcon;
	@UiField 
	FocusPanel focusPanel;
	@UiField
	Label rating;
	
	@UiHandler("focusPanel")
	public void onClick(ClickEvent e) {
	
		layer.clickedPoi(issue.getId());
	}
	
	@UiHandler("focusPanel")
	public void onHover(ClickEvent e) {
	
		layer.clickedPoi(issue.getId());
	}

	@Override
	public void setDataItem(Poi issue) {
		this.issue = issue;
		labelTitle.setText(issue.getTitle());
		type.setText(issue.getMarkertype().getMarkerName());
		if(issue.getRating()>0) rating.setText("+"+issue.getRating());
		else rating.setText(""+issue.getRating());
		rating.getElement().getStyle().setColor(issue.getMap_instance().getSecondary_color());
		date.setText(", " + issue.getCreationTimestamp().relativeToNow().toReadableString(true,1));
		date.setTitle(dtf.format(issue.getCreationTimestamp().toDate()));
		
		String iconUrl = renderer.getIconUrl(issue);
		imageMarkerIcon.setUrl(iconUrl);
	}
	
}
