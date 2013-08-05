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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Map_List_Item extends Composite implements HasText {

	private static Map_List_ItemUiBinder uiBinder = GWT
			.create(Map_List_ItemUiBinder.class);

	interface Map_List_ItemUiBinder extends UiBinder<Widget, Map_List_Item> {
	}

	public Map_List_Item(long mapId, String mapSubdomain, String mapTitle) {
		initWidget(uiBinder.createAndBindUi(this));
		id.setText("" + mapId);
		subdomain.setText(mapSubdomain);
		title.setText(mapTitle);
	}

	@UiField
	Label id;
	@UiField
	Label subdomain;
	@UiField
	Label title;

	public Map_List_Item(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	public void setText(String text) {
	}

	public String getText() {
		return null;
	}

}
