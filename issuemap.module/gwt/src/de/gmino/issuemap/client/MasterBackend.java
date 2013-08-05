package de.gmino.issuemap.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class MasterBackend  implements EntryPoint {

	FlowPanel panel;
	
	@Override
	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());

		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"
				+ Location.getHost() + "/"));

		panel = new FlowPanel();
		panel.add(new Label("MasterBackend is running."));
		RootPanel.get(null).add(panel);
		
		EntityQuery query = new QueryMapBySubdomain("gmino");
		Requests.getLoadedEntitiesByQuery(Map.type, query, new RequestListener<Map>() {
			@Override
			public void onNewResult(Map result) {
				panel.add(new Label("Test result: " + result.getTitle()));
			}
		});
	} 

}
