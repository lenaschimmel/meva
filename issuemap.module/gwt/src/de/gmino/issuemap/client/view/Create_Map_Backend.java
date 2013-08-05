package de.gmino.issuemap.client.view;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.MapHasMarkertype;
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
		getNewMapObject();
	}

	@UiField
	Label heading;
	
	@UiField
	Button button;
	@UiField
	TextBox title;
	@UiField
	TextBox subdomain;
	@UiField
	TextBox infoText;
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
	
	Map map;

	@UiHandler("button")
	void onClick(ClickEvent e) {
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
		Requests.saveEntity(map, null);
	}

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
		
		
	}
	
	public void showNewMap()
	{
		clear_form();
		getNewMapObject();
		heading.setText("Neue Map erstellen");
	}

	public void getNewMapObject() {
		Requests.getNewEntity(Map.type, new RequestListener<Map>() {
			@Override
			public void onNewResult(Map result) {
				Create_Map_Backend.this.map = result;
			}
		});
	}	
	
	public void showExistingMap(Map map, boolean getNewId)
	{
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
		
		//Alle Markertypes
		Requests.getLoadedEntitiesByType(Markertype.type, new RequestListener<Markertype>() {
			@Override
			public void onFinished(Collection<Markertype> results) {
				
			}
		});
		//Alle Markertypes der Map
		map.loadMarkertypes(new RequestListener<Markertype>() {
			@Override
			public void onFinished(Collection<Markertype> results) {
				
			}
		});
		
		if(getNewId)
		{
			getNewMapObject();
			heading.setText("Map kopieren (ALTE id="+map.getId()+")");
		}
		else
			heading.setText("Map editieren (id="+map.getId()+")");
	}

	
}
