package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.popup.Feedback_PopUp;

public class Feedback_Button extends Composite implements HasText {

	private static Feedback_ButtonUiBinder uiBinder = GWT
			.create(Feedback_ButtonUiBinder.class);
	
	private Map mapObject;
	public Feedback_PopUp feedbackPopup;

	interface Feedback_ButtonUiBinder extends UiBinder<Widget, Feedback_Button> {
	}

	public Feedback_Button(Map mapObject) {
		initWidget(uiBinder.createAndBindUi(this));
		this.mapObject=mapObject;
		feedbackPopup = new Feedback_PopUp(mapObject, this);
		RootPanel.get("feedback").add(feedbackPopup);
		feedbackPopup.setVisible(false);
	}

	@UiField
	PushButton feedback_button;


	@UiHandler("feedback_button")
	void onClick(ClickEvent e) {
		this.setVisible(false);
		feedbackPopup.setVisible(true);
		
	}

	public void setText(String text) {
		feedback_button.setText(text);
	}

	public String getText() {
		return feedback_button.getText();
	}



}
