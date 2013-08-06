package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
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

public class CreateIssue_PopUp extends Composite {

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

		for (de.gmino.issuemap.shared.domain.Markertype mt : map.getHasMarkertypes())
			typebox.addItem(mt.getMarkerName(), mt.getId() + "");

		
	}
	
	public CreateIssue_PopUp(final Map map, final LatLon location, OpenLayersSmartLayer smartLayer) {
		this(map,smartLayer);
		
		// this is a dummy instance, just needed to provide an early icon. As soon as the real issue object is present, it will be used instead.
		mIssue = new Issue(-1);
		mIssue.setMap_instance(map);
		updateIcon();
		
        Requests.getNewEntity(Issue.type, new RequestListener<Issue>() {
                public void onNewResult(Issue issue) {
                        mIssue = issue; // needed for upload handler
                        issue.setLocation(location);
                        issue.setMap_instance(map);
                        issue.setCreationTimestamp(Timestamp.now());
                        updateIcon();
                }
        });
		
        newIssue = true;
		headLable.setText("Neuen Marker erstellen");
		description.getElement().setAttribute("placeholder", "Beschreibung");
		title.getElement().setAttribute("placeholder", "Titel");
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
		
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mIssue);
		String iconUrl = renderer.getIconUrl(mIssue);
		imageMarkerIcon.setUrl(iconUrl);
		
		newIssue = false;
	}
	
	@UiField
	VerticalPanel parent;
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


	public CreateIssue_PopUp() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("close")
	void onClose(ClickEvent e) {
		this.removeFromParent();
	}
	
	@UiHandler("button")
	void onClick(ClickEvent e) {
		setIssueValuesFromMask(mIssue);
		smartLayer.updatePoi(mIssue); // works even if the poi is a new one
		mapObject.getIssues().add(mIssue); // works even if the poi is already present
		Requests.saveEntity(mIssue, null);
		Requests.saveEntity(mapObject, null);
		
		if(newIssue)
		{
			// Add marker to map
			final IssuemapGwt issueMap = IssuemapGwt.getInstance();
			issueMap.addMarker(mIssue);
			issueMap.setCounter();
			issueMap.loadIssuesToList();
		}
		
		this.removeFromParent();
	}
	
	@UiHandler("typebox")
	void onChange(ChangeEvent e)
	{
		updateIcon();
	}

	private void updateIcon() {
		setIssueValuesFromMask(mIssue);
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mIssue);
		String iconUrl = renderer.getIconUrl(mIssue);
		imageMarkerIcon.setUrl(iconUrl);
	}

	private void setIssueValuesFromMask(Issue issue){
		long markertypeId = Long.parseLong(typebox.getValue(typebox.getSelectedIndex()));
		Markertype markertype = (Markertype) Markertype.getById(markertypeId);
		issue.setTitle(title.getText());
		issue.setMarkertype(markertype);
		issue.setDescription(description.getText());
	}
	
	
}
