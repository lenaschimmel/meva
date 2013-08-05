package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.domain.Map;

public class Info_PopUp extends Composite implements HasText {

	private static Info_PopUpUiBinder uiBinder = GWT
			.create(Info_PopUpUiBinder.class);

	private DecoratedPopupPanel decorated_panel;
	private Map mapObject;
	
	interface Info_PopUpUiBinder extends UiBinder<Widget, Info_PopUp> {
	}

	public Info_PopUp(Map mapObject, DecoratedPopupPanel decorated_panel) {
		initWidget(uiBinder.createAndBindUi(this));
		title.setText(mapObject.getTitle());
		title.getElement().getStyle().setColor(mapObject.getSecondary_color());
		infotext.setHTML(new SafeHtmlBuilder().appendEscapedLines(mapObject.getInfoText()).toSafeHtml());
		this.decorated_panel = decorated_panel;
		this.mapObject=mapObject;
		
	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	HTML infotext;
	@UiField
	Label impressum;

	public Info_PopUp(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("close")
	void onClick(ClickEvent e) {
		decorated_panel.hide();
	}
	
	@UiHandler("impressum")
	void onImpressumClick(ClickEvent e){
		Window.open(mapObject.getImpressum_url(), "Impressum", "");
		decorated_panel.hide();


		
	}

	public void setText(String text) {

	}

	public String getText() {
		return null;
	}

}
