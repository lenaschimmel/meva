package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.Marker_Wrapper;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.meva.shared.request.Requests;

public class ShowIssue_PopUp extends Composite {

	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");

	private static Detail_PopUpUiBinder uiBinder = GWT
			.create(Detail_PopUpUiBinder.class);

	interface Detail_PopUpUiBinder extends UiBinder<Widget, ShowIssue_PopUp> {
	}

	Map mapObject;
	OpenLayersSmartLayer smartLayer;
	Issue mIssue;
	Marker_Wrapper mWrapper;

	public ShowIssue_PopUp(Map map, Issue issue, Marker_Wrapper marker_Wrapper, OpenLayersSmartLayer smartLayer) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.smartLayer = smartLayer;
		this.mIssue = issue;
		this.mWrapper = marker_Wrapper;
		setBoarderColor(map.getColor());

		if (mIssue.getPrimary_picture().getUrl().equals("")
				|| mIssue.getPrimary_picture().getUrl().equals("Bild URL")) { //TODO funktioniert nur halb?!
			picture.setVisible(false);
			picture.setHeight("0px");
		} else {
			ImageUrl img = mIssue.getPrimary_picture();
			picture.setUrl(img.getUrl());
		}
		date.setText(dtf.format(mIssue.getCreationTimestamp().toDate()));
		type.setText(mIssue.getMarkertype().getMarkerName());
		resolved.setValue(mIssue.isResolved());
		rating.setText("" + mIssue.getRating());
		up_count.setText("" +(mIssue.getNumber_of_rating()+mIssue.getRating())/2);
		down_count.setText("" + (mIssue.getNumber_of_rating()-mIssue.getRating())/2);
		title.setText(mIssue.getTitle());
		description.setText(mIssue.getDescription());

	}

	@UiField
	Label title;
	@UiField
	CheckBox resolved;
	@UiField
	Label rating;
	@UiField
	Label up_count;
	@UiField
	Label down_count;
	@UiField
	Image rate_up;
	@UiField
	Image rate_down;
	@UiField
	Label date;
	@UiField
	Label type;
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
		CreateIssue_PopUp cip = new CreateIssue_PopUp(mapObject, mIssue, smartLayer);
		cip.setBoarderColor(mapObject.getColor());
		mWrapper.add(cip);

	}

	@UiHandler("delete")
	void onDelete(ClickEvent e) {
		mIssue.setDeleted(true);
		Requests.saveEntity(mIssue, null);
		this.removeFromParent();
		smartLayer.removePoi(mIssue);
	}

	@UiHandler("resolved")
	void onCheckbox(ClickEvent e) {
		mIssue.setResolved(resolved.getValue());
		Requests.saveEntity(mIssue, null);
		smartLayer.updatePoi(mIssue);
	}

	@UiHandler("rate_up")
	void onRateUp(ClickEvent e) {
		mIssue.setRating(mIssue.getRating() + 1);
		mIssue.setNumber_of_rating(mIssue.getNumber_of_rating() + 1);
		rating.setText("" + (mIssue.getRating()));
		up_count.setText("" +(mIssue.getNumber_of_rating()+mIssue.getRating())/2);
		Requests.saveEntity(mIssue, null);
	}

	@UiHandler("rate_down")
	void onRateDown(ClickEvent e) {
		mIssue.setRating(mIssue.getRating() - 1);
		mIssue.setNumber_of_rating(mIssue.getNumber_of_rating() + 1);
		rating.setText("" + (mIssue.getRating()));
		down_count.setText("" + (mIssue.getNumber_of_rating()-mIssue.getRating())/2);
		Requests.saveEntity(mIssue, null);
	}

	public void setText(String titleString, String descriptionString) {
		description.setText(descriptionString);
		title.setText(titleString);
	}

	public void setBoarderColor(String color) {
		parent.getElement().getStyle().setBorderColor(color);
	}
}
