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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.ImageUrl;

public class Header extends Composite implements HasText {

	private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);

	interface HeaderUiBinder extends UiBinder<Widget, Header> {
	}

	public Header() {
		initWidget(uiBinder.createAndBindUi(this));
		info_button.setVisible(false);
		search_button.setVisible(false);
		search_field.setVisible(false);
		logo.setHeight("30px");
		header.setHeight("57px");
	}

	@UiField
	HorizontalPanel header;
	@UiField
	Image logo;
	@UiField
	Label title;
	@UiField
	PushButton search_button;
	@UiField
	TextBox search_field;
	@UiField
	PushButton info_button;

	public Header(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("info_button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}
	
	public void setDesign(String imgUrl, String titleString, String color) {
		logo.setUrl(imgUrl);
		title.setText(titleString);
		title.getElement().getStyle().setColor(color);
		header.getElement().getStyle().setBorderColor(color);
		search_button.getElement().getStyle().setBackgroundColor(color);
		info_button.getElement().getStyle().setBackgroundColor(color);
		info_button.setVisible(true);
		search_button.setVisible(true);
		search_field.setVisible(true);
		logo.setHeight("45px");
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}



}
