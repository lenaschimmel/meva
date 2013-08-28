package de.gmino.issuemap.client.view.popup;

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
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.poi.Marker_Wrapper;
import de.gmino.meva.shared.request.Requests;

public class ShowEvent_PopUp extends Composite {

	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");

	private static Detail_PopUpUiBinder uiBinder = GWT
			.create(Detail_PopUpUiBinder.class);

	interface Detail_PopUpUiBinder extends UiBinder<Widget, ShowEvent_PopUp> {
	}

	Map mapObject;
	OpenLayersSmartLayer smartLayer;
	Poi mIssue;
	Marker_Wrapper mWrapper;

	public ShowEvent_PopUp(Map map, Poi issue, Marker_Wrapper marker_Wrapper, OpenLayersSmartLayer smartLayer) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.smartLayer = smartLayer;
		this.mIssue = issue;
		this.mWrapper = marker_Wrapper;
		setBoarderColor(map.getPrimary_color());

		date.setText(dtf.format(mIssue.getCreationTimestamp().toDate()));
		organizer.setText(mIssue.getValue("Veranstalter").getString());
		price.setText(mIssue.getValue("Eintritt").getFloat() + " Euro");
		type.setText(mIssue.getMarkertype().getMarkerName());
		resolved.setValue(mIssue.isMarked());
		title.setText(mIssue.getTitle());
		description.setText(mIssue.getValue("Beschreibung").getString());

	}

	@UiField
	Label title;
	@UiField
	CheckBox resolved;

	@UiField
	Label price;
	@UiField
	Label organizer;
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
		CreateEvent_PopUp cip = new CreateEvent_PopUp(mapObject, mIssue, smartLayer);
		cip.setBoarderColor(mapObject.getPrimary_color());
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
		mIssue.setMarked(resolved.getValue());
		Requests.saveEntity(mIssue, null);
		smartLayer.updatePoi(mIssue);
	}


	public void setText(String titleString, String descriptionString) {
		description.setText(descriptionString);
		title.setText(titleString);
	}

	public void setBoarderColor(String color) {
		parent.getElement().getStyle().setBorderColor(color);
	}
}
