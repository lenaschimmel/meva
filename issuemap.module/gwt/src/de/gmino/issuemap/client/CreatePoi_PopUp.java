package de.gmino.issuemap.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CreatePoi_PopUp extends Composite implements HasText {

	private static PopUpUiBinder uiBinder = GWT.create(PopUpUiBinder.class);
	private Map mapObject;
	private LatLon mLocation;


	interface PopUpUiBinder extends UiBinder<Widget, CreatePoi_PopUp> {
	}

	public CreatePoi_PopUp(Map map, LatLon location) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject=map;
		this.mLocation = location;
	}

	@UiField
	Button button;
	@UiField
	TextBox title;
	@UiField
	TextArea description;
	@UiField
	ListBox typebox;


	public CreatePoi_PopUp(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(firstName);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Requests.getNewEntity(Issue.type, new RequestListener<Issue>(){
			// Fetch content and add to Database
			public void onNewResult(Issue issue) {
				String type = null;
				if(typebox.getSelectedIndex()==0) type = "Allgemein";
				if(typebox.getSelectedIndex()==1) type = "Straﬂenschaden";
				if(typebox.getSelectedIndex()==2) type = "Unfall";
				issue.setTitle(title.getText());
				issue.setLocation(mLocation);
				issue.setDescription(description.getText());
				issue.setCreationTimestamp(Timestamp.now());
				issue.setIssueType(type);
				
				mapObject.getIssues().add(issue);
				Requests.saveEntity(issue, null);
				Requests.saveEntity(mapObject, null);
				
				// Add marker to map
				IssuemapGwt.addMarker(issue);
				
				//close PopUp
			}
		});
		this.removeFromParent();

	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

}
