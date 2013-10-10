package de.gmino.issuemap.client.view.list;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.poi.IssueIconRenderer;
import de.gmino.issuemap.client.view.list.ListView.ListViewItem;
import de.gmino.issuemap.client.view.popup.Info_PopUp;

public class ResolvedList_Item extends Composite implements ListViewItem<Poi> {

	private static IssueList_ItemUiBinder uiBinder = GWT
			.create(IssueList_ItemUiBinder.class);
	
	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");

	interface IssueList_ItemUiBinder extends UiBinder<Widget, ResolvedList_Item> {
	}

	private Poi issue;
	private OpenLayersSmartLayer layer;
	private DecoratedPopupPanel decorated_panel;
	private IssueIconRenderer renderer;
	
	public ResolvedList_Item(Poi issue, IssueIconRenderer renderer, OpenLayersSmartLayer layer, DecoratedPopupPanel decorated_panel) {
		initWidget(uiBinder.createAndBindUi(this));
		labelTitle.getElement().getStyle().setColor(issue.getMap_instance().getPopupTextColor());
		create_date.getElement().getStyle().setColor(issue.getMap_instance().getPopupTextColor());
		create_date.getElement().getStyle().setColor(issue.getMap_instance().getPopupTextColor());
		resolved_date.getElement().getStyle().setColor(issue.getMap_instance().getPopupTextColor());
		type.getElement().getStyle().setColor(issue.getMap_instance().getPopupTextColor());
		this.renderer = renderer;
		this.layer = layer;
		setDataItem(issue);
		this.decorated_panel=decorated_panel;
	}
	
	@UiField
	Label labelTitle;
	@UiField
	Label create_date;
	@UiField
	Label resolved_date;
	@UiField
	Label type;
	@UiField
	Image imageMarkerIcon;
	@UiField 
	FocusPanel focusPanel;


	@Override
	public void setDataItem(Poi issue) {
		this.issue = issue;
		labelTitle.setText(issue.getTitle());
		type.setText(issue.getMarkertype().getMarkerName());
		if (issue.getCreationTimestamp().getMillisSinceEpoch() == 1372938278000L) {
			create_date.setText("Eingetragen: Zeitpunkt unbekannt");
			create_date.setTitle("damals wurde der Zeitpunkt noch nicht getrackt");
		} else {
			create_date.setText("Eingetragen " + issue.getCreationTimestamp().relativeToNow().toReadableString(true, 1));
			create_date.setTitle(dtf.format(issue.getCreationTimestamp().toDate()));
		}
		if (issue.getResolvedTimestamp().getMillisSinceEpoch() == 1379603365674L) {
			resolved_date.setText("Erledigt: Zeitpunkt unbekannt");
			resolved_date.setTitle("damals wurde der Zeitpunkt noch nicht getrackt");
		} else {
			resolved_date.setText("Erledigt " + issue.getResolvedTimestamp().relativeToNow().toReadableString(true, 1));
			resolved_date.setTitle(dtf.format(issue.getCreationTimestamp().toDate()));
		}
		String iconUrl = renderer.getIconUrl(issue);
		imageMarkerIcon.setUrl(iconUrl);
	}
	
}
