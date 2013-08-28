package de.gmino.issuemap.client.view.popup;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.ImageUrlLoader.ImageLoadListener;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Comment;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Photo;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.view.valueview.KeyValueView;
import de.gmino.issuemap.shared.domain.Markertype;
import de.gmino.meva.client.domain.KeyValueSet;
import de.gmino.meva.shared.Log;
import de.gmino.meva.shared.ValueWrapper;
import de.gmino.meva.shared.domain.KeyValueDef;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class Show_PopUp extends Composite {

	/**** Ui-Binder stuff ****/
	private static Detail_PopUpUiBinder uiBinder = GWT
			.create(Detail_PopUpUiBinder.class);
	
	interface Detail_PopUpUiBinder extends UiBinder<Widget, Show_PopUp> {
	}
	
	interface Style extends CssResource {
		String underline();
		String active();
	}
	
	@UiField
	Style style;

	/**** other fields ****/
	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy, 'um' HH:mm");

	private Map map;
	private OpenLayersSmartLayer smartLayer;
	private Poi mPoi;
	private boolean mainPhotoShown;
	private HashMap<Widget, String> displayValueSafe = new HashMap<Widget, String>();
	private TreeMap<String, KeyValueView> keyValueViews = new TreeMap<String, KeyValueView>();
	
	/**** Ui-Fields ****/
	@UiField
	Label lbTitle;
	
	@UiField
	FocusPanel tbResolved;
	@UiField
	FocusPanel tbClose;
	@UiField
	FocusPanel tbEdit;
	@UiField 
	Label lbRating;
	
	@UiField
	FocusPanel tbRatingDown2;
	@UiField
	FocusPanel tbRatingUp2;
	@UiField 
	Label lbRating2;

	@UiField
	Label lbTypeAndDate;
	@UiField
	VerticalPanel keyValuePanel;
	
	@UiField
	VerticalPanel parent;
	@UiField
	FlowPanel picturesPanel;
	@UiField
	VerticalPanel commentsPanel;
	@UiField
	Panel descriptionPanel;
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
	DeckPanel deckPanel;
	@UiField
	Panel tabDescription;
	@UiField
	Panel tabPhotos;
	@UiField
	Panel tabComments;
	
	@UiField 
	Button tabButtonDescription;
	@UiField 
	Button tabButtonPhotos;
	@UiField 
	Button tabButtonComments;
	
	@UiField
	Label lbRatingUpCount2;
	@UiField
	Label lbRatingDownCount2;
	
	@UiField
	Image imageMarkerIcon;
	@UiField
	Label lbNoPhotos;
	@UiField
	Label lbNoPhotosMain;
	@UiField
	Panel pnPhotoMain;
	@UiField
	Panel pnPhotoHeading;
	@UiField
	Panel pnRatingMain;
	@UiField
	Panel pnRatingHeading;
	@UiField
	Label lbPhotoMainHeader;
	@UiField
	Label lbNoComments;
	@UiField
	ScrollPanel spComments;
	@UiField
	Label lbMorePhotos;
	
	@UiField
	Label ratingCriteria;
	
	@UiField
	Label tabDivider2;
	@UiField
	Label tabDivider3;

	// TODO Merge functionality into a widget class which shows the thumbnail and manages its loading.
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

	@SuppressWarnings("unchecked")
	public Show_PopUp(Map map, OpenLayersSmartLayer smartLayer) {
		initWidget(uiBinder.createAndBindUi(this));
		this.map = map;
		this.smartLayer = smartLayer;
		
		enableOrDisableFeatures();
		commentTextBox.getElement().setAttribute("placeholder", "Bitte geben Sie einen Kommentar ein");
		setupPhotoUploadForm();
		activateTab(0);
	}

	public void setPoi(Poi poi) {
		this.mPoi = poi;

		lbTypeAndDate.setText(mPoi.getMarkertype().getMarkerName() + ", " + poi.getCreationTimestamp().relativeToNow().toReadableString(true,1));
		lbTypeAndDate.setTitle("Eintrag vom " + dtf.format(poi.getCreationTimestamp().toDate()));

		tbResolved.setStyleName(style.underline(), mPoi.isMarked());
		setRatingText();
		lbTitle.setText(mPoi.getTitle());
		lbTitle.setTitle(mPoi.getTitle());
		
		keyValuePanel.clear();
		keyValueViews.clear();
		for(ValueWrapper val : poi.getValues())
		{
			System.out.println("ValueWrapper: " + val.getDescription());
			KeyValueView keyValueView = new KeyValueView(val);
			keyValueViews.put(val.getName(), keyValueView);
			keyValuePanel.add(keyValueView);
		}
		
		updateButtonColorsAndLabels();
		loadAndShowComments();
		loadAndShowPhotos();

		GwtIconRenderer<? super Poi> renderer = this.smartLayer.getRendererForPoi(mPoi);
		String iconUrl = renderer.getIconUrl(mPoi);
		imageMarkerIcon.setUrl(iconUrl);
	}
	
	public void createNewPoi(final de.gmino.geobase.shared.domain.LatLon location)
	{
		final Markertype firstMarkertype = map.getHasMarkertypes().iterator().next();
		final KeyValueSet markerClass = (KeyValueSet) firstMarkertype.getMarkerClass();
		Requests.loadEntity(markerClass, new RequestListener<de.gmino.meva.shared.domain.KeyValueSet>() {
			@Override
			public void onNewResult(final de.gmino.meva.shared.domain.KeyValueSet set) {
				Requests.loadEntities(set.getDefs(), new RequestListener<KeyValueDef>() {
					@Override
					public void onFinished(Collection<KeyValueDef> defs) {
						Requests.getNewEntity(Poi.type, new RequestListener<Poi>() {
							public void onNewResult(Poi poi) {
								poi.setKeyvalueset(markerClass);
								poi.setLocation(location);
								poi.setMap_instance(map);
								poi.setCreationTimestamp(Timestamp.now());
								poi.setMarkertype(firstMarkertype);
								setPoi(poi);
								setEditMode(true);
							}
						});
					}
				});
			}
		});
	}

	private void enableOrDisableFeatures() {
		boolean showPhotoFeatures = map.isHas_fotos();
		showOrHideWidgets(showPhotoFeatures, tabButtonPhotos ,tabDivider2, pnPhotoMain, pnPhotoHeading);
		
		boolean showCommentFeatures = map.isHas_comments();
		showOrHideWidgets(showCommentFeatures, tabButtonComments, tabDivider3);
		
		boolean showRatingFeatures = map.isHas_ratings();
		showOrHideWidgets(showRatingFeatures, pnRatingMain, pnRatingHeading, lbRating);

		tbEdit.setVisible(map.isEdit());
		tbResolved.setVisible(map.isMark());
		
		if(!showRatingFeatures && !showPhotoFeatures)
			descriptionPanel.setWidth("475px");
		else
			descriptionPanel.setWidth("310px");
		
		ratingCriteria.setText(map.getRate_criteria());
		tbResolved.getElement().setAttribute("title", map.getMark_description());
	}
	
	private void showOrHideWidgets(boolean show, Widget... widgets)
	{
		if(show)
			for(Widget w : widgets)
			{
				com.google.gwt.dom.client.Style elementStyle = w.getElement().getStyle();
				String displayValue = elementStyle.getDisplay();
				if(!displayValue.equals("none"));
					displayValueSafe.put(w, displayValue);
				elementStyle.setDisplay(Display.NONE);
			}
		else
			for(Widget w : widgets)
			{
				com.google.gwt.dom.client.Style elementStyle = w.getElement().getStyle();
				String displayValue = displayValueSafe.get(w);
				if(displayValue == null || displayValue.equals("") || displayValue.equalsIgnoreCase("none"))
					elementStyle.setDisplay(Display.BLOCK);
				else
					elementStyle.setDisplay(Display.valueOf(displayValue));
			}
	}

	@SuppressWarnings("unchecked")
	private void loadAndShowComments() {
		final int commentCount = mPoi.getComments().size();
		if(commentCount == 0)
			commentsHeader.setText("Bisher keine Kommentare.");
		else
		{
			commentsHeader.setText(commentCount + " Kommentare (lade...)");
			Requests.loadEntities(mPoi.getComments(), new RequestListener<de.gmino.issuemap.shared.domain.Comment>() {
				@Override
				public void onFinished(Collection<de.gmino.issuemap.shared.domain.Comment> comments) {
					commentsHeader.setText(commentCount + " Kommentare:");
					for(de.gmino.issuemap.shared.domain.Comment comment : comments)
						showComment((Comment)comment);
				}
	
				@Override
				public void onError(String message, Throwable e) {
					commentsHeader.setText("Fehler beim Laden der Kommentare.");
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	private void loadAndShowPhotos() {
		final int photoCount = mPoi.getPhotos().size();
		if(photoCount == 0)
			photosHeader.setText("Bisher keine Fotos.");
		else
		{
			photosHeader.setText(photoCount + " Fotos (lade...)");
			Requests.loadEntities(IssuemapGwt.<Photo, de.gmino.issuemap.shared.domain.Photo>convertCollection(mPoi.getPhotos()), new RequestListener<Photo>() {
				@Override
				public void onFinished(Collection<Photo> photos) {
					photosHeader.setText(photoCount + " Fotos:");
					for(Photo photo : photos)
						showPhoto(photo);
				}

				@Override
				public void onError(String message, Throwable e) {
					photosHeader.setText("Fehler beim Laden der Fotos.");
				}
			});
		}
	}
	
	@UiHandler("tabButtonDescription")
	void onTabButtonDescriptionClick(ClickEvent e) {
		activateTab(0);
	}

	@UiHandler("tabButtonPhotos")
	void onTabButtonPhotosClick(ClickEvent e) {
		activateTab(1);
	}
		
	@UiHandler("tabButtonComments")
	void onTabButtonCommentsClick(ClickEvent e) {
		activateTab(2);
	}
		
	private void activateTab(int i) {
		deckPanel.showWidget(i);
		tabButtonDescription.setStyleName(style.underline(), i == 0);
		tabButtonPhotos		.setStyleName(style.underline(), i == 1);
		tabButtonComments	.setStyleName(style.underline(), i == 2);
	}
		
	// TODO Extract Upload form into its own widget class, providing a callback with a photo entity as return value.
	public void setupPhotoUploadForm()
	{
		form.setAction("/Upload/uploader");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		fileupload.setName("img");
		// Add an event handler to the form.

		form.addSubmitHandler(new SubmitHandler() {
			
			@Override
			public void onSubmit(SubmitEvent event) {
				System.out.println("Submitted form.");
			}
		});
		
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				System.out.println("Submit complete.");
				
				final String url = event.getResults().replace("<pre>", "").replace("</pre>", "").trim();
				
				if(url.startsWith("http://"))
				{
					Requests.getNewEntity(Photo.type, new RequestListener<Photo>() {
						@Override
						public void onNewResult(final Photo photo) {
							Requests.loadEntity(mPoi, new RequestListener<Poi>() {
								@Override
								public void onFinished(Collection<Poi> results) {
									photo.setUser("anonym");
									photo.setTimestamp(Timestamp.now());
									photo.setImage(new ImageUrl(url));
									mPoi.getPhotos().add(photo);
									Requests.saveEntity(photo, null);
									Requests.saveEntity(mPoi, null);
									showPhoto(photo);
									smartLayer.updatePoi(mPoi);
									updateIcon();
								}
							});
						}
					});
					
					Requests.saveEntity(mPoi, null);
				}
				else
					Window.alert("Beim Upload ist ein Fehler aufgetreten. Bitt versuchen Sie es erneut.");
					Log.log("Photo upload error. Instead of an url, we got this: " + url);
			}
		});
	}
	
	@UiHandler("tbClose")
	void onTbClose(ClickEvent e) {
		this.removeFromParent();
	}

	@UiHandler("tbEdit")
	void onTbEdit(ClickEvent e) {
		setEditMode(true);
	}

	public void setEditMode(boolean edit) {
		for(KeyValueView view : keyValueViews.values())
		{
			view.setEditMode(edit);
		}
	}

	@UiHandler("tbResolved")
	void onCheckbox(ClickEvent e) {
		mPoi.setMarked(!mPoi.isMarked());
		tbResolved.setStyleName(style.underline(), mPoi.isMarked());
		updateIcon();
	}

	private void updateIcon() {
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mPoi);
		String iconUrl = renderer.getIconUrl(mPoi);
		imageMarkerIcon.setUrl(iconUrl);
		Requests.saveEntity(mPoi, null);
		smartLayer.updatePoi(mPoi);
		IssuemapGwt.getInstance().loadIssuesToList();
	}

	@UiHandler("uploadButton")
	void onUpload(ClickEvent e) {
		if(fileupload.getFilename() != null && fileupload.getFilename().length() > 0)
		{
			System.out.println("Image File Name: " + fileupload.getFilename());
			form.submit();
		}
	}
	
	@UiHandler("commentTextBox")
	void onKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			sendComment();
		}
	}
	
	@UiHandler("commentButton")
	void onCommentButtonClick(ClickEvent e) {
		if(commentTextBox.getText().equals("")){}
		else {sendComment();
	}}

	private void sendComment() {
		commentTextBox.setEnabled(false);
		Requests.getNewEntity(Comment.type, new RequestListener<Comment>() {
			@Override
			public void onNewResult(final Comment comment) {
				Requests.loadEntity(mPoi, new RequestListener<Poi>() {
					@Override
					public void onFinished(Collection<Poi> results) {
						comment.setText(commentTextBox.getText());
						comment.setUser("anonym");
						comment.setTimestamp(Timestamp.now());
						mPoi.getComments().add(comment);
						Requests.saveEntity(comment, null);
						Requests.saveEntity(mPoi, null);
						showComment(comment);
						commentTextBox.setText("");
						commentTextBox.setEnabled(true);
						smartLayer.updatePoi(mPoi);
						int commentCount = mPoi.getComments().size();
						commentsHeader.setText(commentCount  + " Kommentare:");
						updateIcon();	
					}
				});
			}
		});
	}
	
	@UiHandler({"tbRatingUp2"})
	void onRateUp(ClickEvent e) {
		if(mPoi.vote == 1)
			mPoi.changeRating(0);
		else
			mPoi.changeRating(1);
		
		Requests.saveEntity(mPoi, null);
		updateButtonColorsAndLabels();
		updateIcon();
	}
		
	@UiHandler({"tbRatingDown2"})
	void onRateDown(ClickEvent e) {
		if(mPoi.vote == -1)
			mPoi.changeRating(0);
		else
			mPoi.changeRating(-1);
		
		Requests.saveEntity(mPoi, null);
		updateButtonColorsAndLabels();
		updateIcon();
	}
	
	private void updateButtonColorsAndLabels() {
		if (mPoi.vote >= 1)
			tbRatingUp2.setStyleName(style.active(), true);
		if (mPoi.vote >= 0)
			tbRatingDown2.setStyleName(style.active(), false);
		if (mPoi.vote <= -1)
			tbRatingDown2.setStyleName(style.active(), true);
		if (mPoi.vote <= 0)
			tbRatingUp2.setStyleName(style.active(), false);
		
		setRatingText();
	}

	private void setRatingText() {
		String ratingText = "" + (mPoi.getRating());
		if(mPoi.getRating() > 0)
			ratingText = "+" + ratingText;
		lbRating.setText(ratingText);
		lbRating2.setText(ratingText);
		int upVotes = (mPoi.getNumber_of_rating() + mPoi.getRating()) / 2;
		int downVotes = (mPoi.getNumber_of_rating() - mPoi.getRating()) / 2;
		lbRatingUpCount2.setText("" + upVotes);
		lbRatingDownCount2.setText("" + downVotes);
	}

	public void setText(String titleString, String descriptionString) {
		lbTitle.setText(titleString);
	}

	public void setBoarderColor(String color) {
		parent.getElement().getStyle().setBorderColor(color);
	}

	private void showComment(Comment comment) {
		VerticalPanel vp = new VerticalPanel();
		vp.getElement().getStyle().setPaddingBottom(5, Unit.PX);
		Label commentheader;
		if (comment.getUser().equals("anonym")) commentheader = new Label(comment.getTimestamp().relativeToNow().toReadableString(true, 2) + ".");
		else commentheader = new Label(comment.getTimestamp().relativeToNow().toReadableString(true, 2) + " von " + comment.getUser() + ".");
		commentheader.setTitle("Eingetragen am " + dtf.format(comment.getTimestamp().toDate()));
		commentheader.getElement().getStyle().setFontSize(10, Unit.PX);
		commentheader.getElement().getStyle().setMarginTop(3, Unit.PX);
		commentheader.getElement().getStyle().setMarginBottom(8, Unit.PX);
		commentheader.getElement().getStyle().setMarginLeft(8, Unit.PX);
		commentheader.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		Label commenttext = new Label(comment.getText());
		commenttext.getElement().getStyle().setLineHeight(16, Unit.PX);

		vp.add(commenttext);
		vp.add(commentheader);
		commentsPanel.add(vp);
		tabButtonComments.setText("Kommentare (" + mPoi.getComments().size() + ")");
		lbNoComments.setVisible(false);
		spComments.scrollToBottom();
	}
	
	private void showPhoto(Photo photo) {
		lbNoPhotos.setVisible(false);
		final String photoBaseUrl = photo.getImage().getUrl();
		Panel imagePanel = new SimplePanel();
		imagePanel.setWidth("100px");
		imagePanel.setHeight("100px");
		imagePanel.getElement().getStyle().setMargin(3, Unit.PX);
		imagePanel.getElement().getStyle().setFloat(com.google.gwt.dom.client.Style.Float.LEFT);
		
		Image image = new Image(photoBaseUrl+"&h=100&w=100");
		image.getElement().getStyle().setCursor(Cursor.POINTER);
		image.getElement().getStyle().setProperty("margin", "auto");
		imagePanel.add(image);
		picturesPanel.add(imagePanel);
		final int photoCount = mPoi.getPhotos().size();
		photosHeader.setText(photoCount + " Fotos:");
		tabButtonPhotos.setText("Fotos (" + photoCount + ")");
		if(photoCount == 1)
			lbMorePhotos.setText("");
		else
			lbMorePhotos.setText("("+(photoCount - 1)+" weitere)");
		image.addClickHandler(new ShowPhotoThingy(photoBaseUrl));
		if(!mainPhotoShown)
		{
			mainPhotoShown = true;
			lbNoPhotosMain.setVisible(false);
			pnPhotoMain.clear();
			Image mainImage = new Image(photoBaseUrl+"&h=110&w=150");
			mainImage.getElement().getStyle().setCursor(Cursor.POINTER);
			pnPhotoMain.add(mainImage);
			final ClickHandler photoClick = new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					activateTab(1);
				}
			};
			mainImage.addClickHandler(photoClick);
			lbMorePhotos.addClickHandler(photoClick);
		}	
	}
	
	@UiHandler("lbWhatsThis")
	public void onWhatsThisClicked(ClickEvent e)
	{
		final DecoratedPopupPanel decorated_panel = new DecoratedPopupPanel(true, true);
		final Label messageLabel = new Label("Sie können abstimmen, ob sie diese Meldung für wichtig (+1) oder unwichtig (-1) halten. Aus den abgebenen Stimmen berechnen wir eine Summe. Damit können andere schnell die wichtigsten Punkte finden. Meldungen, die überweigend als unwichtig bewertet werden, werden ausgeblendet um die Übersichtlichkeit der Karte beizubehalten");
		messageLabel.setWordWrap(true);
		messageLabel.setWidth("250px");
		decorated_panel.add(messageLabel);
		decorated_panel.setGlassEnabled(true);
		decorated_panel.setAnimationEnabled(true);
		decorated_panel.show();
		decorated_panel.center();
	}
}
