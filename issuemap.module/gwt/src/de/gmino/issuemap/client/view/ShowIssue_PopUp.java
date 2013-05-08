package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.Marker_Wrapper;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.meva.shared.request.Requests;

public class ShowIssue_PopUp extends Composite {

	private static Detail_PopUpUiBinder uiBinder = GWT
			.create(Detail_PopUpUiBinder.class);

	interface Detail_PopUpUiBinder extends UiBinder<Widget, ShowIssue_PopUp> {
	}

	Map mapObject;
	Issue mIssue;
	Marker_Wrapper mWrapper;
	
	public ShowIssue_PopUp(Map map, Issue issue, Marker_Wrapper marker_Wrapper) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.mIssue = issue;
		this.mWrapper = marker_Wrapper;
		setBoarderColor(map.getColor());
		
		ImageUrl img= issue.getPrimary_picture();
		picture.setUrl(img.getUrl());
		
		
	}

	@UiField
	Label title;
	@UiField
	Label description;
	@UiField
	Image close;
	@UiField
	Image delete;
	@UiField
	Image edit;
	@UiField
	VerticalPanel parent;
	@UiField
	Image picture;

	@UiHandler("close")
	void onClose(ClickEvent e) {
		this.removeFromParent();
	}
	
	@UiHandler("edit")
	void onEdit(ClickEvent e) {
		this.removeFromParent();
		mWrapper.add(new CreateIssue_PopUp(mIssue));
	}
	
	@UiHandler("delete")
	void onDelete(ClickEvent e) {
		mIssue.setDeleted(true);
		Requests.saveEntity(mIssue, null);
		this.removeFromParent();
	// FIXME	IssuemapGwt.deleteMarker(mIssue);
		
	}

	public void setText(String titleString, String descriptionString) {
		description.setText(descriptionString);
		title.setText(titleString);
	}
	
	public void setBoarderColor(String color){
		parent.getElement().getStyle().setBorderColor(color);
	}
}
