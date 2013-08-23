package de.gmino.issuemap.client.view;

import java.util.Collection;

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
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.ImageUrlLoader.ImageLoadListener;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Comment;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Photo;
import de.gmino.issuemap.client.poi.Marker_Wrapper;
import de.gmino.issuemap.client.resources.ImageResources;
import de.gmino.meva.shared.Log;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class Show_PopUp extends Composite {

	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy, 'um' HH:mm");
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

	interface Detail_PopUpUiBinder extends UiBinder<Widget, Show_PopUp> {
	}
	
	interface Style extends CssResource {
		String underline();
		String active();
	}
	
	@UiField
	Style style;

	Map mapObject;
	OpenLayersSmartLayer smartLayer;
	Issue mIssue;
	Marker_Wrapper mWrapper;
	boolean mainPhotoShown;

	@SuppressWarnings("unchecked")
	public Show_PopUp(Map map, Issue issue, Marker_Wrapper marker_Wrapper, OpenLayersSmartLayer smartLayer) {
		imageRes = GWT.create(ImageResources.class);
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.smartLayer = smartLayer;
		this.mIssue = issue;
		this.mWrapper = marker_Wrapper;


//		tbRatingUp.setVisible(false);
//		tbRatingDown.setVisible(false);
		
		lbTypeAndDate.setText(mIssue.getMarkertype().getMarkerName() + ", " + issue.getCreationTimestamp().relativeToNow().toReadableString(true,1));
		lbTypeAndDate.setTitle("Eintrag vom " + dtf.format(issue.getCreationTimestamp().toDate()));

		tbResolved.setStyleName(style.underline(), mIssue.isResolved());
		setRatingText();
		lbTitle.setText(mIssue.getTitle());
		lbTitle.setTitle(mIssue.getTitle());
		
		if(mIssue.getMap_instance().isHas_fotos()==false){
			tabButtonPhotos.removeFromParent();
			tabDivider2.removeFromParent();
			pnPhotoMain.removeFromParent();
			pnPhotoHeading.removeFromParent();
			}
		if(mIssue.getMap_instance().isHas_comments()==false){
			tabButtonComments.removeFromParent();
			tabDivider3.removeFromParent();
		}
		
		if(mIssue.getMap_instance().isHas_ratings()==false){
			pnRatingMain.removeFromParent();
			pnRatingHeading.removeFromParent();
			
		}
			lbRating.setVisible(mIssue.getMap_instance().isHas_ratings());
			tbEdit.setVisible(mIssue.getMap_instance().isEdit());
			tbResolved.setVisible(mIssue.getMap_instance().isMark());
		
		if(mIssue.getMap_instance().isHas_ratings()==false && mIssue.getMap_instance().isHas_fotos()==false)
			descriptionPanel.setWidth("475px");
		
		ratingCriteria.setText(mIssue.getMap_instance().getRate_criteria());
		tbResolved.getElement().setAttribute("title", mIssue.getMap_instance().getMark_description());
		
		
		addKeyValue("Key", "Value", "Description");
		description.setHTML(new SafeHtmlBuilder().appendEscapedLines(mIssue.getDescription()).toSafeHtml());
		commentTextBox.getElement().setAttribute("placeholder", "Bitte geben Sie einen Kommentar ein");
		
		//mIssue.vote=0;
		updateButtonColorsAndLabels();
		
		loadAndShowComments();
		loadAndShowPhotos();
		setupForm();
		deckPanel.showWidget(0);
		
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mIssue);
		String iconUrl = renderer.getIconUrl(mIssue);
		imageMarkerIcon.setUrl(iconUrl);
		activateTab(0);
	}

	@SuppressWarnings("unchecked")
	private void loadAndShowComments() {
		final int commentCount = mIssue.getComments().size();
		if(commentCount == 0)
			commentsHeader.setText("Bisher keine Kommentare.");
		else
		{
			commentsHeader.setText(commentCount + " Kommentare (lade...)");
			Requests.loadEntities(mIssue.getComments(), new RequestListener<de.gmino.issuemap.shared.domain.Comment>() {
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
		final int photoCount = mIssue.getPhotos().size();
		if(photoCount == 0)
			photosHeader.setText("Bisher keine Fotos.");
		else
		{
			photosHeader.setText(photoCount + " Fotos (lade...)");
			Requests.loadEntities(IssuemapGwt.<Photo, de.gmino.issuemap.shared.domain.Photo>convertCollection(mIssue.getPhotos()), new RequestListener<Photo>() {
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

	@UiField
	Label lbTitle;
	
	@UiField
	FocusPanel tbResolved;
	@UiField
	FocusPanel tbClose;
	@UiField
	FocusPanel tbEdit;
//	@UiField
//	FocusPanel tbRatingDown;
//	@UiField
//	FocusPanel tbRatingUp;
	@UiField 
	Label lbRating;
	

	@UiField
	FocusPanel tbRatingDown2;
	@UiField
	FocusPanel tbRatingUp2;
	@UiField 
	Label lbRating2;

	
//	@UiField
//	Label rating;
//	@UiField
//	Image rate_up;
//	@UiField
//	Image rate_down;
	@UiField
	Label lbTypeAndDate;
	@UiField
	VerticalPanel keyValuePanel;
	@UiField
	HTML description;

	
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
//	@UiField
//	HorizontalPanel panelRating;
	
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
	
//	@UiField
//	Label lbRatingUpCount;
	@UiField
	Label lbRatingUpCount2;
//	@UiField
//	Label lbRatingDownCount;
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
	
//	@UiHandler("tbRating")
//	void onTbRatingClick(ClickEvent e) {
//		boolean visible = !tbRatingUp.isVisible();
//		tbRatingUp.setVisible(visible);
//		tbRatingDown.setVisible(visible);
//	}
		
	public void setupForm()
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
							Requests.loadEntity(mIssue, new RequestListener<Issue>() {
								@Override
								public void onFinished(Collection<Issue> results) {
									photo.setUser("anonym");
									photo.setTimestamp(Timestamp.now());
									photo.setImage(new ImageUrl(url));
									mIssue.getPhotos().add(photo);
									Requests.saveEntity(photo, null);
									Requests.saveEntity(mIssue, null);
									showPhoto(photo);
									smartLayer.updatePoi(mIssue);
									updateIcon();
								}
							});
						}
					});
					
					Requests.saveEntity(mIssue, null);
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
		this.removeFromParent();
		CreateIssue_PopUp cip = new CreateIssue_PopUp(mapObject, mIssue, smartLayer);
		mWrapper.add(cip);

	}

//	@UiHandler("delete")
//	void onDelete(ClickEvent e) {
//		mIssue.setDeleted(true);
//		Requests.saveEntity(mIssue, null);
//		this.removeFromParent();
//		IssuemapGwt.getInstance().deleteMarker(mIssue);
//		IssuemapGwt.getInstance().setCounter();
//
//	}

	@UiHandler("tbResolved")
	void onCheckbox(ClickEvent e) {
		mIssue.setResolved(!mIssue.isResolved());
		tbResolved.setStyleName(style.underline(), mIssue.isResolved());
		updateIcon();
	}

	private void updateIcon() {
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mIssue);
		String iconUrl = renderer.getIconUrl(mIssue);
		imageMarkerIcon.setUrl(iconUrl);
		Requests.saveEntity(mIssue, null);
		smartLayer.updatePoi(mIssue);
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
				Requests.loadEntity(mIssue, new RequestListener<Issue>() {
					@Override
					public void onFinished(Collection<Issue> results) {
						comment.setText(commentTextBox.getText());
						comment.setUser("anonym");
						comment.setTimestamp(Timestamp.now());
						mIssue.getComments().add(comment);
						Requests.saveEntity(comment, null);
						Requests.saveEntity(mIssue, null);
						showComment(comment);
						commentTextBox.setText("");
						commentTextBox.setEnabled(true);
						smartLayer.updatePoi(mIssue);
						int commentCount = mIssue.getComments().size();
						commentsHeader.setText(commentCount  + " Kommentare:");
						updateIcon();	
					}
				});
				
				
			}
		});
	}
	
	@UiHandler({"tbRatingUp2"})
	void onRateUp(ClickEvent e) {
		if(mIssue.vote == 1)
			mIssue.changeRating(0);
		else
			mIssue.changeRating(1);
		
		Requests.saveEntity(mIssue, null);
		updateButtonColorsAndLabels();
		updateIcon();
	}
	
	
	@UiHandler({"tbRatingDown2"})
	void onRateDown(ClickEvent e) {
		if(mIssue.vote == -1)
			mIssue.changeRating(0);
		else
			mIssue.changeRating(-1);
		
		Requests.saveEntity(mIssue, null);
		updateButtonColorsAndLabels();
		updateIcon();
	}

	
	private void updateButtonColorsAndLabels() {
		if (mIssue.vote >= 1)
		{
//			tbRatingUp.setStyleName(style.underline(), true);
			tbRatingUp2.setStyleName(style.active(), true);
		}
		if (mIssue.vote >= 0)
		{
//			tbRatingDown.setStyleName(style.underline(), false);
			tbRatingDown2.setStyleName(style.active(), false);
		}
		if (mIssue.vote <= -1)
		{
//			tbRatingDown.setStyleName(style.underline(), true);
			tbRatingDown2.setStyleName(style.active(), true);
		}
		if (mIssue.vote <= 0)
		{
//			tbRatingUp.setStyleName(style.underline(), false);
			tbRatingUp2.setStyleName(style.active(), false);
		}
		
//		if (mIssue.vote >= 1)
//				rate_up.setResource(imageRes.go_up());
//		if (mIssue.vote >= 0)
//			rate_down.setResource(imageRes.go_down_grey());
//		if (mIssue.vote <= -1)
//			rate_down.setResource(imageRes.go_down());
//		if (mIssue.vote <= 0)
//			rate_up.setResource(imageRes.go_up_grey());
		setRatingText();
	}

	private void setRatingText() {
		String ratingText = "" + (mIssue.getRating());
		if(mIssue.getRating() > 0)
			ratingText = "+" + ratingText;
		lbRating.setText(ratingText);
		lbRating2.setText(ratingText);
		int upVotes = (mIssue.getNumber_of_rating() + mIssue.getRating()) / 2;
		int downVotes = (mIssue.getNumber_of_rating() - mIssue.getRating()) / 2;
//		lbRatingUpCount.setText("" + upVotes);
		lbRatingUpCount2.setText("" + upVotes);
//		lbRatingDownCount.setText("" + downVotes);
		lbRatingDownCount2.setText("" + downVotes);
	}

	public void setText(String titleString, String descriptionString) {
		description.setText(descriptionString);
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
		tabButtonComments.setText("Kommentare (" + mIssue.getComments().size() + ")");
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
		final int photoCount = mIssue.getPhotos().size();
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
	
	public void addKeyValue(String key, String value, String description){
		keyValuePanel.add(new String_KeyValueView(key, value, description));
		
	}
}
