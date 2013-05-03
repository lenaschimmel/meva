package de.gmino.issuemap.client.view;


import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
		Canvas canvas = Canvas.createIfSupported();
//		canvas.getContext2d().drawImage((ImageElement)icon.getElement(, 0, 0);
		canvas.setWidth("32px");
		canvas.getContext2d().setFillStyle("#F00");
		canvas.getContext2d().fillRect(2, 2, 30, 30);
		System.out.println("Bild: " + canvas.toDataUrl());
	//	icon.setUrl(canvas.toDataUrl());

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
	
	public void setColor(String color){
		icon.getElement().getStyle().setBackgroundColor(color);
	}
}
