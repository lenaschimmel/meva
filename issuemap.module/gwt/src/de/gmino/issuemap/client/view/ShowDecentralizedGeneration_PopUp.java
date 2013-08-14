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
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.poi.Marker_Wrapper;
import de.gmino.issuemap.client.resources.ImageResources;

public class ShowDecentralizedGeneration_PopUp extends Composite {

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

	interface Detail_PopUpUiBinder extends UiBinder<Widget, ShowDecentralizedGeneration_PopUp> {
	}

	Map mapObject;
	OpenLayersSmartLayer smartLayer;
	DecentralizedGeneration mGen;
	Marker_Wrapper mWrapper;

	@SuppressWarnings("unchecked")
	public ShowDecentralizedGeneration_PopUp(Map map, DecentralizedGeneration gen,
			Marker_Wrapper marker_Wrapper, OpenLayersSmartLayer smartLayer) {
		imageRes = GWT.create(ImageResources.class);
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.smartLayer = smartLayer;
		this.mGen = gen;
		this.mWrapper = marker_Wrapper;
		setBoarderColor(map.getPrimary_color());

		type.setText(mGen.getDescription());
		if (mGen.getAddress().getStreet().equals(""))
			adressPanel.getElement().removeFromParent();
		else
			adress.setText(mGen.getAddress().toReadableString(true));
		
		if (mGen.getTitle().equals("")) {
			labelTitle.setText("EE-Anlage (Name unbekannt)");
		} else {
			labelTitle.setText(mGen.getTitle());
		}
		description.setHTML(new SafeHtmlBuilder().appendEscapedLines(
				mGen.getDescription()).toSafeHtml());
		power.setText(mGen.getPower()+"kW");

		deckPanel.showWidget(0);

		GwtIconRenderer<? super Poi> renderer = smartLayer
				.getRendererForPoi(mGen);
		String iconUrl = renderer.getIconUrl(mGen);
		imageMarkerIcon.setUrl(iconUrl);
	}
	
	
	
	@UiField
	HorizontalPanel adressPanel;

	@UiField
	HorizontalPanel powerPanel;

	
	@UiField
	Label adress;
	@UiField
	Label power;
	@UiField
	Label labelTitle;
	@UiField
	Label type;
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
