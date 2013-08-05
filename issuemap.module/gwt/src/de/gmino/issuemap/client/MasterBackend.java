package de.gmino.issuemap.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.Create_Map_Backend;
import de.gmino.issuemap.client.view.Show_Maps_Backend;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class MasterBackend  implements EntryPoint {

	Create_Map_Backend createMapField= new Create_Map_Backend();
	Show_Maps_Backend mapList = new Show_Maps_Backend();
	
	@Override
	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());

		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"
				+ Location.getHost() + "/"));


		RootPanel.get("left").add(createMapField);
		RootPanel.get("right").add(mapList);
		
		Requests.getLoadedEntitiesByType(Map.type, new RequestListener<Map>() {
			@Override
			public void onNewResult(Map result) {
				mapList.addMapElement(result);
			}
		});
	} 

}
