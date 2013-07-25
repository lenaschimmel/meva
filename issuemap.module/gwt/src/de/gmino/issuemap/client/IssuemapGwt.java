package de.gmino.issuemap.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.geobase.client.map.OpenLayersMapView;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.issuemap.client.domain.Route;
import de.gmino.issuemap.client.poi.BicycleShopIconRenderer;
import de.gmino.issuemap.client.poi.BicycleShopPopupCreator;
import de.gmino.issuemap.client.poi.IssueIconRenderer;
import de.gmino.issuemap.client.poi.IssuePopupCreator;
import de.gmino.issuemap.client.poi.RouteIconRenderer;
import de.gmino.issuemap.client.poi.RoutePopupCreator;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.issuemap.client.view.CreateEvent_PopUp;
import de.gmino.issuemap.client.view.CreateIssue_PopUp;
import de.gmino.issuemap.client.view.Feedback_Button;
import de.gmino.issuemap.client.view.Feedback_PopUp;
import de.gmino.issuemap.client.view.Footer;
import de.gmino.issuemap.client.view.Header;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class IssuemapGwt implements EntryPoint {
	
		public class AddIssuesCommand implements RepeatingCommand {

			private ArrayList<Issue> issues;
			private int i = 0;

			public AddIssuesCommand(ArrayList<Issue> issues) {
				this.issues = issues;
			}
			
		@Override
		public boolean execute() {
			if(i >= issues.size())
				return false;
			
			addMarker(issues.get(i++));
			
			return true;
		}

	}

		private final class IssueLatitudeComparator implements Comparator<Issue> {
		@Override
		public int compare(Issue i1, Issue i2) {
			int latitudeComparision = -Double.compare(i1.getLocation()
					.getLatitude(), i2.getLocation().getLatitude());
			if (latitudeComparision != 0)
				return latitudeComparision;
			else
				return (i1.getId() < i2.getId()) ? -1 : ((i1.getId() == i2
						.getId()) ? 0 : 1); // Long.compare(i1.getId(),
											// i2.getId());
		}
	}

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	public static OpenLayersMapView mapView;
	private static Map mapObject;
	private static TreeMap<Issue, DivElement> divByIssue = new TreeMap<Issue, DivElement>();
	private OpenLayersSmartLayer markerLayer;
	private Footer footer = new Footer();
	private Header header = new Header();


	int counter = 0;

	private static IssuemapGwt instance;

	public IssuemapGwt() {
		if (instance != null)
			throw new RuntimeException("Can't create multiple instances!");
		instance = this;
	}

	public static IssuemapGwt getInstance() {
		return instance;
	}

	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());

		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"
				+ Location.getHost() + "/"));

		String[] domainSplit = Location.getHostName().split("\\.");
		String subdomain = domainSplit[0];
		if (subdomain.equalsIgnoreCase("www"))
			subdomain = domainSplit[1];

		// Create the map
		mapView = new OpenLayersMapView("map", "mapquest");
		markerLayer = mapView.newSmartLayer("Issues");
		mapView.setCenterAndZoom(new LatLon(20, 0), 0, false);
		markerLayer.addMarkerIconRenderer(Issue.type, new IssueIconRenderer());

		if (subdomain.equals("zgb")) {
			// TODO improves performance to load it first, but listener is not yet adjusted to wait for it.
			ImageUrlLoader.getInstance().loadImage("/mapicon/cycleway.png", null);
			markerLayer.addMarkerIconRenderer(BicycleShop.type,
					new BicycleShopIconRenderer());
			markerLayer.addMarkerIconRenderer(Route.type,
					new RouteIconRenderer());
		}
		
		

		
		// Add Header to RootPanel
		RootPanel.get("bar_top").add(header);
		RootPanel.get("bar_bottom").add(footer);
		

		// mapView.addEventListener(Event.click, new MapListener() {
		//
		// @Override
		// public void onEvent(LatLon location, Event event) {
		// footer.setText(location.toString());
		// }
		// });

		// Add create-PopUp by Double-Click
		mapView.addEventListener(Event.dblclick, new MapListener() {

			@Override
			public void onEvent(final LatLon location, Event event) {
				DivElement div = ((OpenLayersMapView) mapView).createPopup(
						location, "centerPopup", 1, 1);
				Composite popUp = null;
				if (mapObject.getMapTyp().equals("Issue")) {
					popUp = new CreateIssue_PopUp(mapObject,
							location, markerLayer);
				} else {
					popUp = new CreateEvent_PopUp(mapObject,
							location, markerLayer);
				}
				HTMLPanel.wrap(div).add(popUp);
				
				final int offsetWidth = popUp.getOffsetWidth();
				final int offsetHeight = popUp.getOffsetHeight();
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						mapView.panRectIntoMap(location, offsetWidth,
								offsetHeight, 30, true);
					}
				});
			}
		});

		mapRequest(subdomain);
		

	}

	void mapRequest(final String subdomain) {
		EntityQuery q = new QueryMapBySubdomain(subdomain);
		Requests.getLoadedEntitiesByQuery(Map.type, q,
				new RequestListener<Map>() {

					@SuppressWarnings("unchecked")
					public void onNewResult(final Map map) {
						mapObject = map;
						addFeedback_Button();
						markerLayer.addMarkerPopupCreator(Issue.type,
								new IssuePopupCreator(map, markerLayer));

						header.setMap(map);	

						footer.setMap(map);
						Window.setTitle(map.getTitle());
						mapView.setCenterAndZoom(mapObject.getInitLocation(),
								mapObject.getInitZoomlevel(), false);
						header.setDesign(mapObject.getLogo().getUrl(),
								mapObject.getTitle(), mapObject.getPrimary_color());
						footer.setDesign(mapObject.getPrimary_color());

						map.loadMarkertypes(new RequestListener<Markertype>() {
							@Override
							public void onFinished(
									Collection<Markertype> results) {

								if (subdomain.equals("zgb")) {
									markerLayer
											.addMarkerPopupCreator(
													BicycleShop.type,
													new BicycleShopPopupCreator(
															map,
															markerLayer));
									markerLayer
											.addMarkerPopupCreator(
													Route.type,
													new RoutePopupCreator(
															map,
															markerLayer));

									ZgbTools zgb = new ZgbTools(
											markerLayer) {
										@Override
										public void onAllRoutesShown() {
											System.out
													.println("All routes shown, now loading bicycle shops and Issues.");
											showBicycleShops();
											loadAndShowIssues();
										}
									};
									zgb.showRoutes();
								} else
									loadAndShowIssues();
							}
														
						});
					};

					@Override
					public void onError(String message, Throwable e) {
						Window.alert(message);
					}
				});
	}

	// update Map position
	static void setMapPosition(LatLon position) {
		mapView.setCenter(position, true);
	}

	public void addMarker(Poi poi) {
		markerLayer.addPoi(poi);
		counter++;
	}

	public void deleteMarker(Issue nIssue) {
		markerLayer.removePoi(nIssue);
		counter--;
	}

	private void loadAndShowIssues() {
		Collection<Issue> issues = mapObject.getIssues();
		Requests.loadEntities(issues, new RequestListener<Issue>() {
			@Override
			public void onFinished(Collection<Issue> results) {
				Scheduler scheduler = Scheduler.get();
				
				Comparator<Issue> compare = new IssueLatitudeComparator();
				TreeSet<Issue> sortedIssues = new TreeSet<Issue>(compare);
				sortedIssues.addAll(results);
				
				final ArrayList<Issue> filteredIssues = new ArrayList<Issue>();
				for (final Issue i : sortedIssues) {
					if (!i.isDeleted())
						filteredIssues.add(i);					
				}
				scheduler.scheduleIncremental(new AddIssuesCommand(filteredIssues));
				
				counter = filteredIssues.size();
				setCounter();
				counter = 0; // will be incremented by the deferred command and we dont want to count everything twice.
			}

		});
	}

	public void setCounter() {
		footer.setCounter(counter);
	}
	
	public void addFeedback_Popup(){
		Feedback_PopUp feedbackPopup = new Feedback_PopUp(mapObject);
		RootPanel.get("feedback").add(feedbackPopup);
	}
	
	public void addFeedback_Button(){
		Feedback_Button feedback = new Feedback_Button();
		RootPanel.get("feedback").add(feedback);
		
	}

	public Map getMap()
	{
		return mapObject;
	}


}
