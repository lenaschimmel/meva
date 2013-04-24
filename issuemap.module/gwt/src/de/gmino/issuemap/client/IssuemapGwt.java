package de.gmino.issuemap.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.geobase.client.map.OpenLayersMapView;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.geobase.shared.map.MarkerLayer;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.issuemap.shared.request.QueryIssuesByMap;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;


public class IssuemapGwt implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private OpenLayersMapView map;
	private MarkerLayer markerLayer;
	private Footer footer = new Footer();
	private Header header = new Header();
	
	public void onModuleLoad() {


		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());

		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"
				+ Location.getHost()+"/"));

		
		// Create the map
		map = new OpenLayersMapView("map","mapquest");
		markerLayer = map.newMarkerLayer("cycleway_problems_bs");
		map.addLayer(markerLayer);
		map.setCenterAndZoom(new LatLon(20, 0), 0, false);						

		
		//Add Header to RootPanel
		RootPanel.get("bar_top").add(header);
		
		RootPanel.get("bar_bottom").add(footer);
		
		// Add create-PopUp by Doubble-Click
		map.addEventListener(Event.dblclick, new MapListener() {
			
			@Override
			public void onEvent(LatLon location, Event event) {
				DivElement div = ((OpenLayersMapView)map).createPopup(location, "centerPopup", 100, 100);
				div.getStyle().setOverflow(Overflow.VISIBLE);
				div.getStyle().setBackgroundColor("transparent");
				div.getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
				div.getParentElement().getStyle().setBackgroundColor("transparent");
				div.getParentElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
				div.getParentElement().getParentElement().getStyle().setBackgroundColor("transparent");
				CreatePoi_PopUp popUp = new CreatePoi_PopUp();
				HTMLPanel.wrap(div).add(popUp);
			//	div.appendChild(popUp.getElement());
		
			}
		});
		
		// Focus the cursor on the search field when the app loads
//		searchField.setFocus(true);
//		searchField.selectAll();
		
		//fetch map-Objekt
		String[] domainSplit = Location.getHostName().split("\\.");
		String subdomain = domainSplit[0];
		mapRequest(subdomain);

	}
	void mapRequest(String subdomain) {

		EntityQuery q = new QueryMapBySubdomain(subdomain);
		Requests.getLoadedEntitiesByQuery(Map.type, q,
				new RequestListener<Map>() {

					public void onNewResult(Map result) {
						map.setCenterAndZoom(result.getInitLocation(), result.getInitZoomlevel(), false);						
						header.setDesign(result.getLogo().getUrl(), result.getTitle(), result.getColor());
						footer.setDesign(result.getColor());
						Collection<Issue> tooManyIssues = result.getIssues();
						Collection<Issue> issues = new ArrayList<Issue>();
						
						int i = 0;
						for (Issue issue : tooManyIssues)
						{
							i++;
							issues.add(issue);
							if(i > 10)
								break;
						}
						Requests.loadEntities(issues, new RequestListener<Issue>() {
							@Override
							public void onNewResult(Issue result) {
								System.out.println(result.getTitle());
								DivElement div = ((OpenLayersMapView)map).createPopup(result.getLocation(), "centerPopup", 1, 1);
								div.getStyle().setOverflow(Overflow.VISIBLE);
								div.getStyle().setBackgroundColor("transparent");
								div.getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
								div.getParentElement().getStyle().setBackgroundColor("transparent");
								div.getParentElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
								div.getParentElement().getParentElement().getStyle().setBackgroundColor("transparent");
								Marker_Wrapper popUp = new Marker_Wrapper();
								HTMLPanel.wrap(div).add(popUp);
								//div.appendChild(popUp.getElement());
								
							}
						});
					};

					@Override
					public void onError(String message, Throwable e) {
						Window.alert(message);
					}
				});
	}
	
	
	
	void showInfoPopUp(){
		
		
	}
	
}
