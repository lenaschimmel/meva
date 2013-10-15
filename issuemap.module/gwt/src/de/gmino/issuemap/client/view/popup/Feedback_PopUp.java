package de.gmino.issuemap.client.view.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.Feedback_Button;
import de.gmino.issuemap.shared.request.SendFeedback;
import de.gmino.meva.shared.request.Requests;

public class Feedback_PopUp extends Composite {

	private static Feedback_PopUpUiBinder uiBinder = GWT
			.create(Feedback_PopUpUiBinder.class);
	public Feedback_Button feedback_Button;
	
	interface Feedback_PopUpUiBinder extends UiBinder<Widget, Feedback_PopUp> {
	}

	public Feedback_PopUp(Map mapObject, Feedback_Button feedback_Button) {
		initWidget(uiBinder.createAndBindUi(this));
		parent.getElement().getStyle().setBackgroundColor(mapObject.getPopupBackgroundColor());
		message.getElement().setAttribute("placeholder", "Geben Sie hier Ihre Nachricht ein");
		email.getElement().setAttribute("placeholder", "Ihre Email-Adresse (optional)");
		typebox.setItemText(1, 	"Feedback an: " + mapObject.getPostal_address().getRecipientName());
		this.feedback_Button=feedback_Button;
	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	Button sendButton;	
	@UiField
	Panel parent;

	@UiField
	ListBox typebox;
	
	@UiField
	TextArea message;

	@UiField
	TextBox email;

	@UiHandler("close")
	void onClick(ClickEvent e) {
		this.setVisible(false);
		feedback_Button.setVisible(true);

	}
	
	@UiHandler("sendButton")
	void onSend(ClickEvent e) {
		
		boolean toDevelopers = typebox.getSelectedIndex() == 0;
		String messageString = message.getText();
		String emailAddressString = email.getText();
		
		SendFeedback sendFeedback = new SendFeedback(toDevelopers, messageString, emailAddressString, IssuemapGwt.getInstance().getMap());
		Requests.getValuesByQuery(sendFeedback, null);
		
		this.removeFromParent();
		IssuemapGwt.getInstance().addFeedback_Button();
	}
}
