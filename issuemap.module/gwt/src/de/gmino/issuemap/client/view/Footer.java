package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.domain.Map;

public class Footer extends Composite {

	private static UIUiBinder uiBinder = GWT.create(UIUiBinder.class);
	Map mapObject;

	interface UIUiBinder extends UiBinder<Widget, Footer> {
	}


	public Footer() {
		initWidget(uiBinder.createAndBindUi(this));
		text.setVisible(false);
		footer.setHeight("50px");
	}



	@UiField
	HorizontalPanel footer;
	@UiField
	Image gmino_logo;
	@UiField
	Image counter_icon;
	@UiField
	Image cursor;
	@UiField
	Label text;
	@UiField
	Label counter;
	@UiField
	Panel counterPanel;
	@UiField
	Panel doubleClickInfoPanel;
	
	public Footer(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("gmino_logo")
	void onClick(ClickEvent e) {
		Window.open("http://geoengine.de", "greenmobile geoengine", "");
	}
	
	public void setDesign(Map map) {
		this.mapObject = map;
		text.getElement().getStyle().setColor(mapObject.getSecondary_color());
		counter.getElement().getStyle().setColor(mapObject.getSecondary_color());
		text.setVisible(true);
		cursor.setVisible(true);
		footer.getElement().getStyle().setBackgroundColor(mapObject.getBackground_color());
		if(mapObject.isEdit()){
			counterPanel.setVisible(true);
			doubleClickInfoPanel.setVisible(true);
		}			
	}

	public void setCounter(int count) {
		counter.setText("Bisher wurden " + count+" Stellen markiert");
		counter_icon.setVisible(true);
	}

}
