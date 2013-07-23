package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;

public class Feedback_PopUp extends Composite implements HasText {

	private static Info_PopUpUiBinder uiBinder = GWT
			.create(Info_PopUpUiBinder.class);

	
	interface Info_PopUpUiBinder extends UiBinder<Widget, Feedback_PopUp> {
	}

	public Feedback_PopUp(Map mapObject) {
		initWidget(uiBinder.createAndBindUi(this));
		title.getElement().getStyle().setColor(mapObject.getSecondary_color());
		
	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	Button sendButton;	


	public Feedback_PopUp(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("close")
	void onClick(ClickEvent e) {
		this.removeFromParent();
		IssuemapGwt.getInstance().addFeedback_Button();
	}
	
	@UiHandler("sendButton")
	void onSend(ClickEvent e) {
		this.removeFromParent();
		IssuemapGwt.getInstance().addFeedback_Button();
	}

	public void setText(String text) {

	}

	public String getText() {
		return null;
	}

}
