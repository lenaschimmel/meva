package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.shared.request.SendFeedback;
import de.gmino.meva.shared.request.Requests;

public class Feedback_PopUp extends Composite implements HasText {

	private static Info_PopUpUiBinder uiBinder = GWT
			.create(Info_PopUpUiBinder.class);

	
	interface Info_PopUpUiBinder extends UiBinder<Widget, Feedback_PopUp> {
	}

	public Feedback_PopUp(Map mapObject) {
		initWidget(uiBinder.createAndBindUi(this));
		title.getElement().getStyle().setColor(mapObject.getSecondary_color());
		message.getElement().setAttribute("placeholder", "Geben Sie hier Ihre Nachricht ein");
		email.getElement().setAttribute("placeholder", "Ihre Email-Adresse (optional)");
	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	Button sendButton;	


	@UiField
	ListBox typebox;
	
	@UiField
	TextArea message;

	@UiField
	TextBox email;

	@UiHandler("close")
	void onClick(ClickEvent e) {
		this.removeFromParent();
		IssuemapGwt.getInstance().addFeedback_Button();
	}
	
	@UiHandler("sendButton")
	void onSend(ClickEvent e) {
		
		boolean toDevelopers = typebox.getSelectedIndex() == 0;
		String messageString = message.getText();
		String emailAddressString = email.getText();
		
		SendFeedback sendFeedback = new SendFeedback(toDevelopers, messageString, emailAddressString);
		Requests.getValuesByQuery(sendFeedback, null);
		
		this.removeFromParent();
		IssuemapGwt.getInstance().addFeedback_Button();
	}

	public void setText(String text) {

	}

	public String getText() {
		return null;
	}

}
