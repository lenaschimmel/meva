package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CreateEvent_PopUp extends Composite implements HasText {

	private static PopUpUiBinder uiBinder = GWT.create(PopUpUiBinder.class);
	private Map mapObject;
	OpenLayersSmartLayer smartLayer;
	private LatLon mLocation;
	private Issue mIssue = null;

	interface PopUpUiBinder extends UiBinder<Widget, CreateEvent_PopUp> {
	}

	private CreateEvent_PopUp(Map map, OpenLayersSmartLayer smartLayer) {
		this.smartLayer = smartLayer;
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		setBoarderColor(map.getPrimary_color());
		for(Markertype mt : map.getMarkertypes())
			typebox.addItem(mt.getMarkerName(), mt.getId()+"");
	}
	
	public CreateEvent_PopUp(Map map, LatLon location, OpenLayersSmartLayer smartLayer) {
		this(map,smartLayer);
		this.mLocation = location;
		headLable.setText("Neuen Marker erstellen");
	}

	public CreateEvent_PopUp(Map map, Issue editIssue, OpenLayersSmartLayer smartLayer) {
		this(map,smartLayer);
		this.mIssue = editIssue;
		title.setText(editIssue.getTitle());
		description.setText(editIssue.getDescription());
		organizer.setText(editIssue.getOrganizer());
		pricebox.setSelectedIndex((int) editIssue.getPrice()); //TODO
		String markertypeId = editIssue.getMarkertypeId() + "";
		for(int i = 0; i < typebox.getItemCount(); i++)
			if(typebox.getValue(i).equals(markertypeId))
			{
				typebox.setSelectedIndex(i);
				break;
			}
		if (editIssue.getPrimary_picture().getUrl().equals("")) picture.setText("Bild URL");
		else picture.setText(editIssue.getPrimary_picture().getUrl());
	}
	
	@UiField
	VerticalPanel parent;
	@UiField
	Button button;
	@UiField
	TextBox title;
	@UiField
	TextBox picture;
	@UiField
	TextBox organizer;
	@UiField
	TextArea description;
	@UiField
	ListBox typebox;
	@UiField
	ListBox pricebox;
	@UiField
	Image close;
	@UiField
	Label headLable;

	public CreateEvent_PopUp(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(firstName);
	}

	@UiHandler("close")
	void onClose(ClickEvent e) {
		this.removeFromParent();
	}
	
	@UiHandler("button")
	void onClick(ClickEvent e) {

		// Save changes
		if (mIssue != null) {
			setIssueValuesFromMask(mIssue);
			Requests.saveEntity(mIssue, null);
			smartLayer.updatePoi(mIssue);
		}

		// Add new content to Database
		else {
			Requests.getNewEntity(Issue.type, new RequestListener<Issue>() {
				public void onNewResult(Issue issue) {
					setIssueValuesFromMask(issue);
					issue.setCreationTimestamp(Timestamp.now());
					issue.setLocation(mLocation);
					mapObject.getIssues().add(issue);
					Requests.saveEntity(issue, null);
					Requests.saveEntity(mapObject, null);

					// Add marker to map
					smartLayer.addPoi(issue);
					// FIXME IssuemapGwt.addMarker(issue);

				}
			});

		}
		// close PopUp
		this.removeFromParent();
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

	private void setIssueValuesFromMask(Issue issue){
		long markertypeId = Long.parseLong(typebox.getValue(typebox.getSelectedIndex()));
		Markertype markertype = mapObject.getMarkertypeById(markertypeId);
		issue.setTitle(title.getText());
		issue.setMarkertype(markertype);
		issue.setDescription(description.getText());
		issue.setPrimary_picture(new ImageUrl(picture.getText()));
		issue.setOrganizer(organizer.getText());
		issue.setPrice((long)pricebox.getSelectedIndex());
	}
	
	public void setBoarderColor(String color){
		parent.getElement().getStyle().setBorderColor(color);
	}
	
}
