package de.gmino.issuemap.client.view;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Route;
import de.gmino.issuemap.client.poi.Marker_Wrapper;
import de.gmino.issuemap.client.resources.ImageResources;
import de.gmino.meva.shared.request.RequestListener;

public class ShowRoute_PopUp extends Composite {

	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy hh:mm");
	ImageResources imageRes;

	private static Detail_PopUpUiBinder uiBinder = GWT
			.create(Detail_PopUpUiBinder.class);

	private static final class ShowPhotoThingy extends RequestListener<Void> implements ClickHandler {
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
				loader.addUrl(scaledUrl);
				loader.setOnLoadListener(this);
			}
		}
		
		@Override
		public void onFinished(Collection<Void> results) {
			loaded = true;
			showPopup();
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

	interface Detail_PopUpUiBinder extends UiBinder<Widget, ShowRoute_PopUp> {
	}

	Map mapObject;
	OpenLayersSmartLayer smartLayer;
	Route mRoute;
	Marker_Wrapper mWrapper;

	@SuppressWarnings("unchecked")
	public ShowRoute_PopUp(Map map, Route route, Marker_Wrapper marker_Wrapper, OpenLayersSmartLayer smartLayer) {
		imageRes = GWT.create(ImageResources.class);
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.smartLayer = smartLayer;
		this.mRoute = route;
		this.mWrapper = marker_Wrapper;
		setBoarderColor(map.getColor());

		type.setText("Fahrradreparaturmöglichkeit");
		
		labelTitle.setText(mRoute.getTitle());
		description.setHTML(new SafeHtmlBuilder().appendEscapedLines(mRoute.getDescription()).toSafeHtml());
		
		deckPanel.showWidget(0);
		
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mRoute);
		String iconUrl = renderer.getIconUrl(mRoute);
		imageMarkerIcon.setUrl(iconUrl);
	}

	@UiField
	Label labelTitle;
	@UiField
	CheckBox resolved;
	@UiField
	Label rating;
	@UiField
	Image rate_up;
	@UiField
	Image rate_down;
	@UiField
	Label date;
	@UiField
	Label type;
	@UiField
	HTML description;
	@UiField
	Image close;
	@UiField
	Image delete;
	@UiField
	Image edit;
	@UiField
	VerticalPanel parent;
	@UiField
	FlowPanel picturesPanel;
	@UiField
	VerticalPanel commentsPanel;
	@UiField
	Label commentsHeader;
	@UiField
	Label photosHeader;
	@UiField 
	TextBox commentTextBox;
	@UiField
	Button commentButton;
	@UiField
	FileUpload fileupload;
	@UiField
	FormPanel form;
	@UiField
	HorizontalPanel panelRating;
	
	@UiField
	DeckPanel deckPanel;
	@UiField
	ScrollPanel tabDescription;
	@UiField
	ScrollPanel tabPhotos;
	@UiField
	ScrollPanel tabComments;
	
	@UiField
	Label labelPhotoCount;
	@UiField
	Label labelCommentCount;
	
	@UiField
	Image imageMarkerIcon;
	
	@UiHandler("tabButtonDescription")
	void onTabButtonDescriptionClick(ClickEvent e) {
		deckPanel.showWidget(0);
	}
	
	@UiHandler("tabButtonPhotos")
	void onTabButtonPhotosClick(ClickEvent e) {
		deckPanel.showWidget(1);
	}
	
	@UiHandler("tabButtonComments")
	void onTabButtonCommentsClick(ClickEvent e) {
		deckPanel.showWidget(2);
	}
	

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