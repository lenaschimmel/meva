package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.client.domain.Issue;

public class Hover_PopUp extends Composite implements HasText {

	private static ShowPoi_PopUpUiBinder uiBinder = GWT
			.create(ShowPoi_PopUpUiBinder.class);

	interface ShowPoi_PopUpUiBinder extends UiBinder<Widget, Hover_PopUp> {
	}

	public Hover_PopUp(int height) {
		initWidget(uiBinder.createAndBindUi(this));
		expanded_marker.setVisible(true);
		expanded_marker.getElement().getStyle().setHeight(height, Unit.PX);
	}

	@UiField
	VerticalPanel expanded_marker;
	@UiField
	Label title;




	public void setText(String titleString, String descriptionString) {
		title.setText(titleString);
	}

	public String getText() {
		return null;

	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}

}
