package de.gmino.issuemap.client.view;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Comment;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Photo;
import de.gmino.issuemap.client.poi.Marker_Wrapper;
import de.gmino.issuemap.client.resources.ImageResources;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class ShowIssue_PopUp extends Composite {

	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");
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

	interface Detail_PopUpUiBinder extends UiBinder<Widget, ShowIssue_PopUp> {
	}

	Map mapObject;
	OpenLayersSmartLayer smartLayer;
	Issue mIssue;
	Marker_Wrapper mWrapper;

	@SuppressWarnings("unchecked")
	public ShowIssue_PopUp(Map map, Issue issue, Marker_Wrapper marker_Wrapper, OpenLayersSmartLayer smartLayer) {
		imageRes = GWT.create(ImageResources.class);
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject = map;
		this.smartLayer = smartLayer;
		this.mIssue = issue;
		this.mWrapper = marker_Wrapper;
//		setBoarderColor(map.getColor());

		date.setText(", vom " + dtf.format(mIssue.getCreationTimestamp().toDate()));
		type.setText(mIssue.getMarkertype().getMarkerName());
		resolved.setValue(mIssue.isResolved());
		setRatingText();
		labelTitle.setText(mIssue.getTitle());
		description.setHTML(new SafeHtmlBuilder().appendEscapedLines(mIssue.getDescription()).toSafeHtml());
		
		//mIssue.vote=0;
		updateButtonColorsAndLabels();
		
		loadAndShowComments();
		loadAndShowPhotos();
		setupForm();
		deckPanel.showWidget(0);
		
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mIssue);
		String iconUrl = renderer.getIconUrl(mIssue);
		imageMarkerIcon.setUrl(iconUrl);
	}

	@SuppressWarnings("unchecked")
	private void loadAndShowComments() {
		final int commentCount = mIssue.getComments().size();
		if(commentCount == 0)
			commentsHeader.setText("Bisher keine Kommentare.");
		else
		{
			commentsHeader.setText(commentCount + " Kommentare (lade...)");
			Requests.loadEntities(mIssue.getComments(), new RequestListener<Comment>() {
				@Override
				public void onFinished(Collection<Comment> comments) {
					commentsHeader.setText(commentCount + " Kommentare:");
					for(Comment comment : comments)
						showComment(comment);
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
			Requests.loadEntities(mIssue.getPhotos(), new RequestListener<Photo>() {
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
	PushButton delete;
	@UiField
	PushButton edit;
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
	
	@UiHandler("topCellPhotos")
	void onTopCellPhotosClick(ClickEvent e) {
		deckPanel.showWidget(1);
	}
	
	@UiHandler("tabButtonComments")
	void onTabButtonCommentsClick(ClickEvent e) {
		deckPanel.showWidget(2);
	}
	
	@UiHandler("topCellComments")
	void onTopCellCommentsClick(ClickEvent e) {
		deckPanel.showWidget(2);
	}
	
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
				
				Requests.getNewEntity(Photo.type, new RequestListener<Photo>() {
					@Override
					public void onNewResult(Photo photo) {
						photo.setUser("anonym");
						photo.setTimestamp(Timestamp.now());
						photo.setImage(new ImageUrl(url));
						mIssue.getPhotos().add(photo);
						Requests.saveEntity(photo, null);
						Requests.saveEntity(mIssue, null);
						showPhoto(photo);
						smartLayer.updatePoi(mIssue);
					}
				});
				
				Requests.saveEntity(mIssue, null);
			//	ShowIssue_PopUp.this.removeFromParent();
			}
		});
	}
	
	@UiHandler("close")
	void onClose(ClickEvent e) {
		this.removeFromParent();
	}

	@UiHandler("edit")
	void onEdit(ClickEvent e) {
		this.removeFromParent();
		CreateIssue_PopUp cip = new CreateIssue_PopUp(mapObject, mIssue, smartLayer);
		cip.setBoarderColor(mapObject.getPrimary_color());
		mWrapper.add(cip);

	}

	@UiHandler("delete")
	void onDelete(ClickEvent e) {
		mIssue.setDeleted(true);
		Requests.saveEntity(mIssue, null);
		this.removeFromParent();
		IssuemapGwt.getInstance().deleteMarker(mIssue);
		IssuemapGwt.getInstance().setCounter();

	}

	@UiHandler("resolved")
	void onCheckbox(ClickEvent e) {
		mIssue.setResolved(resolved.getValue());
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mIssue);
		String iconUrl = renderer.getIconUrl(mIssue);
		imageMarkerIcon.setUrl(iconUrl);
		Requests.saveEntity(mIssue, null);
		smartLayer.updatePoi(mIssue);
	}

	@UiHandler("rate_up")
	void onRateUp(ClickEvent e) {
		if(mIssue.vote == 1)
			mIssue.changeRating(0);
		else
			mIssue.changeRating(1);
		
		Requests.saveEntity(mIssue, null);
		updateButtonColorsAndLabels();
	}
	
	@UiHandler("uploadButton")
	void onUpload(ClickEvent e) {
		if(fileupload.getFilename() != null && fileupload.getFilename().length() > 0)
		{
			System.out.println("Image File Name: " + fileupload.getFilename());
			form.submit();
		}
	}
	
	@UiHandler("commentButton")
	void onCommentButtonClick(ClickEvent e) {
		commentTextBox.setEnabled(false);
		Requests.getNewEntity(Comment.type, new RequestListener<Comment>() {
			@Override
			public void onNewResult(Comment comment) {
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
			}
		});
	}
	
	@UiHandler("rate_down")
	void onRateDown(ClickEvent e) {
		if(mIssue.vote == -1)
			mIssue.changeRating(0);
		else
			mIssue.changeRating(-1);
		
		Requests.saveEntity(mIssue, null);
		updateButtonColorsAndLabels();
	}

	private void updateButtonColorsAndLabels() {
		if (mIssue.vote >= 1)
			rate_up.setResource(imageRes.go_up());
		if (mIssue.vote >= 0)
			rate_down.setResource(imageRes.go_down_grey());
		if (mIssue.vote <= -1)
			rate_down.setResource(imageRes.go_down());
		if (mIssue.vote <= 0)
			rate_up.setResource(imageRes.go_up_grey());
		setRatingText();
	}

	private void setRatingText() {
		rating.setText("" + (mIssue.getRating()));
		int upVotes = (mIssue.getNumber_of_rating() + mIssue.getRating()) / 2;
		int downVotes = (mIssue.getNumber_of_rating() - mIssue.getRating()) / 2;
		panelRating.setTitle(upVotes + " positive Bewertungen, " + downVotes + " negative.");
	}

	public void setText(String titleString, String descriptionString) {
		description.setText(descriptionString);
		labelTitle.setText(titleString);
	}

	public void setBoarderColor(String color) {
		parent.getElement().getStyle().setBorderColor(color);
	}

	private void showComment(Comment comment) {
		VerticalPanel vp = new VerticalPanel();
		vp.getElement().getStyle().setPaddingBottom(5, Unit.PX);
		Label commentheader = new Label("Am " + dtf.format(comment.getTimestamp().toDate()) + " von " + comment.getUser() + ":");
		commentheader.getElement().getStyle().setFontSize(10, Unit.PX);
		commentheader.getElement().getStyle().setMarginBottom(-1, Unit.PX);
		vp.add(commentheader);
		Label commenttext = new Label(comment.getText());
		commenttext.getElement().getStyle().setLineHeight(16, Unit.PX);
		commenttext.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		vp.add(commenttext);
		commentsPanel.add(vp);
		labelCommentCount.setText(mIssue.getComments().size()+"");
	}
	
	private void showPhoto(Photo photo) {
		final String photoBaseUrl = photo.getImage().getUrl();
		Image image = new Image(photoBaseUrl+"&h=100");
		image.getElement().getStyle().setCursor(Cursor.POINTER);
		picturesPanel.add(image);
		photosHeader.setText(mIssue.getPhotos().size() + " Fotos:");
		labelPhotoCount.setText(mIssue.getPhotos().size()+"");
		image.addClickHandler(new ShowPhotoThingy(photoBaseUrl));
	}
	
}
