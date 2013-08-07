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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.MasterBackend;

public class Map_List_Item extends Composite {

	private static Map_List_ItemUiBinder uiBinder = GWT
			.create(Map_List_ItemUiBinder.class);

	interface Map_List_ItemUiBinder extends UiBinder<Widget, Map_List_Item> {
	}

	public Map_List_Item(long mapId, String mapSubdomain, String mapTitle) {
		this.mapId = mapId;
		initWidget(uiBinder.createAndBindUi(this));
		id.setText("" + mapId);
		subdomain.setText(mapSubdomain);
		title.setText(mapTitle);
	}

	long mapId;
	
	@UiField
	Label id;
	@UiField
	Label subdomain;
	@UiField
	Label title;

	
	@UiHandler("subdomain")
	void onClick(ClickEvent e) {
		Window.open("http://"+ subdomain.getText() + ".3.geoengine.de", "geoengine" + subdomain.getText(), "");
	}

	public Map_List_Item() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	


	@UiHandler("edit")
	public void onEditClicked(ClickEvent e)
	{
		MasterBackend.getInstance().editMap(mapId);
	}
	
	@UiHandler("copy")
	public void onCopyClicked(ClickEvent e)
	{
		MasterBackend.getInstance().copyMap(mapId);
	}
}
