package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CreateIssue_PopUp extends Composite implements HasText {

	private static PopUpUiBinder uiBinder = GWT.create(PopUpUiBinder.class);
	private Map mapObject;
	private LatLon mLocation;
	private Issue mIssue = null;

	interface PopUpUiBinder extends UiBinder<Widget, CreateIssue_PopUp> {
	}

	public CreateIssue_PopUp(Map map, LatLon location) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.mLocation = location;
	}

	public CreateIssue_PopUp(Issue editIssue) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mIssue = editIssue;
		title.setText(editIssue.getTitle());
		description.setText(editIssue.getDescription());
		typebox.setSelectedIndex(0); // ToDo
		picture.setText(editIssue.getPrimary_picture().getUrl());
	}

	@UiField
	Button button;
	@UiField
	TextBox title;
	@UiField
	TextBox picture;
	@UiField
	TextArea description;
	@UiField
	ListBox typebox;
	@UiField
	Image close;

	public CreateIssue_PopUp(String firstName) {
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
			String type = null;
			if (typebox.getSelectedIndex() == 0)
				type = "Allgemein";
			if (typebox.getSelectedIndex() == 1)
				type = "Straﬂenschaden";
			if (typebox.getSelectedIndex() == 2)
				type = "Unfall";
			mIssue.setTitle(title.getText());

			
			mIssue.setDescription(description.getText());
			mIssue.setCreationTimestamp(Timestamp.now());
			mIssue.setIssueType(type);
			mIssue.setPrimary_picture(new ImageUrl(picture.getText()));
			Requests.saveEntity(mIssue, null);
		}

		// Fetch new content and add to Database
		else {
			Requests.getNewEntity(Issue.type, new RequestListener<Issue>() {
				public void onNewResult(Issue issue) {
					String type= typebox.getValue(typebox.getSelectedIndex());
					issue.setTitle(title.getText());
					issue.setLocation(mLocation);
					issue.setDescription(description.getText());
					issue.setCreationTimestamp(Timestamp.now());
					issue.setIssueType(type);
					issue.setPrimary_picture(new ImageUrl(picture.getText()));


					mapObject.getIssues().add(issue);
					Requests.saveEntity(issue, null);
					Requests.saveEntity(mapObject, null);

					// Add marker to map
					IssuemapGwt.addMarker(issue);

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

}
