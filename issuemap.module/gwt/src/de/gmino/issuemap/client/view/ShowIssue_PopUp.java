package de.gmino.issuemap.client.view;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.issuemap.client.Marker_Wrapper;
import de.gmino.issuemap.client.domain.Comment;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.resources.ImageResources;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class ShowIssue_PopUp extends Composite {

	static DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
	ImageResources imageRes;

	private static Detail_PopUpUiBinder uiBinder = GWT
			.create(Detail_PopUpUiBinder.class);

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
		setBoarderColor(map.getColor());

		if (mIssue.getPrimary_picture().getUrl().equals("")
				|| mIssue.getPrimary_picture().getUrl().equals("Bild URL")) { //TODO funktioniert nur halb?!
			picture.setVisible(false);
			picture.setHeight("0px");
		} else {
			ImageUrl img = mIssue.getPrimary_picture();
			picture.setUrl(img.getUrl());
		}
		date.setText(dtf.format(mIssue.getCreationTimestamp().toDate()));
		type.setText(mIssue.getMarkertype().getMarkerName());
		resolved.setValue(mIssue.isResolved());
		rating.setText("" + mIssue.getRating());
		up_count.setText("" +(mIssue.getNumber_of_rating()+mIssue.getRating())/2);
		down_count.setText("" + (mIssue.getNumber_of_rating()-mIssue.getRating())/2);
		title.setText(mIssue.getTitle());
		description.setText(mIssue.getDescription());
		
		//mIssue.vote=0;
		updateButtonColorsAndLabels();
		
		final int commentCount = issue.getComments().size();
		if(commentCount == 0)
			commentsHeader.setText("Bisher keine Kommentare.");
		else
		{
			commentsHeader.setText(commentCount + " Kommentare (lade...)");
			Requests.loadEntities(issue.getComments(), new RequestListener<Comment>() {
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

	@UiField
	Label title;
	@UiField
	CheckBox resolved;
	@UiField
	Label rating;
	@UiField
	Label up_count;
	@UiField
	Label down_count;
	@UiField
	Image rate_up;
	@UiField
	Image rate_down;
	@UiField
	Label date;
	@UiField
	Label type;
	@UiField
	Label description;
	@UiField
	Image close;
	@UiField
	Image delete;
	@UiField
	Image edit;
	@UiField
	VerticalPanel parent;
	@UiField
	Image picture;
	@UiField
	VerticalPanel commentsPanel;
	@UiField
	Label commentsHeader;
	@UiField 
	TextBox commentTextBox;
	@UiField
	Button commentButton;

	@UiHandler("close")
	void onClose(ClickEvent e) {
		this.removeFromParent();
	}

	@UiHandler("edit")
	void onEdit(ClickEvent e) {
		this.removeFromParent();
		CreateIssue_PopUp cip = new CreateIssue_PopUp(mapObject, mIssue, smartLayer);
		cip.setBoarderColor(mapObject.getColor());
		mWrapper.add(cip);

	}

	@UiHandler("delete")
	void onDelete(ClickEvent e) {
		mIssue.setDeleted(true);
		Requests.saveEntity(mIssue, null);
		this.removeFromParent();
		smartLayer.removePoi(mIssue);
	}

	@UiHandler("resolved")
	void onCheckbox(ClickEvent e) {
		mIssue.setResolved(resolved.getValue());
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
	
	@UiHandler("commentButton")
	void onCommentButtonClick(ClickEvent e) {
		commentTextBox.setEnabled(false);
		Requests.getNewEntity(Comment.type, new RequestListener<Comment>() {
			@Override
			public void onNewResult(Comment comment) {
				comment.setText(commentTextBox.getText());
				comment.setUser("anonym");
				mIssue.getComments().add(comment);
				Requests.saveEntity(comment, null);
				Requests.saveEntity(mIssue, null);
				showComment(comment);
				commentTextBox.setText("");
				commentTextBox.setEnabled(true);
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
		rating.setText("" + (mIssue.getRating()));
		up_count.setText(""
				+ (mIssue.getNumber_of_rating() + mIssue.getRating()) / 2);
		down_count.setText(""
				+ (mIssue.getNumber_of_rating() - mIssue.getRating()) / 2);
	}

	public void setText(String titleString, String descriptionString) {
		description.setText(descriptionString);
		title.setText(titleString);
	}

	public void setBoarderColor(String color) {
		parent.getElement().getStyle().setBorderColor(color);
	}
	
	private void showComment(Comment comment) {
		commentsPanel.add(new Label(comment.getText()));
	}
}
