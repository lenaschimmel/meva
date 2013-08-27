package de.gmino.issuemap.client.view;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.meva.client.domain.LongText;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.ValueWrapper;
import de.gmino.meva.shared.domain.KeyValueDef;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class Create_PopUp extends Composite {

	private static PopUpUiBinder uiBinder = GWT.create(PopUpUiBinder.class);
	private Map mapObject;
	OpenLayersSmartLayer smartLayer;
	private Poi mIssue = null;
	private boolean newIssue;

	interface PopUpUiBinder extends UiBinder<Widget, Create_PopUp> {
	}

	private Create_PopUp(Map map, OpenLayersSmartLayer smartLayer) {
		this.smartLayer = smartLayer;
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;

		for (de.gmino.issuemap.shared.domain.Markertype mt : map.getHasMarkertypes())
			typebox.addItem(mt.getMarkerName(), mt.getId() + "");

		
	}
	
	public Create_PopUp(final Map map, final LatLon location, OpenLayersSmartLayer smartLayer) {
		this(map, smartLayer);

		Requests.loadEntity(map.getMarkerClass(), new RequestListener<de.gmino.meva.shared.domain.KeyValueSet>() {
			@Override
			public void onNewResult(final de.gmino.meva.shared.domain.KeyValueSet set) {
				Requests.loadEntities(set.getDefs(), new RequestListener<KeyValueDef>() {
					@Override
					public void onFinished(Collection<KeyValueDef> defs) {
						// this is a dummy instance, just needed to provide an
						// early icon. As soon as the real issue object is
						// present, it will be used instead.
						headLable.setText("Neuen Marker erstellen");
						mIssue = new Poi(-1);
						mIssue.setKeyvalueset(map.getMarkerClass());
						mIssue.setMap_instance(map);
						updateIcon();

						Requests.getNewEntity(Poi.type, new RequestListener<Poi>() {
							public void onNewResult(Poi issue) {
								mIssue = issue; // needed for upload handler
								issue.setKeyvalueset(map.getMarkerClass());
								issue.setLocation(location);
								issue.setMap_instance(map);
								issue.setCreationTimestamp(Timestamp.now());
								updateIcon();
							}
						});

						newIssue = true;
						description.getElement().setAttribute("placeholder", "Beschreibung");
						title.getElement().setAttribute("placeholder", "Bitte geben Sie einen Titel ein");
					}
				});
			}
		});

	}

	public Create_PopUp(Map map, Poi editIssue, OpenLayersSmartLayer smartLayer) {
		this(map,smartLayer);
		this.mIssue = editIssue;
		button.setText("Speichern");
		if(editIssue.getMap_instance().isDelete()) delete.setVisible(true);
		title.setText(editIssue.getTitle());
		description.setText(editIssue.getValue("Beschreibung").getString());
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
	@UiField
	Image delete;
	@UiField
	Button button;


	public Create_PopUp() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("close")
	void onClose(ClickEvent e) {
		this.removeFromParent();
	}
	
	@UiHandler("delete")
	void onDelete(ClickEvent e) {
		if(Window.confirm("Möchten sie diesen Marker wirklich löschen?"))
		{
			mIssue.setDeleted(true);
			//button.setText("Löschen");
			final IssuemapGwt issueMap = IssuemapGwt.getInstance();
			issueMap.deleteMarker(mIssue);
			issueMap.setCounter();
			issueMap.loadIssuesToList();
			Requests.saveEntity(mIssue, null);
			this.removeFromParent();
		}
	}
	
	@UiHandler("button")
	void onClick(ClickEvent e) {
		if(title.getText().equals("")) 
			title.getElement().setAttribute("placeholder", "Bitte geben Sie erst einen Titel ein, bevor Sie den Eintrag speichern");
		else {
			Requests.loadEntity(mapObject, new RequestListener<Map>() {
				@Override
				public void onFinished(Collection<Map> results) {
					setIssueValuesFromMask(mIssue);
					smartLayer.updatePoi(mIssue); // works even if the poi is a new one
					mapObject.getIssues().add(mIssue); // works even if the poi is already present
					Requests.saveEntity(mIssue, new RequestListener<Poi>() {
						public void onFinished(java.util.Collection<Poi> results) {
							final IssuemapGwt issueMap = IssuemapGwt.getInstance();
							issueMap.loadIssuesToList();
						};
					});
					
					Requests.saveEntity(mapObject, null);
					
					if(newIssue)
					{
						// Add marker to map
						final IssuemapGwt issueMap = IssuemapGwt.getInstance();
						issueMap.addMarker(mIssue);
						issueMap.setCounter();
					}
					
					removeFromParent();
				}
			});
			
		}
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

	private void setIssueValuesFromMask(Poi issue){
		long markertypeId = Long.parseLong(typebox.getValue(typebox.getSelectedIndex()));
		Markertype markertype = (Markertype) Markertype.getById(markertypeId);
		issue.setTitle(title.getText());
		issue.setMarkertype(markertype);
		issue.getValue("Beschreibung").setValue(new LongText(description.getText()));
	}
	
	
}
