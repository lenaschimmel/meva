package de.gmino.issuemap.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.resources.ImageResources;

public class MarkerIcon extends Composite implements HasText {

	private static MarkerIconUiBinder uiBinder = GWT
			.create(MarkerIconUiBinder.class);

	interface MarkerIconUiBinder extends UiBinder<Widget, MarkerIcon> {
	}

	public MarkerIcon() {
		initWidget(uiBinder.createAndBindUi(this));
		ImageResources resources = GWT.create(ImageResources.class);
		icon.setResource(resources.pin_export());	
	}

	@UiField
	Image icon;

	public MarkerIcon(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setText(String text) {
	}

	public String getText() {
		return null;

	}

}
