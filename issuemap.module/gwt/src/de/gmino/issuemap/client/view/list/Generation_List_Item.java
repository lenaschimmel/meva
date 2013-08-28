package de.gmino.issuemap.client.view.list;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.request.Geocoder;
import de.gmino.geobase.client.request.Geocoder.SearchLocationListener;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.view.list.ListView.ListViewItem;
import de.gmino.meva.shared.request.Requests;

public class Generation_List_Item extends Composite implements ListViewItem<DecentralizedGeneration> {

	private static Generation_List_ItemUiBinder uiBinder = GWT
			.create(Generation_List_ItemUiBinder.class);

	interface Generation_List_ItemUiBinder extends UiBinder<Widget, Generation_List_Item> {
	}


	private DecentralizedGeneration gen;

	private static Geocoder gc = new Geocoder();


	public Generation_List_Item(DecentralizedGeneration gen) {
		initWidget(uiBinder.createAndBindUi(this));
		setDataItem(gen);
	}

	long mapId;
	
	@UiField
	Label lbId;
	@UiField
	Label lbAddress;
	@UiField
	Label lbPower;
	@UiField
	Label lbVoltage;
	@UiField
	Label lbLocation;
	@UiField
	Label lbType;

	
	@UiHandler("btGeocode")
	public void onBtGeocodeClicked(ClickEvent e)
	{
		
		gc.searchLocationByAddress(gen.getAddress(), new SearchLocationListener() {

			@Override
			public void onLocationNotFound() {
				
			}
			
			@Override
			public void onLocationFound(LatLon location) {
				gen.setLocation(location);
				Requests.saveEntity(gen, null);
				setDataItem(gen);
			}

			@Override
			public void onError(String message) {
				Window.alert("Es ist ein Fehler bei der Straßensuche passiert: "
						+ message);
			}
		});
	}


	@Override
	public void setDataItem(DecentralizedGeneration item) {
		this.gen = item;
		lbId.setText(item.getId() + "");
		lbAddress.setText(item.getAddress().toReadableString(false));
		lbPower.setText(item.getPower() + " kW");
		lbVoltage.setText(item.getVoltage());
		lbLocation.setText(item.getLocation().toDecimalString());
		lbType.setText(item.getUnitType());
	}
}
