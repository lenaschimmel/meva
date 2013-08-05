package de.gmino.issuemap.client.view;

import java.awt.Label;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.domain.Map;

public class Show_Maps_Backend extends Composite implements HasText {

	private static Show_Maps_BackendUiBinder uiBinder = GWT
			.create(Show_Maps_BackendUiBinder.class);

	
	interface Show_Maps_BackendUiBinder extends
			UiBinder<Widget, Show_Maps_Backend> {
	}

	public Show_Maps_Backend() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button button;
	@UiField
	VerticalPanel verticalPanel;

	public Show_Maps_Backend(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(firstName);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}
	
	public void addMapElement(Map map) {
		Map_List_Item mapListItem = new Map_List_Item(map.getId(), map.getSubdomain(), map.getTitle());
		verticalPanel.add(mapListItem);
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

}
