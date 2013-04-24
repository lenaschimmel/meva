package de.gmino.issuemap.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.resources.ImageResources;

public class Marker_Wrapper extends Composite implements HasText {

	ShowPoi_PopUp hover = new ShowPoi_PopUp();
	Image image;

	
	private static Marker_WrapperUiBinder uiBinder = GWT
			.create(Marker_WrapperUiBinder.class);

	interface Marker_WrapperUiBinder extends UiBinder<Widget, Marker_Wrapper> {
	}

	public Marker_Wrapper() {
		initWidget(uiBinder.createAndBindUi(this));
		ImageResources resources = GWT.create(ImageResources.class);
		image = new Image(resources.pin_export());
		wrapper.add(image);
	}

	@UiField
	FocusPanel wrapper;

	public Marker_Wrapper(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));


	}
	
	@UiHandler("wrapper")
	void onMouseOver(MouseOverEvent e) {
		wrapper.clear();
		
//		remove(image);
		wrapper.add(hover);
	}
	
	@UiHandler("wrapper")
	void onMouseOut(MouseOutEvent e) {
//		wrapper.remove(hover);
		wrapper.clear();
		wrapper.add(image);

	}

	public void setText(String text) {
	}

	public String getText() {
		return null;
	}

}
