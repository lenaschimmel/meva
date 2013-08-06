package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Marker_List_Item extends Composite {

	private static Marker_List_ItemUiBinder uiBinder = GWT
			.create(Marker_List_ItemUiBinder.class);

	interface Marker_List_ItemUiBinder extends
			UiBinder<Widget, Marker_List_Item> {
	}

	public Marker_List_Item(String imageName, String markerName) {
		initWidget(uiBinder.createAndBindUi(this));
		markerImage.setUrl("mapicon/" + imageName + ".png");
		markerNameLabel.setText(markerName);
	}

	@UiField
	Image markerImage;
	@UiField
	Label markerNameLabel;
	@UiField
	CheckBox markerCheckbox;
	@UiField
	VerticalPanel parent;
	public boolean selected = false;
	
	public void setCheckBox(){
		selected=true;
		markerCheckbox.setValue(true);
		parent.getElement().getStyle().setBackgroundColor("rgba(255,0,0,0.2)");
	}
	
	public void setUncheckBox(){
		selected=false;
		markerCheckbox.setValue(false);
		parent.getElement().getStyle().setBackgroundColor("rgba(255,0,0,0)");
	}
	
	@UiHandler("markerCheckbox")
	void onClick(ClickEvent e) {
		if(selected==false)	setCheckBox();			
		else if(selected==true) setUncheckBox();
	}
}
