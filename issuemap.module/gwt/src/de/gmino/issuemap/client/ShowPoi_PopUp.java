package de.gmino.issuemap.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ShowPoi_PopUp extends Composite implements HasText {

	private static ShowPoi_PopUpUiBinder uiBinder = GWT
			.create(ShowPoi_PopUpUiBinder.class);

	interface ShowPoi_PopUpUiBinder extends UiBinder<Widget, ShowPoi_PopUp> {
	}

	public ShowPoi_PopUp() {
		initWidget(uiBinder.createAndBindUi(this));
		simple_marker.setVisible(false);
		expanded_marker.setVisible(true);
		
		
	}

	@UiField
	Image simple_marker;
	@UiField
	HTMLPanel expanded_marker;

	@UiHandler("simple_marker")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	public void setText(String text) {

	}

	public String getText() {
		return null;

	}

}
