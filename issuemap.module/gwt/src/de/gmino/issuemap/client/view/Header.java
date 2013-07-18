package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.request.Geocoder;
import de.gmino.geobase.client.request.Geocoder.SearchLocationListener;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;

public class Header extends Composite implements HasText {

	private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);
	private Map mapObject;

	interface HeaderUiBinder extends UiBinder<Widget, Header> {
	}

	public Header() {
		initWidget(uiBinder.createAndBindUi(this));
		info_button.setVisible(false);
		search_field.setVisible(false);
		search_field.getElement().setAttribute("placeholder", "Straßensuche");
		logo.setHeight("30px");
		header.setHeight("57px");
	}

	@UiField
	HorizontalPanel header;
	@UiField
	Image logo;
	@UiField
	Label title;
	@UiField
	TextBox search_field;
	@UiField
	PushButton info_button;

	public Header(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("info_button")
	void onInfoClick(ClickEvent e) {
		final DecoratedPopupPanel infoPopUp = new DecoratedPopupPanel();
		infoPopUp.add(new Info_PopUp(mapObject, infoPopUp));
		infoPopUp.show();
		infoPopUp.setPopupPosition(Window.getClientWidth()/2, Window.getClientHeight()/3);
	}
	
//	@UiHandler("search_field")
//	void onSearchClick(ClickEvent e) {
//		performSearch();
//	}

	@UiHandler("search_field")
	void onKeyUp(KeyUpEvent event) {
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			performSearch();
		}
	}

	private void performSearch() {
		final String query = mapObject.getCity() + ", " + search_field.getText();
		Geocoder gc = new Geocoder();
		gc.searchLocationByString(query, new SearchLocationListener() {
			
			@Override
			public void onLocationNotFound() {
				Window.alert("Kein Ergebnis für: " + query);
			}
			
			@Override
			public void onLocationFound(LatLon location) {
				IssuemapGwt.mapView.setCenter(location, true);
				IssuemapGwt.mapView.setZoom(17);
			}
			
			@Override
			public void onError(String message) {
				Window.alert("Es ist ein Fehler bei der Straßensuche passiert: " + message);
			}
		});
	}
	
	public void setDesign(String imgUrl, String titleString, String color) {
		logo.setUrl(imgUrl);
		title.setText(titleString);
		title.getElement().getStyle().setColor(color);
//		header.getElement().getStyle().setBorderColor(color);
//		info_button.getElement().getStyle().setBackgroundColor(color);
		info_button.setVisible(true);

		search_field.setVisible(true);
		logo.setHeight("45px");
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMap(Map map) {
		this.mapObject = map;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}



}
