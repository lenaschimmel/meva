package de.gmino.issuemap.client.view;

import java.util.HashMap;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class Create_Map_Backend extends Composite {

	private static Create_Map_BackendUiBinder uiBinder = GWT
			.create(Create_Map_BackendUiBinder.class);

	interface Create_Map_BackendUiBinder extends
			UiBinder<Widget, Create_Map_Backend> {
	}

	public Create_Map_Backend() {
		initWidget(uiBinder.createAndBindUi(this));
		//Alle Markertypes
		Requests.getLoadedEntitiesByType(Markertype.type, new RequestListener<Markertype>() {

			@Override
			public void onNewResult(Markertype markertype) {
				Marker_List_Item item = new Marker_List_Item(markertype.getImageName(), markertype.getMarkerName());
				markerPanel.add(item);
				markerListItems.put(markertype, item);
				markertypes.put(item, markertype);
				super.onNewResult(markertype);
			}
		});
		getNewMapObject();
		eeBackend = new EE_Backend();
		eePanel.add(eeBackend);
		eePanel.setVisible(false);
		markerPanel.setVisible(false);
		isNewMap = true;
	}
	
	TreeMap<Markertype, Marker_List_Item> markerListItems = new TreeMap<Markertype, Marker_List_Item>(); 
	HashMap<Marker_List_Item, Markertype> markertypes = new HashMap<Marker_List_Item, Markertype>(); 

	@UiField
	Label heading;
	
	@UiField
	Button button;
	@UiField
	TextBox title;
	@UiField
	TextBox subdomain;
	@UiField
	TextArea infoText;
	@UiField
	TextBox primary_color;
	@UiField
	TextBox secondary_color;
	@UiField
	TextBox background_color;
	@UiField
	TextBox resolved_color;
	@UiField
	TextBox city;
	@UiField
	TextBox initLocation_latitude;
	@UiField
	TextBox initLocation_longitude;
	@UiField
	TextBox initZoomlevel;
	@UiField
	TextBox logo_url;
	@UiField
	TextBox website;
	@UiField
	TextBox email;
	@UiField
	TextBox url_impressum;
	@UiField
	ListBox mapType;
	@UiField
	TextBox recipient_name;
	@UiField
	TextBox street;
	@UiField
	TextBox houseNumber;
	@UiField
	TextBox zip;
	@UiField
	TextBox additionalAddressLine;
	@UiField
	FlowPanel markerPanel;
	@UiField
	FlowPanel eePanel;
	
	Map map;
	private boolean isNewMap;
	private EE_Backend eeBackend;

	@UiHandler("button")
	void onClick(ClickEvent e) {
		if(isNewMap)
			performSave();
		else
			Requests.loadEntity(map, new RequestListener<Map>() {
				public void onFinished(java.util.Collection<Map> results) {
					performSave();
				}
		});
	}
	
	@UiHandler("primary_color")
	void onPrimaryColorChange(ChangeEvent e) {
		primary_color.getElement().getStyle().setBackgroundColor(primary_color.getText());
	}
	
	@UiHandler("secondary_color")
	void onSecondaryColorChange(ChangeEvent e) {
		secondary_color.getElement().getStyle().setBackgroundColor(secondary_color.getText());
	}
	
	@UiHandler("background_color")
	void onBackgroundColorChange(ChangeEvent e) {
		background_color.getElement().getStyle().setBackgroundColor(background_color.getText());
	}
	
	@UiHandler("resolved_color")
	void onResolvedColorChange(ChangeEvent e) {
		resolved_color.getElement().getStyle().setBackgroundColor(resolved_color.getText());
	}


	private void performSave() {
		map.setTitle(title.getText());
		map.setSubdomain(subdomain.getText());
		map.setInfoText(infoText.getText());
		map.setPrimary_color(primary_color.getText());
		map.setSecondary_color(secondary_color.getText());
		map.setBackground_color(background_color.getText());
		map.setResolved_color(resolved_color.getText());
		map.setCity(city.getText());
		map.setInitLocation(new LatLon(Double.parseDouble(initLocation_latitude.getText()) , Double.parseDouble(initLocation_longitude.getText())));
		map.setInitZoomlevel(Integer.parseInt(initZoomlevel.getText()));
		map.setLogo(new ImageUrl(logo_url.getText()));
		map.setWebsite(website.getText());
		map.setEmail(email.getText());
		map.setImpressum_url(url_impressum.getText());
		map.setPostal_address(new Address(recipient_name.getText(),street.getText(),houseNumber.getText(),zip.getText(),city.getText(),additionalAddressLine.getText()));
		if(mapType.getSelectedIndex()==0) map.setMapTyp("Issue");
		if(mapType.getSelectedIndex()==1) map.setMapTyp("Event");
		if(mapType.getSelectedIndex()==2) map.setMapTyp("EE");
		
		for(Marker_List_Item i : markerListItems.values()){
			if(i.selected)
				map.getHasMarkertypes().add(markertypes.get(i));
			else
				map.getHasMarkertypes().remove(markertypes.get(i));

		}
		Requests.saveEntity(map, null);
		isNewMap = false;
	};
	
	public void clear_form(){
		title.setText("");
		subdomain.setText("");
		infoText.setText("");
		primary_color.setText("");
		secondary_color.setText("");
		background_color.setText("");
		resolved_color.setText("");
		city.setText("");
		initLocation_latitude.setText("");
		initLocation_longitude.setText("");
		initZoomlevel.setText("");
		logo_url.setText("");
		website.setText("");
		email.setText("");
		url_impressum.setText("");
		mapType.setSelectedIndex(0);
		recipient_name.setText("");
		street.setText("");
		houseNumber.setText("");
		zip.setText("");
		additionalAddressLine.setText("");
		markerPanel.setVisible(false);
		eePanel.setVisible(false);
		primary_color.getElement().getStyle().setBackgroundColor("#FFF");
		secondary_color.getElement().getStyle().setBackgroundColor("#FFF");
		background_color.getElement().getStyle().setBackgroundColor("#FFF");
		background_color.getElement().getStyle().setColor("#000");
		resolved_color.getElement().getStyle().setBackgroundColor("#FFF");
		
		for(Marker_List_Item i : markerListItems.values()){
			i.setUncheckBox();
		}
		
	}
	
	public void showNewMap()
	{
		clear_form();
		getNewMapObject();
		heading.setText("Neue Map erstellen");
		isNewMap = true;
	}

	public void getNewMapObject() {
		Requests.getNewEntity(Map.type, new RequestListener<Map>() {
			@Override
			public void onNewResult(Map result) {
				Create_Map_Backend.this.map = result;
				setPlaceholder();
			}
		});
	}	
	
	public void showExistingMap(Map map, boolean getNewId)
	{
		clear_form();

		this.map = map;
		title.setText(map.getTitle());
		subdomain.setText(map.getSubdomain());
		infoText.setText(map.getInfoText());
		primary_color.setText(map.getPrimary_color());
		secondary_color.setText(map.getSecondary_color());
		background_color.setText(map.getBackground_color());
		resolved_color.setText(map.getResolved_color());
		city.setText(map.getCity());
		initLocation_latitude.setText(map.getInitLocation().getLatitude()+"");
		initLocation_longitude.setText(map.getInitLocation().getLongitude()+"");
		initZoomlevel.setText(map.getInitZoomlevel()+"");
		logo_url.setText(map.getLogo().getUrl());
		website.setText(map.getWebsite());
		email.setText(map.getEmail());
		url_impressum.setText(map.getImpressum_url());
		recipient_name.setText(map.getPostal_address().getRecipientName());
		street.setText(map.getPostal_address().getStreet());
		houseNumber.setText(map.getPostal_address().getHouseNumber());
		zip.setText(map.getPostal_address().getZip());
		additionalAddressLine.setText(map.getPostal_address().getAdditionalAddressLine());
		primary_color.getElement().getStyle().setBackgroundColor(primary_color.getText());
		secondary_color.getElement().getStyle().setBackgroundColor(secondary_color.getText());
		background_color.getElement().getStyle().setBackgroundColor(background_color.getText());
		background_color.getElement().getStyle().setColor("#FFF");
		resolved_color.getElement().getStyle().setBackgroundColor(resolved_color.getText());

		
		if(map.getMapTyp().equals("Issue")) mapType.setSelectedIndex(0);
		if(map.getMapTyp().equals("Event")) mapType.setSelectedIndex(1);
		if(map.getMapTyp().equals("EE")) mapType.setSelectedIndex(2);
		
		if(map.getMapTyp().equals("EE"))
		{
			eePanel.setVisible(true);
			eeBackend.setMap(map);
		}
		else
		{
			markerPanel.setVisible(true);
			//Alle Markertypes der Map
			map.loadMarkertypes(new RequestListener<Markertype>() {
				@Override
				public void onNewResult(Markertype result) {
						markerListItems.get(result).setCheckBox();
					super.onNewResult(result);
				}
				
			});
		}
		
		isNewMap = getNewId;
		
		if(getNewId)
		{
			getNewMapObject();
			heading.setText("Map kopieren (ALTE id="+map.getId()+")");
		}
		else
			heading.setText("Map editieren (id="+map.getId()+")");
	}

	public void setPlaceholder(){
		title.getElement().setAttribute("placeholder", "Titel der Karte");
		title.getElement().setAttribute("title", "Titel der Karte");
		subdomain.getElement().setAttribute("placeholder", "Subdomain");
		subdomain.getElement().setAttribute("title", "Subdomain");
		infoText.getElement().setAttribute("placeholder", "Informations-Text");
		infoText.getElement().setAttribute("title", "Informations-Text");
		logo_url.getElement().setAttribute("placeholder", "URL zum Logo");
		logo_url.getElement().setAttribute("title", "URL zum Logo");
		primary_color.getElement().setAttribute("placeholder", "Markerfarbe");
		primary_color.getElement().setAttribute("title", "Markerfarbe");
		secondary_color.getElement().setAttribute("placeholder", "Textfarbe");
		secondary_color.getElement().setAttribute("title", "Textfarbe");
		background_color.getElement().setAttribute("placeholder", "Hintergrund-Farbe");
		background_color.getElement().setAttribute("title", "Hintergrund-Farbe");
		resolved_color.getElement().setAttribute("placeholder", "Erledigt-Farbe");
		resolved_color.getElement().setAttribute("title", "Erledigt-Farbe");
		initLocation_latitude.getElement().setAttribute("placeholder", "Latitude");
		initLocation_latitude.getElement().setAttribute("title", "Latitude");
		initLocation_longitude.getElement().setAttribute("placeholder", "Longitude");
		initLocation_longitude.getElement().setAttribute("title", "Longitude");
		initZoomlevel.getElement().setAttribute("placeholder", "Zoomlevel");
		initZoomlevel.getElement().setAttribute("title", "Zoomlevel");
		website.getElement().setAttribute("placeholder", "URL zur Website des Betreibers");
		website.getElement().setAttribute("title", "URL zur Website des Betreibers");
		url_impressum.getElement().setAttribute("placeholder", "URL zum Impressum");
		url_impressum.getElement().setAttribute("title", "URL zum Impressum");
		recipient_name.getElement().setAttribute("placeholder", "Name des Betreibers");
		recipient_name.getElement().setAttribute("title", "Name des Betreibers");
		email.getElement().setAttribute("placeholder", "Mail-Adresse");
		email.getElement().setAttribute("title", "Mail-Adresse");
		additionalAddressLine.getElement().setAttribute("placeholder", "Adresszusatz");
		additionalAddressLine.getElement().setAttribute("title", "Adresszusatz");
		street.getElement().setAttribute("placeholder", "Straße");
		street.getElement().setAttribute("title", "Straße");
		houseNumber.getElement().setAttribute("placeholder", "Hausnummer");
		houseNumber.getElement().setAttribute("title", "Hausnummer");
		zip.getElement().setAttribute("placeholder", "PLZ");
		zip.getElement().setAttribute("title", "PLZ");
		city.getElement().setAttribute("placeholder", "Stadt");
		city.getElement().setAttribute("title", "Stadt");
		
		
	}
	
}
