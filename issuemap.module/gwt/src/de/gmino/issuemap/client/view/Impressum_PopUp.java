package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.domain.Map;

public class Impressum_PopUp extends Composite implements HasText {

	private static Info_PopUpUiBinder uiBinder = GWT
			.create(Info_PopUpUiBinder.class);

	private DecoratedPopupPanel decorated_panel;
	
	interface Info_PopUpUiBinder extends UiBinder<Widget, Impressum_PopUp> {
	}

	public Impressum_PopUp(Map mapObject, DecoratedPopupPanel decorated_panel) {
		initWidget(uiBinder.createAndBindUi(this));
		title.getElement().getStyle().setColor(mapObject.getSecondary_color());
		this.decorated_panel = decorated_panel;
		full_name.setText(mapObject.getPostal_address().getRecipientName());
		street.setText(mapObject.getPostal_address().getStreet()+" "+mapObject.getPostal_address().getHouseNumber());
		town.setText(mapObject.getPostal_address().getZip()+ " "+ mapObject.getPostal_address().getCity());
		email.setText(mapObject.getEmail());
		
		
	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	Label full_name;
	@UiField
	Label street;
	@UiField
	Label town;
	@UiField
	Label email;

	@UiHandler("close")
	void onClick(ClickEvent e) {
		decorated_panel.hide();
	}

	public void setText(String text) {

	}

	public String getText() {
		return null;
	}

}
