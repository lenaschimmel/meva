package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.IssuemapGwt;

public class Feedback_Button extends Composite implements HasText {

	private static Feedback_ButtonUiBinder uiBinder = GWT
			.create(Feedback_ButtonUiBinder.class);

	interface Feedback_ButtonUiBinder extends UiBinder<Widget, Feedback_Button> {
	}

	public Feedback_Button() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	PushButton feedback_button;

	public Feedback_Button(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		feedback_button.setText(firstName);
	}

	@UiHandler("feedback_button")
	void onClick(ClickEvent e) {
		feedback_button.removeFromParent();
		IssuemapGwt.getInstance().addFeedback_Popup();
	}

	public void setText(String text) {
		feedback_button.setText(text);
	}

	public String getText() {
		return feedback_button.getText();
	}

}
