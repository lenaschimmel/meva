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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.request.Geocoder;
import de.gmino.geobase.client.request.Geocoder.SearchLocationListener;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.issuemap.client.BaseApp;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.popup.Info_PopUp;
import de.gmino.meva.shared.request.Requests;

public class Header extends Composite  {

	private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);
	private Map mapObject;
	private boolean customURL;
	private String URL;
	private DecoratedPopupPanel decorated_panel;
	private BaseApp app;
	interface HeaderUiBinder extends UiBinder<Widget, Header> {
	}

	public Header(BaseApp app, DecoratedPopupPanel decorated_panel) {
		this.app = app;
		this.decorated_panel=decorated_panel;
		initWidget(uiBinder.createAndBindUi(this));
		info_button.setVisible(false);
		search_field.setVisible(false);
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
	@UiField
	Label logout;

	private Info_PopUp infoPopup;

	@UiHandler("info_button")
	void onInfoClick(ClickEvent e) {
		if (decorated_panel.isShowing()&&infoPopup.getActiveTab()==0) {
			decorated_panel.hide();
		} else {
			decorated_panel.setPopupPosition(Window.getClientWidth() / 2, (int) (Window.getClientHeight()*0.15));
			decorated_panel.show();
			infoPopup.activateTab(0);
		}
	}

	@UiHandler("search_field")
	void onKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			performSearch();
		}
	}

	@UiHandler("logout")
	void onLogoutClick(ClickEvent event) {
		Requests.logout();
		app.onLogut();
	}

	@UiHandler("logo")
	void onLogoClick(ClickEvent event) {
		if(customURL==true) Window.Location.replace(URL);
		else{
			Window.open(mapObject.getWebsite(), "Partei-Website", "");
		}
	}

	private void performSearch() {
		final String query = createSearchQuery();
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

	private String createSearchQuery() {
		if(!mapObject.isSearchCity() && mapObject.isSearchStreet())
			return mapObject.getCity() + ", " + search_field.getText();
		else
			return search_field.getText();
	}

	public void setBackendDesign(String imgUrl, String titleString, String textcolor, String backgroundcolor) {
		logo.setUrl(imgUrl);
		title.setText(titleString);

		title.getElement().getStyle().setColor(textcolor);
		logout.getElement().getStyle().setColor(textcolor);
		info_button.setVisible(true);
		header.getElement().getStyle().setBackgroundColor(backgroundcolor);
		search_field.setVisible(true);
		logo.setHeight("45px");
		info_button.setVisible(false);
		search_field.setVisible(false);
	}

	public void setFrontendDesign(Map map, Info_PopUp infoPopup) {
		this.mapObject = map;
		this.infoPopup=infoPopup;
		logo.setTitle("Klicken Sie, um die Website " + map.getWebsite() + " zu besuchen (öffnet in neuem Fenster / Tab).");
		logo.setUrl(map.getLogo().getUrl());
		title.setText(map.getTitle());

		title.getElement().getStyle().setColor(mapObject.getBarTextColor());
		logout.getElement().getStyle().setColor(mapObject.getBarTextColor());
		info_button.setVisible(true);
		header.getElement().getStyle()
				.setBackgroundColor(mapObject.getBarBackgroundColor());
		search_field.setVisible(true);
		logo.setHeight("45px");
		info_button.setVisible(true);

		decorated_panel.setWidget(infoPopup);
		decorated_panel.setGlassEnabled(false);
		
		if(map.isSearchCity() && map.isSearchStreet())
			search_field.getElement().setAttribute("placeholder", "Stadt, Straße");
		else if(map.isSearchStreet())
			search_field.getElement().setAttribute("placeholder", "Straßensuche");
		else if(map.isSearchCity())
			search_field.getElement().setAttribute("placeholder", "Stadtsuche");
		else
			search_field.setVisible(false);
	}

	public void setURL(String URL){
		customURL=true;
		this.URL=URL;
	}
}
