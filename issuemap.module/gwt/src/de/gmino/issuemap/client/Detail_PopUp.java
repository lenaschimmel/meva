package de.gmino.issuemap.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Detail_PopUp extends Composite {

	private static Detail_PopUpUiBinder uiBinder = GWT
			.create(Detail_PopUpUiBinder.class);

	interface Detail_PopUpUiBinder extends UiBinder<Widget, Detail_PopUp> {
	}

	public Detail_PopUp() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label title;
	@UiField
	Label description;
	@UiField
	Button close;

	@UiHandler("close")
	void onClick(ClickEvent e) {
		this.removeFromParent();
	}

	public void setText(String titleString, String descriptionString) {
		description.setText(descriptionString);
		title.setText(titleString);
	}
}
