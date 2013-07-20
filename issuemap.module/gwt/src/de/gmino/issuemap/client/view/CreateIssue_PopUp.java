package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class CreateIssue_PopUp extends Composite implements HasText {

	private static PopUpUiBinder uiBinder = GWT.create(PopUpUiBinder.class);
	private Map mapObject;
	OpenLayersSmartLayer smartLayer;
	private Issue mIssue = null;
	private boolean newIssue;

	interface PopUpUiBinder extends UiBinder<Widget, CreateIssue_PopUp> {
	}

	private CreateIssue_PopUp(Map map, OpenLayersSmartLayer smartLayer) {
		this.smartLayer = smartLayer;
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
	//	setBoarderColor(map.getColor());

		for (Markertype mt : map.getMarkertypes())
			typebox.addItem(mt.getMarkerName(), mt.getId() + "");

		
	}
	
	public CreateIssue_PopUp(Map map, final LatLon location, OpenLayersSmartLayer smartLayer) {
		this(map,smartLayer);
		
        Requests.getNewEntity(Issue.type, new RequestListener<Issue>() {
                public void onNewResult(Issue issue) {
                        mIssue = issue; // needed for upload handler
                        issue.setLocation(location);
                }
        });
		
        newIssue = true;
		headLable.setText("Neuen Marker erstellen");
	}

	public CreateIssue_PopUp(Map map, Issue editIssue, OpenLayersSmartLayer smartLayer) {
		this(map,smartLayer);
		this.mIssue = editIssue;
		title.setText(editIssue.getTitle());
		description.setText(editIssue.getDescription());
		String markertypeId = editIssue.getMarkertypeId() + "";
		for(int i = 0; i < typebox.getItemCount(); i++)
			if(typebox.getValue(i).equals(markertypeId))
			{
				typebox.setSelectedIndex(i);
				break;
			}
		
		newIssue = false;
	}
	
	@UiField
	VerticalPanel parent;
	@UiField
	Button button;
	@UiField
	TextBox title;
	@UiField
	TextArea description;
	@UiField
	ListBox typebox;
	@UiField
	Image close;
	@UiField
	Label headLable;
	@UiField
	Image imageMarkerIcon;


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
		setIssueValuesFromMask(mIssue);
		Requests.saveEntity(mIssue, null);
		smartLayer.updatePoi(mIssue); // works even if the poi is a new one
		mapObject.getIssues().add(mIssue); // works even if the poi is already present
		
		if(newIssue)
		{
			// Add marker to map
			IssuemapGwt.getInstance().addMarker(mIssue);
			IssuemapGwt.getInstance().setCounter();
		}
		
		this.removeFromParent();
	}
	
	@UiHandler("typebox")
	void onChange(ChangeEvent e)
	{
		setIssueValuesFromMask(mIssue);
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mIssue);
		String iconUrl = renderer.getIconUrl(mIssue);
		imageMarkerIcon.setUrl(iconUrl);
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
	}
	
	public void setBoarderColor(String color){
		parent.getElement().getStyle().setBorderColor(color);
	}
	
}
