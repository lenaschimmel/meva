package de.gmino.issuemap.client.view.backend;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.MasterBackend;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.list.Map_List_Item;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class Show_Maps_Backend extends Composite  {

	
	private static Show_Maps_BackendUiBinder uiBinder = GWT
			.create(Show_Maps_BackendUiBinder.class);

	
	interface Show_Maps_BackendUiBinder extends
			UiBinder<Widget, Show_Maps_Backend> {
	}

	public Show_Maps_Backend() {
		initWidget(uiBinder.createAndBindUi(this));
		executeRequest();
	}

	@UiField
	Button buttonRefresh;
	@UiField
	Button buttonNew;
	@UiField
	VerticalPanel verticalPanel;

	@UiHandler("buttonRefresh")
	void onClickRefresh(ClickEvent e) {
		verticalPanel.clear();
		executeRequest();
	}
	
	@UiHandler("buttonNew")
	void onClickNew(ClickEvent e) {
		MasterBackend.getInstance().newMap();
	}
	
	@UiHandler("buttonExport")
	void onClickExport(ClickEvent e) {
		Window.open("/CsvExport", "_blank", "");
	}
	
		public void addMapElement(Map map) {
		Map_List_Item mapListItem = new Map_List_Item(map.getId(), map.getSubdomain(), map.getTitle());
		verticalPanel.add(mapListItem);
	}

	
	private void executeRequest(){
		Requests.getLoadedEntitiesByType(Map.type, new RequestListener<Map>() {
			@Override
			public void onNewResult(Map result) {
				addMapElement(result);
			}
		});
	}
	
}
