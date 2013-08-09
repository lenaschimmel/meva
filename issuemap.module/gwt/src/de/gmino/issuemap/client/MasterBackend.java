package de.gmino.issuemap.client;

import java.util.Collection;

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

	Create_Map_Backend createMapField;
	Show_Maps_Backend mapList;
	private static MasterBackend instance;
	
	public MasterBackend() {
		instance = this;
	}
	
	@Override
	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());

		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"
				+ Location.getHost() + "/"));

		createMapField= new Create_Map_Backend();
		mapList = new Show_Maps_Backend();
		
		RootPanel.get("right").add(createMapField);
		RootPanel.get("left").add(mapList);
		

	} 

	public static MasterBackend getInstance()
	{
		return instance;
	}
	
	public void newMap()
	{
		createMapField.showNewMap();
	}
	
	public void editMap(long id)
	{
		final Map map = (Map)Map.getById(id);
		Requests.loadEntity(map, new RequestListener<Map>() {
			@Override
			public void onFinished(Collection<Map> results) {
				createMapField.showExistingMap(map, false);
			}
		});
	}	
	
	public void copyMap(long id)
	{
		createMapField.showExistingMap((Map)Map.getById(id), true);
	}
}
