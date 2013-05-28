/**
 * 
 */
package de.gmino.issuemap.client.view;

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
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.domain.ImageUrl;

/**
 * @author greenmobile
 *
 */
public class Footer extends Composite implements HasText {

	private static UIUiBinder uiBinder = GWT.create(UIUiBinder.class);

	interface UIUiBinder extends UiBinder<Widget, Footer> {
	}


	public Footer() {
		initWidget(uiBinder.createAndBindUi(this));
		chart_button.setVisible(false);
		list_button.setVisible(false);
		preferences_button.setVisible(false);
		footer.setHeight("50px");
	}



	@UiField
	HorizontalPanel footer;
	@UiField
	PushButton chart_button;
	@UiField
	PushButton list_button;
	@UiField
	PushButton preferences_button;
	@UiField
	Image gmino_logo;
	
	
	public Footer(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("gmino_logo")
	void onClick(ClickEvent e) {
		Window.open("http://www.gmino.de", "greenmobile Innovations", "");
	}
	
	public void setDesign(String color) {
		footer.getElement().getStyle().setBorderColor(color);
		chart_button.getElement().getStyle().setBackgroundColor(color);
		list_button.getElement().getStyle().setBackgroundColor(color);
		preferences_button.getElement().getStyle().setBackgroundColor(color);
//		chart_button.setVisible(true);
//		list_button.setVisible(true);
//		preferences_button.setVisible(true);
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
