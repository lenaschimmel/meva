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

import de.gmino.issuemap.client.domain.Map;

public class Info_PopUp extends Composite implements HasText {

	private static Info_PopUpUiBinder uiBinder = GWT
			.create(Info_PopUpUiBinder.class);

	private DecoratedPopupPanel infoPopUp;
	
	interface Info_PopUpUiBinder extends UiBinder<Widget, Info_PopUp> {
	}

	public Info_PopUp(Map mapObject, DecoratedPopupPanel infoPopUp) {
		initWidget(uiBinder.createAndBindUi(this));
		title.setText(mapObject.getTitle());
		infotext.setText(mapObject.getInfoText());
		this.infoPopUp = infoPopUp;
		
	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	Label infotext;

	public Info_PopUp(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("close")
	void onClick(ClickEvent e) {
		infoPopUp.hide();
	}

	public void setText(String text) {

	}

	public String getText() {
		return null;
	}

}
