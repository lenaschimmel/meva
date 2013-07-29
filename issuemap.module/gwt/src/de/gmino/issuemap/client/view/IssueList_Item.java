package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.poi.IssueIconRenderer;

public class IssueList_Item extends Composite {

	private static IssueList_ItemUiBinder uiBinder = GWT
			.create(IssueList_ItemUiBinder.class);
	
	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");

	interface IssueList_ItemUiBinder extends UiBinder<Widget, IssueList_Item> {
	}
	
	public IssueList_Item(Issue issue, IssueIconRenderer renderer) {
		initWidget(uiBinder.createAndBindUi(this));
		
		labelTitle.setText(issue.getTitle());
		type.setText(issue.getMarkertype().getMarkerName());
		date.setText(", vom " + dtf.format(issue.getCreationTimestamp().toDate()));

		
		String iconUrl = renderer.getIconUrl(issue);
		imageMarkerIcon.setUrl(iconUrl);
		
		
	}

	@UiField
	Label labelTitle;
	@UiField
	Label date;
	@UiField
	Label type;
	@UiField
	Image imageMarkerIcon;
	
	
}
