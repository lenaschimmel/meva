/**
 * 
 */
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

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public Footer() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	@UiField
	HorizontalPanel footer;
	@UiField
	PushButton chart_button;
	@UiField
	PushButton list_button;
	@UiField
	PushButton preferences_button;
	
	
	public Footer(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("chart_button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}
	
	public void setDesign(String color) {
		footer.getElement().getStyle().setBorderColor(color);
		chart_button.getElement().getElementsByTagName("img").getItem(0).getStyle().setBackgroundColor(color);
		list_button.getElement().getElementsByTagName("img").getItem(0).getStyle().setBackgroundColor(color);
		preferences_button.getElement().getElementsByTagName("img").getItem(0).getStyle().setBackgroundColor(color);
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
