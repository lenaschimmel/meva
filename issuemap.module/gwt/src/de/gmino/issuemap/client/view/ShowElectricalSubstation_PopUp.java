package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.ImageUrlLoader.ImageLoadListener;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.ElectricalSubstation;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.poi.Marker_Wrapper;
import de.gmino.issuemap.client.resources.ImageResources;

public class ShowElectricalSubstation_PopUp extends Composite {

	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy hh:mm");
	ImageResources imageRes;

	private static Detail_PopUpUiBinder uiBinder = GWT
			.create(Detail_PopUpUiBinder.class);

	private static final class ShowPhotoThingy implements ClickHandler {
		private final String photoBaseUrl;
		private ImageUrlLoader loader;
		private String scaledUrl;
		private boolean loaded;

		private ShowPhotoThingy(String photoBaseUrl) {
			this.photoBaseUrl = photoBaseUrl;
		}

		@Override
		public void onClick(ClickEvent event) {
			if(loaded)
				showPopup();
			else
			{
				loader = ImageUrlLoader.getInstance();
				scaledUrl = photoBaseUrl+"&h=500";
				loader.loadImage(scaledUrl, new ImageLoadListener() {
					@Override
					public void onLoaded() {
						loaded = true;
						showPopup();
					}
				});
			}
		}
		
		private void showPopup() {
			Image popupImage = loader.getImageByUrl(scaledUrl);
			popupImage.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
			
			DecoratedPopupPanel popup = new DecoratedPopupPanel(true, true);
			popup.setGlassEnabled(true);
			
			popupImage.setHeight("500px");
			popup.add(popupImage);
			popup.show();
			popup.center();
		}
	}

	interface Detail_PopUpUiBinder extends UiBinder<Widget, ShowElectricalSubstation_PopUp> {
	}

	Map mapObject;
	OpenLayersSmartLayer smartLayer;
	ElectricalSubstation mSubstation;
	Marker_Wrapper mWrapper;

	@SuppressWarnings("unchecked")
	public ShowElectricalSubstation_PopUp(Map map, ElectricalSubstation substation,
			Marker_Wrapper marker_Wrapper, OpenLayersSmartLayer smartLayer) {
		imageRes = GWT.create(ImageResources.class);
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.smartLayer = smartLayer;
		this.mSubstation = substation;
		this.mWrapper = marker_Wrapper;
		setBoarderColor(map.getPrimary_color());
		
		type.setText("Umspannwerk");
		if (mSubstation.getAddress().getStreet().equals(""))
			adressPanel.getElement().removeFromParent();
		else
			adress.setText(mSubstation.getAddress().toReadableString(true));

		if (mSubstation.getTitle().equals("")) {
			labelTitle.setText("EE-Anlage (Name unbekannt)");
		} else {
			labelTitle.setText(mSubstation.getTitle());
		}
		
		if (mSubstation.getOperator().equals("")) {
			operatorPanel.getElement().removeFromParent();
		} else {
			operator.setText(mSubstation.getOperator());
		}
		
		if (mSubstation.getHigh_voltage()==0) {
			highVoltagePanel.getElement().removeFromParent();
		} else {
			highvoltage.setText(mSubstation.getHigh_voltage()+"kV");
		}
		
		if (mSubstation.getLow_voltage()==0) {
			lowVoltagePanel.getElement().removeFromParent();
		} else {
			lowvoltage.setText(mSubstation.getHigh_voltage()+"kV");
		}
		
		if (mSubstation.getFrequence()==0) {
			frequenzPanel.getElement().removeFromParent();
		} else {
			frequenz.setText(mSubstation.getFrequence()+"Hz");
		}
		
		if (mSubstation.getConsumption()==0) {
			consumptionPanel.getElement().removeFromParent();
		} else {
			consumption.setText(mSubstation.getConsumption()+"KW");
		}
				

		deckPanel.showWidget(0);

		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mSubstation);
		String iconUrl = renderer.getIconUrl(mSubstation);
		imageMarkerIcon.setUrl(iconUrl);
	}
	
	
	
	@UiField
	HorizontalPanel adressPanel;
	@UiField
	HorizontalPanel operatorPanel;
	@UiField
	HorizontalPanel highVoltagePanel;
	@UiField
	HorizontalPanel lowVoltagePanel;
	@UiField
	HorizontalPanel frequenzPanel;
	@UiField
	HorizontalPanel consumptionPanel;


	 
	@UiField
	Label labelTitle;
	@UiField
	Label type;
	@UiField
	Label adress;
	@UiField
	Label operator;
	@UiField
	Label lowvoltage;
	@UiField
	Label highvoltage;
	@UiField
	Label frequenz;
	@UiField
	Label consumption;
	@UiField
	HTML description;
	@UiField
	Image close;
	@UiField
	VerticalPanel parent;
	@UiField
	DeckPanel deckPanel;
	@UiField
	ScrollPanel tabDescription;

	
	@UiField
	Image imageMarkerIcon;
	

	

	@UiHandler("close")
	void onClose(ClickEvent e) {
		this.removeFromParent();
	}

	public void setText(String titleString, String descriptionString) {
		description.setText(descriptionString);
		labelTitle.setText(titleString);
	}

	public void setBoarderColor(String color) {
		parent.getElement().getStyle().setBorderColor(color);
	}
}
