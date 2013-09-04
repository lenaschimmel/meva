package de.gmino.issuemap.client;

import java.util.Collection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.view.Header;
import de.gmino.issuemap.client.view.Login;
import de.gmino.issuemap.client.view.backend.Create_Map_Backend;
import de.gmino.issuemap.client.view.backend.Show_Maps_Backend;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class MasterBackend  implements EntryPoint  {

	Create_Map_Backend createMapField;
	Show_Maps_Backend mapList;
	Header header;
	Login login;
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
		

		header = new Header();
		header.setBackendDesign("logo_geoengine.png", "geoEngine Backend", "#FFF", "rgba(40,40,40,0.8)");
		header.setURL("http://gmino.geoengine.de/masterBackend.html");
		login= new Login(this);
		
		  Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		        public void execute () {
		            login.user.setFocus(true);
		        }
		   });

			

		RootPanel.get("parent").add(header);
		RootPanel.get("parent").add(login);


			
	} 
	
	public void onLogin(){
		createMapField= new Create_Map_Backend();
		mapList = new Show_Maps_Backend();

		RootPanel.get("right").add(createMapField);
		RootPanel.get("left").add(mapList);
		login.removeFromParent();



		
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
