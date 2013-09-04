package de.gmino.issuemap.client.view.popup;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Comment;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Photo;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.view.ThumbnailView;
import de.gmino.issuemap.client.view.PhotoUploadForm;
import de.gmino.issuemap.client.view.PhotoUploadForm.PhotoUploadListener;
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
	private boolean newIssue;
	
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
	SimplePanel uploadPlaceholder;
	
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
	@UiField
	DeckPanel dpTtitleOrTextBox;
	@UiField
	DeckPanel dpTabsOrDropdown;
	@UiField
	TextBox tbTitle;
	@UiField
	FocusPanel btDelete;

    @UiField
    ListBox lbMarkertype;

	
	private boolean isInEditMode;

	private long markertypeIdBeforeEdit;

	@SuppressWarnings("unchecked")
	public Show_PopUp(Map map, OpenLayersSmartLayer smartLayer) {
		initWidget(uiBinder.createAndBindUi(this));
		this.map = map;
		this.smartLayer = smartLayer;
		
		enableOrDisableFeatures();
		commentTextBox.getElement().setAttribute("placeholder", "Bitte geben Sie einen Kommentar ein");
		tbTitle.getElement().setAttribute("placeholder", "Name des Eintrags");
		
		PhotoUploadListener photoUploadListener = new PhotoUploadListener() {
			
			@Override
			public void photoUploaded(final Photo photo) {
				Requests.loadEntity(mPoi, new RequestListener<Poi>() {
					@Override
					public void onFinished(Collection<Poi> results) {

						mPoi.getPhotos().add(photo);
						Requests.saveEntity(photo, null);
						Requests.saveEntity(mPoi, null);
						showPhoto(photo);
						Show_PopUp.this.smartLayer.updatePoi(mPoi);
						updateIcon();
					}
				});
			}
		};
		uploadPlaceholder.add(new PhotoUploadForm(photoUploadListener));
		
		activateTab(0);
		
		for (de.gmino.issuemap.shared.domain.Markertype mt : map.getHasMarkertypes())
			lbMarkertype.addItem(mt.getMarkerName(), mt.getId() + "");
		
		setEditMode(false, true);
	}

	public void setPoi(Poi poi) {
		this.mPoi = poi;

		setValuesFromPoi();
		
		createAndFillKeyValueViews(poi);
	}

	private void setValuesFromPoi() {
		lbTypeAndDate.setText(mPoi.getMarkertype().getMarkerName() + ", " + mPoi.getCreationTimestamp().relativeToNow().toReadableString(true,1));
		lbTypeAndDate.setTitle("Eintrag vom " + dtf.format(mPoi.getCreationTimestamp().toDate()));
		
		tbResolved.setStyleName(style.underline(), mPoi.isMarked());
		if (mPoi.isMarked()) tbResolved.getElement().getStyle().setBorderColor(map.getResolved_color());
		setRatingText();
		lbTitle.setText(mPoi.getTitle());
		lbTitle.setTitle(mPoi.getTitle());
		tbTitle.setText(mPoi.getTitle());
			
		updateButtonColorsAndLabels();
		loadAndShowComments();
		loadAndShowPhotos();

		GwtIconRenderer<? super Poi> renderer = this.smartLayer.getRendererForPoi(mPoi);
		String iconUrl = renderer.getIconUrl(mPoi);
		imageMarkerIcon.setUrl(iconUrl);
		
		selectMarkertypeInListbox();
	}

	private void createAndFillKeyValueViews(Poi poi) {
		keyValuePanel.clear();
		keyValueViews.clear();
		for(ValueWrapper val : poi.getValues())
		{
			System.out.println("ValueWrapper: " + val.getDescription());
			KeyValueView keyValueView = KeyValueView.getKeyValueView(val);
			keyValueViews.put(val.getName(), keyValueView);
			keyValuePanel.add(keyValueView);
		}
	}

	private void selectMarkertypeInListbox() {
		String markertypeId = mPoi.getMarkertypeId() + "";
		for(int i = 0; i < lbMarkertype.getItemCount(); i++)
			if(lbMarkertype.getValue(i).equals(markertypeId))
			{
				lbMarkertype.setSelectedIndex(i);
				break;
			}
	}
	
	@UiHandler("lbMarkertype")
	void onLbMarkertypeChange(ChangeEvent e)
	{
		long markertypeId = Long.parseLong(lbMarkertype.getValue(lbMarkertype.getSelectedIndex()));
		Markertype markertype = (Markertype) Markertype.getById(markertypeId);
		mPoi.setMarkertype(markertype);
		updateIcon();
	}
	
	private void setIssueValuesFromMask(){
		long markertypeId = Long.parseLong(lbMarkertype.getValue(lbMarkertype.getSelectedIndex()));
		Markertype markertype = (Markertype) Markertype.getById(markertypeId);
		mPoi.setTitle(tbTitle.getText());
		mPoi.setMarkertype(markertype);
		updateIcon();
		
		for(KeyValueView view : keyValueViews.values())
			view.saveValue();
		//mPoi.setDescription(description.getText());
	}
	

	public void createNewPoi(final de.gmino.geobase.shared.domain.LatLon location)
	{
		newIssue = true;
		setEditMode(true, false);
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
								setEditMode(true, true);
							}
						});
					}
				});
			}
		});
	}

	private void enableOrDisableFeatures() {
		boolean showPhotoFeatures = map.isHas_fotos() && !isInEditMode;
		showOrHideWidgets(showPhotoFeatures, tabButtonPhotos ,tabDivider2, pnPhotoMain, pnPhotoHeading);
		
		boolean showCommentFeatures = map.isHas_comments();
		showOrHideWidgets(showCommentFeatures, tabButtonComments, tabDivider3);
		
		boolean showRatingFeatures = map.isHas_ratings() && !isInEditMode;
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
		if(!show)
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
					elementStyle.setDisplay(Display.valueOf(displayValue.toUpperCase()));
			}
	}

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
		if(i==0) tabButtonDescription.getElement().getStyle().setBorderColor(map.getPrimary_color());
		else tabButtonDescription.getElement().getStyle().setBorderColor("transparent");
		tabButtonPhotos		.setStyleName(style.underline(), i == 1);
		if(i==1) tabButtonPhotos.getElement().getStyle().setBorderColor(map.getPrimary_color());
		else tabButtonPhotos.getElement().getStyle().setBorderColor("transparent");
		tabButtonComments	.setStyleName(style.underline(), i == 2);
		if(i==2) tabButtonComments.getElement().getStyle().setBorderColor(map.getPrimary_color());
		else tabButtonComments.getElement().getStyle().setBorderColor("transparent");
	}
		
	
	
	@UiHandler("tbClose")
	void onTbCloseClicked(ClickEvent e) {
		this.removeFromParent();
	}

	@UiHandler("tbEdit")
	void onTbEditClicked(ClickEvent e) {

		setEditMode(!isInEditMode, true);
	}

	public void setEditMode(boolean edit, boolean enabled) {
		isInEditMode = edit;
		
		enableOrDisableFeatures();
		
		for(KeyValueView view : keyValueViews.values())
			view.setEditMode(edit);
		
		dpTabsOrDropdown.showWidget(edit ? 1 : 0);
		dpTtitleOrTextBox.showWidget(edit ? 1 : 0);
		tbTitle.setEnabled(enabled);
		lbMarkertype.setEnabled(enabled);
		showOrHideWidgets(!newIssue && map.isDelete(), btDelete);
		
		if(edit)
		{
			if(mPoi != null)
				markertypeIdBeforeEdit = mPoi.getMarkertypeId();
			activateTab(0);
		}
		else if(mPoi != null)
		{
			selectMarkertypeInListbox();
		}
	}

	@UiHandler("tbResolved")
	void onTbResolvedClicked(ClickEvent e) {
	
		mPoi.setMarked(!mPoi.isMarked());
		tbResolved.setStyleName(style.underline(), mPoi.isMarked());

		if (mPoi.isMarked())
			tbResolved.getElement().getStyle().setBorderColor(map.getResolved_color());
		else
			tbResolved.getElement().getStyle().setBorderColor("transparent");

		updateIcon();
		Requests.saveEntity(mPoi, null);
		updateList();
	}

	private void updateIcon() {
		GwtIconRenderer<? super Poi> renderer = smartLayer.getRendererForPoi(mPoi);
		String iconUrl = renderer.getIconUrl(mPoi);
		imageMarkerIcon.setUrl(iconUrl);
		IssuemapGwt.getInstance().deleteMarker(mPoi);
		IssuemapGwt.getInstance().addMarker(mPoi);
	}
	
	private void updateList() {
		IssuemapGwt.getInstance().fillList();
	}

	
	@UiHandler("commentTextBox")
	void onCommentTextBoxKeyUp(KeyUpEvent event) {
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
						updateList();
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
		updateList();
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
		updateList();
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
		
		ThumbnailView showPhoto = new ThumbnailView(photoBaseUrl);

		imagePanel.add(showPhoto);
		
		picturesPanel.add(imagePanel);
		final int photoCount = mPoi.getPhotos().size();
		photosHeader.setText(photoCount + " Fotos:");
		tabButtonPhotos.setText("Fotos (" + photoCount + ")");
		if(photoCount == 1)
			lbMorePhotos.setText("");
		else
			lbMorePhotos.setText("("+(photoCount - 1)+" weitere)");
		
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
		final Label messageLabel = new Label("Sie können abstimmen, ob sie diesen Eintrag für wichtig (+1) oder unwichtig (-1) halten. Aus den abgebenen Stimmen berechnen wir eine Summe. Damit können andere schnell die wichtigsten Punkte finden. Meldungen, die überweigend als unwichtig bewertet werden, werden ausgeblendet um die Übersichtlichkeit der Karte beizubehalten");
		messageLabel.setWordWrap(true);
		messageLabel.setWidth("250px");
		decorated_panel.add(messageLabel);
		decorated_panel.setGlassEnabled(true);
		decorated_panel.setAnimationEnabled(true);
		decorated_panel.show();
		decorated_panel.center();
	}

	@UiHandler("btCancel")
	public void onBtCancelClicked(ClickEvent e)
	{
		if(newIssue)
		{
			this.removeFromParent();
			return;
		}
		if(markertypeIdBeforeEdit != 0)
		{
			mPoi.setMarkertype((Markertype) Markertype.getById(markertypeIdBeforeEdit));
			updateIcon();
		}
		setEditMode(false, true);
	}
	
	@UiHandler("btSave")
	public void onBtSaveClicked(ClickEvent e) {

		if (tbTitle.getText().equals(""))
			tbTitle.getElement().setAttribute("placeholder", "Bitte geben Sie dem Eintrag einen Namen");

		else {
			Requests.loadEntity(map, new RequestListener<Map>() {
				@Override
				public void onFinished(Collection<Map> results) {
					setIssueValuesFromMask();
					smartLayer.updatePoi(mPoi); // works even if the poi is a
												// new one
					map.getIssues().add(mPoi); // works even if the poi is
												// already present
					Requests.saveEntity(mPoi, new RequestListener<Poi>() {
						public void onFinished(java.util.Collection<Poi> results) {
							final IssuemapGwt issueMap = IssuemapGwt.getInstance();
							issueMap.fillList();
						};
					});

					Requests.saveEntity(map, null);

					if (newIssue) {
						// Add marker to map
						final IssuemapGwt issueMap = IssuemapGwt.getInstance();
						issueMap.addMarker(mPoi);
						issueMap.updateCounter();
					}
					updateList();
					newIssue = false;
					setEditMode(false, true);
					setValuesFromPoi();
				}
			});
		}
	}
	
	@UiHandler("btDelete")
	void onDelete(ClickEvent e) {
		if(Window.confirm("Möchten sie diesen Marker wirklich löschen?"))
		{
			mPoi.setDeleted(true);
			final IssuemapGwt issueMap = IssuemapGwt.getInstance();
			issueMap.deleteMarker(mPoi);
			issueMap.updateCounter();
			issueMap.fillList();
			Requests.saveEntity(mPoi, null);
			this.removeFromParent();
		}
	}
}
