package de.gmino.issuemap.client.view.backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.issuemap.client.view.list.Marker_List_Item;
import de.gmino.meva.client.domain.KeyValueDef;
import de.gmino.meva.client.domain.KeyValueSet;
import de.gmino.meva.shared.TypeName;
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

		getNewMapObject();
		eeBackend = new EE_Backend();
		eePanel.add(eeBackend);
		eePanel.setVisible(false);
		isNewMap = true;
		deckPanel.showWidget(0);
		activateTab(0);
		
		Requests.getLoadedEntitiesByType(KeyValueSet.type, new RequestListener<KeyValueSet>() {
			@Override
			public void onNewResult(KeyValueSet set) {
				
				
					mapClass.addItem(set.getName(), set.getId()+"");
				
//				Collection defs = IssuemapGwt.<de.gmino.meva.client.domain.KeyValueDef, de.gmino.meva.shared.domain.KeyValueDef>convertCollection(set.getDefs());
//				Requests.loadEntities(defs, new RequestListener<KeyValueDef>() {
//					@Override
//					public void onNewResult(KeyValueDef def) {
//						String name = def.getName();
//						String valueTypeString = def.getValueType();
//						TypeName.getByString(valueTypeString).isEntity();
//						
//						for(TypeName tn : TypeName.getAllTypes())
//						{
//							System.out.println("Es gibt einen Typ namens " + tn.toString());
//						}
//					}
//				});
			}
		});
	
		
		Requests.getLoadedEntitiesByType(Markertype.type, new RequestListener<Markertype>() {
			@Override
			public void onNewResult(Markertype markertype) {
				Marker_List_Item item = new Marker_List_Item(markertype.getImageName(), markertype.getMarkerName());
				markerListItems.put(markertype, item);
				markertypes.put(item, markertype);
				markerPanel.add(item);
				item.setVisible(false);

			}
		});
		
	}
	
	
	interface Style extends CssResource {
		String underline();
		String active();
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
	TextBox bar_text_color;
	@UiField
	TextBox bar_background_color;
	@UiField
	TextBox popup_text_color;
	@UiField
	TextBox popup_background_color;
	@UiField
	TextBox marker_color;
	@UiField
	TextBox resolved_color;
	@UiField
	TextBox owner_city;
	@UiField
	TextBox map_city;
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
	ListBox mapClass;
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
	
	@UiField
	CheckBox fotosCheckbox;
	@UiField
	CheckBox commentsCheckbox;
	@UiField
	CheckBox ratingsCheckbox;
	@UiField
	CheckBox listCheckbox;
	@UiField
	CheckBox filtersCheckbox;
	@UiField
	CheckBox searchStreetCheckbox;
	@UiField
	CheckBox searchCityCheckbox;
	@UiField
	ListBox layerSelect;
	
	@UiField
	CheckBox createCheckbox;
	@UiField
	CheckBox editCheckbox;
	@UiField
	CheckBox deleteCheckbox;
	@UiField
	CheckBox markCheckbox;
	@UiField
	TextBox markDescription;
	@UiField
	TextBox rateCriteria;
	@UiField
	Label deleteLabel;
	@UiField
	FlexTable tblkKeyValue;
	
	@UiField
	Button tabButtonMeta;
	@UiField
	Button tabButtonMarker;
	@UiField
	Button tabButtonOptions;
	@UiField
	DeckPanel deckPanel;
	@UiField
	Style style;

	

		
	private void activateTab(int i) {
		deckPanel.showWidget(i);
		tabButtonMeta.setStyleName(style.underline(), i == 0);
		tabButtonMarker		.setStyleName(style.underline(), i == 1);
		tabButtonOptions	.setStyleName(style.underline(), i == 2);
	}
	
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
	

	@SuppressWarnings("unchecked")
	@UiHandler("mapClass")
	public void onMapClassChanged(ChangeEvent e)
	{
		tblkKeyValue.removeAllRows();
		final KeyValueSet selectedSet = (KeyValueSet) KeyValueSet.getById(Long.parseLong(mapClass.getValue(mapClass.getSelectedIndex())));
		Collection defs = IssuemapGwt.<de.gmino.meva.client.domain.KeyValueDef, de.gmino.meva.shared.domain.KeyValueDef>convertCollection(selectedSet.getDefs());
		
		
		
		Requests.loadEntities(defs, new RequestListener<KeyValueDef>() {
			@Override
			public void onFinished(Collection<KeyValueDef> results) {
				int r = 0;
				for(KeyValueDef def : results)
				{
					String name = def.getName();
					String valueTypeString = def.getValueType();
					
					tblkKeyValue.setText(r, 0, valueTypeString);
					tblkKeyValue.setText(r, 2, name);
					if(TypeName.getByString(valueTypeString).isEntity())
						tblkKeyValue.setText(r, 1, "Entität");
					if(TypeName.getByString(valueTypeString).isValue())
						tblkKeyValue.setText(r, 1, "Wert");
					if(TypeName.getByString(valueTypeString).isNative())
						tblkKeyValue.setText(r, 1, "Java-nativ");
					r++;
				}
			}
		});
		
//		markerPanel.clear();
//		markerListItems.clear();
//		markertypes.clear();
		
		
		Requests.getLoadedEntitiesByType(Markertype.type, new RequestListener<Markertype>() {
			@Override
			public void onNewResult(Markertype markertype) {

				if(markertype.getMarkerClassId() == selectedSet.getId())
				{

					markerListItems.get(markertype).setVisible(true);

				}
				else {markerListItems.get(markertype).setVisible(false);}
			}
		});
	}
	
	
	
	@UiHandler("bar_background_color")
	void onHeaderBackgroundChange(ChangeEvent e) {
		bar_background_color.getElement().getStyle().setBackgroundColor(bar_background_color.getText());
		
	}
	
	@UiHandler("bar_text_color")
	void onHeaderTextChange(ChangeEvent e) {
		bar_text_color.getElement().getStyle().setBackgroundColor(bar_text_color.getText());
	}
	
	@UiHandler("popup_background_color")
	void onPopupBackgroundChange(ChangeEvent e) {
		popup_background_color.getElement().getStyle().setBackgroundColor(popup_background_color.getText());
	}
	
	@UiHandler("popup_text_color")
	void onPopupTextChange(ChangeEvent e) {
		popup_text_color.getElement().getStyle().setBackgroundColor(popup_text_color.getText());
	}
	
	@UiHandler("marker_color")
	void onMarkerColorChange(ChangeEvent e) {
		marker_color.getElement().getStyle().setBackgroundColor(marker_color.getText());
	}
	
	@UiHandler("resolved_color")
	void onResolvedColorChange(ChangeEvent e) {
		resolved_color.getElement().getStyle().setBackgroundColor(resolved_color.getText());
	}

	@UiHandler("editCheckbox")
	public void onEditCheckbixClicked(ValueChangeEvent<Boolean> ev) {
	    if(ev.getValue()){
	    	deleteCheckbox.setVisible(true);
	    	deleteLabel.setVisible(true);
	    	
	    }
	    else {
	    	deleteCheckbox.setVisible(false);
	    	deleteCheckbox.setValue(false);
	    	deleteLabel.setVisible(false);

	    	}	    	
	}
	
	@UiHandler("tabButtonMeta")
	void onTabButtonMetaClick(ClickEvent e) {
		activateTab(0);
	}

	@UiHandler("tabButtonMarker")
	void onTabButtonMarkerClick(ClickEvent e) {
		activateTab(1);
	}
		
	@UiHandler("tabButtonOptions")
	void onTabButtonOptionsClick(ClickEvent e) {
		activateTab(2);
	}

	private void performSave() {
		map.setTitle(title.getText());
		map.setSubdomain(subdomain.getText());
		map.setInfoText(infoText.getText());
		map.setBarBackgroundColor(bar_background_color.getText());
		map.setBarTextColor(bar_text_color.getText());
		map.setPopupBackgroundColor(popup_background_color.getText());
		map.setPopupTextColor(popup_text_color.getText());
		map.setMarkerColor(marker_color.getText());
		map.setResolvedColor(resolved_color.getText());
		map.setCity(map_city.getText());
		map.setInitLocation(new LatLon(Double.parseDouble(initLocation_latitude.getText()) , Double.parseDouble(initLocation_longitude.getText())));
		map.setInitZoomlevel(Integer.parseInt(initZoomlevel.getText()));
		map.setLogo(new ImageUrl(logo_url.getText()));
		map.setWebsite(website.getText());
		map.setEmail(email.getText());
		map.setImpressum_url(url_impressum.getText());
		map.setPostal_address(new Address(recipient_name.getText(),street.getText(),houseNumber.getText(),zip.getText(),owner_city.getText(),additionalAddressLine.getText()));
		map.setHas_fotos(fotosCheckbox.getValue());
		map.setHas_comments(commentsCheckbox.getValue());
		map.setHas_ratings(ratingsCheckbox.getValue());
		map.setHas_list(listCheckbox.getValue());
		map.setHas_filters(filtersCheckbox.getValue());
		map.setCreate(createCheckbox.getValue());
		map.setDelete(deleteCheckbox.getValue());
		map.setEdit(editCheckbox.getValue());
		map.setMark(markCheckbox.getValue());
		map.setMark_description(markDescription.getText());
		map.setRate_criteria(rateCriteria.getText());
		map.setSearchStreet(searchStreetCheckbox.getValue());
		map.setSearchCity(searchCityCheckbox.getValue());
		if(layerSelect.getSelectedIndex()==0) map.setLayer("mapquest");
		if(layerSelect.getSelectedIndex()==1) map.setLayer("mapnik");
		if(layerSelect.getSelectedIndex()==2) map.setLayer("oepnv");
		if(layerSelect.getSelectedIndex()==3) map.setLayer("Bing Road");
		if(layerSelect.getSelectedIndex()==4) map.setLayer("Bing Hybrid");
		if(layerSelect.getSelectedIndex()==5) map.setLayer("landscape");

	

		
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
		bar_background_color.setText("");
		bar_text_color.setText("");
		popup_background_color.setText("");
		popup_text_color.setText("");
		marker_color.setText("");
		resolved_color.setText("");
		map_city.setText("");
		initLocation_latitude.setText("");
		initLocation_longitude.setText("");
		initZoomlevel.setText("");
		logo_url.setText("");
		website.setText("");
		email.setText("");
		url_impressum.setText("");
		recipient_name.setText("");
		street.setText("");
		houseNumber.setText("");
		zip.setText("");
		owner_city.setText("");
		additionalAddressLine.setText("");
		eePanel.setVisible(false);
		bar_background_color.getElement().getStyle().setBackgroundColor("#FFF");
		bar_text_color.getElement().getStyle().setBackgroundColor("#FFF");
		popup_background_color.getElement().getStyle().setBackgroundColor("#FFF");
		popup_text_color.getElement().getStyle().setBackgroundColor("#FFF");
		marker_color.getElement().getStyle().setBackgroundColor("#FFF");
		resolved_color.getElement().getStyle().setBackgroundColor("#FFF");
		fotosCheckbox.setValue(false);
		commentsCheckbox.setValue(false);
		ratingsCheckbox.setValue(false);
		listCheckbox.setValue(false);
		filtersCheckbox.setValue(false);
		editCheckbox.setValue(false);
		markCheckbox.setValue(false);
		createCheckbox.setValue(false);
		deleteCheckbox.setValue(false);
		deleteCheckbox.setVisible(false);
		deleteLabel.setVisible(false);
		rateCriteria.setText("");
		markDescription.setText("");
		searchStreetCheckbox.setValue(false);
		searchCityCheckbox.setValue(false);
		layerSelect.setSelectedIndex(0);
		
		
		
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
		
		bar_background_color.setText(map.getBarBackgroundColor());
		bar_text_color.setText(map.getBarTextColor());
		popup_background_color.setText(map.getPopupBackgroundColor());
		popup_text_color.setText(map.getPopupTextColor());
		marker_color.setText(map.getMarkerColor());
		resolved_color.setText(map.getResolvedColor());
		
		bar_background_color.getElement().getStyle().setBackgroundColor(bar_background_color.getText());
		bar_text_color.getElement().getStyle().setBackgroundColor(bar_text_color.getText());
		popup_background_color.getElement().getStyle().setBackgroundColor(popup_background_color.getText());
		popup_text_color.getElement().getStyle().setBackgroundColor(popup_text_color.getText());
		marker_color.getElement().getStyle().setBackgroundColor(marker_color.getText());
		resolved_color.getElement().getStyle().setBackgroundColor(resolved_color.getText());
		
		
		
		map_city.setText(map.getCity());
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
		owner_city.setText(map.getPostal_address().getCity());
		additionalAddressLine.setText(map.getPostal_address().getAdditionalAddressLine());
		fotosCheckbox.setValue(map.isHas_fotos());
		commentsCheckbox.setValue(map.isHas_comments());
		ratingsCheckbox.setValue(map.isHas_ratings());
		rateCriteria.setText(map.getRate_criteria());
		listCheckbox.setValue(map.isHas_list());
		filtersCheckbox.setValue(map.isHas_filters());
		createCheckbox.setValue(map.isCreate());
		editCheckbox.setValue(map.isEdit());
		markCheckbox.setValue(map.isMark());
		markDescription.setText(map.getMark_description());
		deleteCheckbox.setValue(map.isDelete());
		if(map.isEdit()) {
			deleteCheckbox.setVisible(true);
			deleteLabel.setVisible(true);
		}
		searchCityCheckbox.setValue(map.isSearchCity());
		searchStreetCheckbox.setValue(map.isSearchStreet());
		if (map.getLayer().equals("mapquest")) layerSelect.setSelectedIndex(0);
		if (map.getLayer().equals("mapnik")) layerSelect.setSelectedIndex(1);
		if (map.getLayer().equals("oepnv")) layerSelect.setSelectedIndex(2);
		if (map.getLayer().equals("Bing Road")) layerSelect.setSelectedIndex(3);
		if (map.getLayer().equals("Bing Hybrid")) layerSelect.setSelectedIndex(4);
		if (map.getLayer().equals("landscape")) layerSelect.setSelectedIndex(5);

			markerPanel.setVisible(true);
			//Alle Markertypes der Map
			map.loadMarkertypes(new RequestListener<Markertype>() {
				@Override
				public void onNewResult(Markertype result) {
						markerListItems.get(result).setCheckBox();
					super.onNewResult(result);
				}
				
			});
		
		
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
		bar_background_color.getElement().setAttribute("placeholder", "Hintergrund-Farbe");
		bar_background_color.getElement().setAttribute("title", "Hintergrund-Farbe");
		bar_text_color.getElement().setAttribute("placeholder", "Textfarbe");
		bar_text_color.getElement().setAttribute("title", "Textfarbe");
		popup_background_color.getElement().setAttribute("placeholder", "Hintergrund-Farbe");
		popup_background_color.getElement().setAttribute("title", "Hintergrund-Farbe");
		popup_text_color.getElement().setAttribute("placeholder", "Textfarbe");	
		popup_text_color.getElement().setAttribute("title", "Textfarbe");
		marker_color.getElement().setAttribute("placeholder", "Markerfarbe");
		marker_color.getElement().setAttribute("title", "Markerfarbe");
		resolved_color.getElement().setAttribute("placeholder", "Erledigt-Farbe");
		resolved_color.getElement().setAttribute("title", "Erledigt-Farbe");
		map_city.getElement().setAttribute("placeholder", "Stadt");
		map_city.getElement().setAttribute("title", "Stadt");
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
		owner_city.getElement().setAttribute("placeholder", "Stadt");
		owner_city.getElement().setAttribute("title", "Stadt");
		rateCriteria.getElement().setAttribute("placeholder", "Kriterium");
		rateCriteria.getElement().setAttribute("title", "Kriterium");
		markDescription.getElement().setAttribute("placeholder", "Beschriftung");
		markDescription.getElement().setAttribute("title", "Beschriftung");
		layerSelect.getElement().setAttribute("title", "Karten-Layer");
	}
	
}
