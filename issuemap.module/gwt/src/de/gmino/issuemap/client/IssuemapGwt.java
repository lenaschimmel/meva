package de.gmino.issuemap.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import de.gmino.geobase.client.map.OpenLayersMapView;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.issuemap.client.ImageUrlLoader.ImageLoadListener;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.ElectricalSubstation;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.issuemap.client.domain.Route;
import de.gmino.issuemap.client.poi.BicycleShopIconRenderer;
import de.gmino.issuemap.client.poi.BicycleShopPopupCreator;
import de.gmino.issuemap.client.poi.DecentralizedGenerationIconRenderer;
import de.gmino.issuemap.client.poi.DecentralizedGenerationPopupCreator;
import de.gmino.issuemap.client.poi.ElectricalSubstationPopupCreator;
import de.gmino.issuemap.client.poi.IssueIconRenderer;
import de.gmino.issuemap.client.poi.IssuePopupCreator;
import de.gmino.issuemap.client.poi.RouteIconRenderer;
import de.gmino.issuemap.client.poi.RoutePopupCreator;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.issuemap.client.view.CreateEvent_PopUp;
import de.gmino.issuemap.client.view.CreateIssue_PopUp;
import de.gmino.issuemap.client.view.Feedback_Button;
import de.gmino.issuemap.client.view.Footer;
import de.gmino.issuemap.client.view.Header;
import de.gmino.issuemap.client.view.IssueList_PopUp;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.domain.KeyValueDef;
import de.gmino.meva.client.domain.KeyValueSet;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.Log;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class IssuemapGwt implements EntryPoint, UncaughtExceptionHandler {
	
	public class AddIssuesCommand implements RepeatingCommand {

		private ArrayList<Issue> issues;
		private int i = 0;

		public AddIssuesCommand(ArrayList<Issue> issues) {
			this.issues = issues;
		}
			
		@Override
		public boolean execute() {
			if(i >= issues.size())
			{
				mapView.setCenterAndZoom(mapObject.getInitLocation(), mapObject.getInitZoomlevel(), false);
				return false;
			}
			
			addMarker(issues.get(i++));
			
			return true;
		}
	}
		
	public static int compareLong(long l1, long l2)
	{
		return (l1 < l2) ? -1 : ((l1 == l2) ? 0 : 1);
	}

	private final class IssueLatitudeComparator implements Comparator<Issue> {
		@Override
		public int compare(Issue i1, Issue i2) {
			int latitudeComparision = -Double.compare(i1.getLocation()
					.getLatitude(), i2.getLocation().getLatitude());
			if (latitudeComparision != 0)
				return latitudeComparision;
			else
				return compareLong(i1.getId(), i2.getId());
		}
	}

	private final class IssueRatingComparator implements Comparator<Issue> {
		@Override
		public int compare(Issue i1, Issue i2) {
			int ratingComparision = -compareLong(i1.getRating(), i2.getRating());
			if (ratingComparision != 0)
				return ratingComparision;
			else
			{
				int timestampComparision = -compareLong(i1.getCreationTimestamp().getMillisSinceEpoch(), i2.getCreationTimestamp().getMillisSinceEpoch());
				if (timestampComparision != 0)
					return timestampComparision;
				else
					return compareLong(i1.getId(), i2.getId());
			}
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
	
	Collection<DecentralizedGeneration> generations;


	int counter = 0;

	private static IssuemapGwt instance;

	private IssueIconRenderer issueRenderer;

	private static final int GENERAL_POPUP_MARGIN = 20;

	private IssueList_PopUp list;

	private String subdomain;

	public IssuemapGwt() {
		if (instance != null)
			throw new RuntimeException("Can't create multiple instances!");
		instance = this;
	}

	public static IssuemapGwt getInstance() {
		return instance;
	}
	

	@Override
	public void onUncaughtException(Throwable e) {
		System.err.println("### UncaughtExceptionHandler ###");
		e.printStackTrace();
	}

	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(this);
		
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());

		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"
				+ Location.getHost() + "/"));

		String[] domainSplit = Location.getHostName().split("\\.");
		subdomain = domainSplit[0];
		if (subdomain.equalsIgnoreCase("www"))
			subdomain = domainSplit[1];

		// Create the map
		mapView = new OpenLayersMapView("map", "mapquest");
		
		mapView.setBorders(30 + GENERAL_POPUP_MARGIN, 58 + GENERAL_POPUP_MARGIN, 357 + GENERAL_POPUP_MARGIN, 53 +GENERAL_POPUP_MARGIN);
		int zoomThreshold = 12;
		if(subdomain.equals("zgb"))
			zoomThreshold = 11;
		if(subdomain.equals("piraten-braunschweig"))
			zoomThreshold = 13;
		if(subdomain.equals("ee"))
			zoomThreshold = 14;
		
		markerLayer = mapView.newSmartLayer("Issues", zoomThreshold);
		mapView.setCenterAndZoom(new LatLon(20, 0), 0, false);
		issueRenderer = new IssueIconRenderer();
		markerLayer.addMarkerIconRenderer(Issue.type, issueRenderer);

		final ImageUrlLoader loader = ImageUrlLoader.getInstance();
		loader.loadImage("/camera.png", null);
		loader.loadImage("/bubble.png", null);
		
		if (subdomain.equals("zgb")) {
			// TODO improves performance to load it first, but listener is not yet adjusted to wait for it.
			loader.loadImage("/mapicon/cycleway.png", null);
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
				final Composite popUp = getPopupByMapObject(location);
				HTMLPanel.wrap(div).add(popUp);
				
				
				// the popup width is not always computeted correctly the first time and simple deferring doesn't help. Therefore, we ask for the width until it seems about right before using ist.
				Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
					@Override
					public boolean execute() {
						int offsetWidth = popUp.getOffsetWidth();
						int offsetHeight = popUp.getOffsetHeight();
						
						if(offsetWidth < 160)
							return true;
						
						mapView.panRectIntoMap(location, offsetWidth,
								offsetHeight, true);
						
						return false;
					}
				}, 100);
			}

			private Composite getPopupByMapObject(final LatLon location) {
				Composite popUp;
				if (mapObject.getMapTyp().equals("Issue")) {
					popUp = new CreateIssue_PopUp(mapObject,
							location, markerLayer);
				} else {
					popUp = new CreateEvent_PopUp(mapObject,
							location, markerLayer);
				}
				return popUp;
			}
		});
		
		Requests.getLoadedEntitiesByType(KeyValueDef.type, new RequestListener<KeyValueDef>() {
			@Override
			public void onFinished(Collection<KeyValueDef> results) {
				Requests.getLoadedEntitiesByType(KeyValueSet.type, new RequestListener<KeyValueSet>() {
					@Override
					public void onFinished(Collection<KeyValueSet> results) {
						mapRequest();
					}
				});
			}
		});
		
		
	}

	public String getSubdomain() {
		return subdomain;
	}

	void mapRequest() {
		EntityQuery q = new QueryMapBySubdomain(subdomain);
		Requests.getLoadedEntitiesByQuery(Map.type, q,
				new RequestListener<Map>() {

					@SuppressWarnings("unchecked")
					public void onNewResult(final Map map) {
						mapObject = map;
						
						
						addFeedback_Button();
						markerLayer.addMarkerPopupCreator(Issue.type, new IssuePopupCreator(map, markerLayer));

						header.setMap(map);	

						footer.setMap(map);
						Window.setTitle(map.getTitle());

						mapView.setZoom(mapObject.getInitZoomlevel());
						mapView.setCenterAndZoom(mapObject.getInitLocation(), mapObject.getInitZoomlevel(), false);
						
						Scheduler.get().scheduleFixedDelay(
						new RepeatingCommand() {
							
							@Override
							public boolean execute() {
									mapView.setCenterAndZoom(mapObject.getInitLocation(), mapObject.getInitZoomlevel(), false);
								return false;
							}
						}, 1000);
						
						header.setDesignbyMapobject(mapObject.getLogo().getUrl(),	mapObject.getTitle(), mapObject.getPrimary_color());
						footer.setDesign(mapObject.getPrimary_color());

						if(map.getMapTyp().equals("EE"))
							fillMapEE();
						else
							map.loadMarkertypes(new RequestListener<Markertype>() {
								@Override
								public void onFinished(Collection<Markertype> results) {
									if (subdomain.equals("zgb")) 
										fillMapZgb();
									else 
										fillMapIssues();
								}
							});
					};

					@Override
					public void onError(String message, Throwable e) {
						Window.alert(message);
					}
				});
	}

	private void fillMapEE() {
		RootPanel.get("list").setVisible(false);
		ImageUrlLoader loader = ImageUrlLoader.getInstance();
		markerLayer.addMarkerIconRenderer(ElectricalSubstation.type, new DecentralizedGenerationIconRenderer());
		markerLayer.addMarkerPopupCreator(ElectricalSubstation.type, new ElectricalSubstationPopupCreator(mapObject, markerLayer));

		Collection<String> imgs = new ArrayList<String>();
		imgs.add("/mapicon/sun.png");
		imgs.add("/mapicon/trafo.png");
		imgs.add("/mapicon/windtourbine.png");
		loader.loadImages(imgs , new ImageLoadListener() {
			@Override
			public void onLoaded() {
				markerLayer.addMarkerPopupCreator(DecentralizedGeneration.type, new DecentralizedGenerationPopupCreator(mapObject, markerLayer));
				markerLayer.addMarkerIconRenderer(DecentralizedGeneration.type, new DecentralizedGenerationIconRenderer());
				
				Requests.loadEntities(IssuemapGwt.<DecentralizedGeneration, de.gmino.issuemap.shared.domain.DecentralizedGeneration>convertCollection(mapObject.getGenerations()), new RequestListener<DecentralizedGeneration>() {
					
					@Override
					public void onFinished(final Collection<DecentralizedGeneration> gens) {
						scatterPois(gens);
						
						generations = gens;
						showEEEntries(new RequestListener<Void>() {
							@Override
							public void onFinished(Collection<Void> nothing) {
								for(DecentralizedGeneration gen : gens)
									markerLayer.addPoi(gen);
							}
						});
					}
				});
				
				ZgbTools zgb = new ZgbTools(
						markerLayer) {
					@Override
					public void onAllRoutesShown() {
						System.out.println("All routes shown.");
					}
				};
				try {
					zgb.showGpxFromUrl("/gpx/110kv.gpx", "#FFFF00", false, 0);
					zgb.showGpxFromUrl("/gpx/220kv.gpx", "#FF8800", false, 0);
					zgb.showGpxFromUrl("/gpx/380kv.gpx", "#FF0000", false, 0);
					zgb.showGpxFromUrl("/gpx/zgb_aussengrenze_700.gpx",	"#000066", true, 0);
					} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void fillMapIssues() {
		if (mapObject.isHas_list()) {
			list = new IssueList_PopUp(mapObject, issueRenderer, markerLayer);
			RootPanel.get("list").add(list);

			loadIssuesToList();

		} else {
			RootPanel.get("list").getElement().getStyle().setDisplay(Display.NONE);
			setListVisible(false);
		}
		loadIssuesToMap();
	}

	private void fillMapZgb() {
		markerLayer
				.addMarkerPopupCreator(
						BicycleShop.type,
						new BicycleShopPopupCreator(
								mapObject,
								markerLayer));
		markerLayer
				.addMarkerPopupCreator(
						Route.type,
						new RoutePopupCreator(
								mapObject,
								markerLayer));

		ZgbTools zgb = new ZgbTools(
				markerLayer) {
			@Override
			public void onAllRoutesShown() {
				System.out.println("All routes shown, now loading bicycle shops and Issues.");
				showBicycleShops();
				fillMapIssues();
			}
		};
		zgb.showRoutes();
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

	public void loadIssuesToMap() {
		Requests.loadEntities(IssuemapGwt.<Issue, de.gmino.issuemap.shared.domain.Issue>convertCollection(mapObject.getIssues()), new RequestListener<Issue>() {
			@Override
			public void onFinished(Collection<Issue> results) {
				Scheduler scheduler = Scheduler.get();
				
				Comparator<Issue> latitudeCompare = new IssueLatitudeComparator();
				TreeSet<Issue> latitudeSortedIssues = new TreeSet<Issue>(latitudeCompare);
				latitudeSortedIssues.addAll(results);
				final ArrayList<Issue> filteredLatitudeIssues = new ArrayList<Issue>();
				for (final Issue i : latitudeSortedIssues) {
					if (!i.isDeleted())
						filteredLatitudeIssues.add(i);					
				}
				scheduler.scheduleIncremental(new AddIssuesCommand(filteredLatitudeIssues));
				
				counter = filteredLatitudeIssues.size();
				setCounter();
				counter = 0; // will be incremented by the deferred command and we don't want to count everything twice.
				
			}
		});
	}
	
	public void loadIssuesToList() {
		if(mapObject.isHas_list()){
		Collection<de.gmino.issuemap.shared.domain.Issue> issues = mapObject.getIssues();
		Requests.loadEntities(issues, new RequestListener<de.gmino.issuemap.shared.domain.Issue>() {
			@Override
			public void onFinished(Collection<de.gmino.issuemap.shared.domain.Issue> results) {
				Comparator<Issue> ratingCompare = new IssueRatingComparator();
				TreeSet<Issue> ratingSortedIssues = new TreeSet<Issue>(ratingCompare);
				ratingSortedIssues.addAll(IssuemapGwt.<Issue, de.gmino.issuemap.shared.domain.Issue>convertCollection(results));
				
				int count = 0;
				final ArrayList<Issue> filteredRatingIssues = new ArrayList<Issue>();
				for (final Issue i : ratingSortedIssues) {
					
					//testKeyValue(i);
					
					if (!i.isDeleted())
						filteredRatingIssues.add(i);
					if(count++ > 30)
						break;
				}
				list.updateData(filteredRatingIssues);
			}
		});
	}
}

	void testKeyValue(final Issue i) {
		System.out.println("Get Testfeld's Description: " + i.getValue("Testfeld").getDescription());
		System.out.println("Get Testfeld's Name: " + i.getValue("Testfeld").getName());
		System.out.println("Get Testfeld's Type: " + i.getValue("Testfeld").getType());
		
		System.out.println("Get Testfeld as String: " + i.getValue("Testfeld").getString());
		try {
			System.out.println("Get Testfeld as int: " + i.getValue("Testfeld").getInt());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Get Toastfeld as String: " + i.getValue("Toastfeld").getString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Set Testfeld as String.");
		i.getValue("Testfeld").setString("Inhalt");
		
		System.out.println("Get Testfeld as String: " + i.getValue("Testfeld").getString());
		
		try {
			System.out.println("Set Testfeld as int.");
			i.getValue("Testfeld").setInt(4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setCounter() {
		footer.setCounter(counter);
	}
		
	public void addFeedback_Button(){
		Feedback_Button feedback = new Feedback_Button(mapObject);
		RootPanel.get("feedback").add(feedback);
	}

	public Map getMap()
	{
		return mapObject;
	}

	public void setListVisible(boolean visible) {
		if(visible)
			mapView.setBorderRight(357 + GENERAL_POPUP_MARGIN);
		else
			mapView.setBorderRight(40 + GENERAL_POPUP_MARGIN);
	}
	
	public static <NewType, OldType> Collection<NewType> convertCollection(Collection<OldType> collection)
	{
		Collection<NewType> ret = new ArrayList<NewType>(collection.size());
		for(OldType old : collection)
			ret.add((NewType)old);
		return ret;
	}
	
	private void showEEEntries(final RequestListener<Void> listener)
	{
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/osm/powerNodesNoTowerZGB.osm");

		try {
			rb.sendRequest("", new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					String osmString = response.getText();
					parseAndShowEEOsm(osmString);
					listener.onFinished(null);
				}

				@Override
				public void onError(Request request, Throwable exception) {
					throw new RuntimeException("Error while fetching /werkstaetten.osm: "
							+ exception.getMessage(), exception);
				}
			});
		} catch (RequestException e) {
			Log.exception("Error showing Bycicle shops.", e);
		}
	}
	
	private void parseAndShowEEOsm(String osmString) {
		Document osmDom = XMLParser.parse(osmString);
		Collection<ElectricalSubstation> stations = new ArrayList<ElectricalSubstation>();
		NodeList shopList = osmDom.getElementsByTagName("node");
	  	for(int n = 0; n < shopList.getLength(); n++)
  		{
  			Element stationNode = (Element)shopList.item(n);
  			
  			boolean deleted  = "delete".equals(stationNode.getAttribute("action"));
  			if(deleted)
  				continue;
  			
  			double latitude  = Double.parseDouble(stationNode.getAttribute("lat"));
  			double longitude = Double.parseDouble(stationNode.getAttribute("lon"));
  			long id = Long.parseLong(stationNode.getAttribute("id"));
  			
  			String street = "", postcode = "", housenumber = "", city = "";
  			String type = null;
			
  			
			ElectricalSubstation station = new ElectricalSubstation(Math.abs(id));
			station.preventNulls();
			station.setLocation(new LatLon(latitude, longitude));
			
		    NodeList tagList = stationNode.getChildNodes();
			for(int i = 0; i < tagList.getLength(); i++)
		    {
				
				Node tagNode = tagList.item(i);
				if (tagNode instanceof Element) {
					Element tagElement = (Element) tagNode;
					if(tagElement.getTagName().equals("tag"))
					{
			  			String key   = tagElement.getAttribute("k");
						String value = tagElement.getAttribute("v");
						if(key.equals("name"))
							station.setTitle(value);
						
						if(key.equals("addr:street"))
							street = value;
						if(key.equals("addr:postcode"))
							postcode = value;
						if(key.equals("addr:housenumber"))
							housenumber = value;
						if(key.equals("addr:city"))
							city = value;
						if(key.equals("power"))
							type=value;
							
					}
				}
		    }
			
			station.setAddress(new Address(station.getTitle(), street, housenumber, postcode, city, ""));
			
			if(type.equals("sub_station")) 
			{
				station.setRandomColor();
				markerLayer.addPoi(station);
				stations.add(station);
			}
  		}
	  	
	  	for(DecentralizedGeneration gen : generations)
	  	{
	  		double minDistMeter = 100000;
	  		ElectricalSubstation nearestStation = null;
	  		for(ElectricalSubstation station : stations)
	  		{
	  			double dist = gen.getLocation().getDistanceTo(station.getLocation()).getMeters();
	  			if(dist < minDistMeter)
	  			{
	  				minDistMeter = dist;
	  				nearestStation = station;
	  			}
	  		}
	  		if(nearestStation != null)
	  		{
	  			gen.station = nearestStation;
	  			
	  			if(gen.getUnitType().equals("pv"))
	  			{
	  				nearestStation.sunPowerSum += gen.getPower();
	  				nearestStation.numberOfSunUnits++;
	  			}
	  			else
	  			{
	  				nearestStation.windPowerSum += gen.getPower();
	  				nearestStation.numberOfWindUnits++;
	  			}
	  			//System.out.println(gen.getTitle() + " has nearest station " + nearestStation.getTitle() + " with color " + nearestStation.color);
	  		}
	  	}
	}
	
	private void scatterPois(Collection<? extends Poi> pois) {
		double minDistMeter;

		for(Poi subjectPoi : pois)
		{
			if(subjectPoi instanceof DecentralizedGeneration && ((DecentralizedGeneration)subjectPoi).getUnitType().equals("wk"))
				minDistMeter = 200;
			else
				minDistMeter = 20;
			
			double angle = Math.random() * Math.PI * 2.0;
			double dLat = Math.sin(angle);
			double dLon = Math.cos(angle);
			LatLon subLoc = subjectPoi.getLocation();
			boolean tooClose;
			do
			{
				tooClose = false;
				for(Poi objectPoi : pois)
				{
					final double distMeter = subLoc.getDistanceTo(objectPoi.getLocation()).getInMeter();
					if(distMeter < minDistMeter)
					{
						tooClose = true;
						break;
					}
				}
				if(tooClose)
					subLoc = new LatLon(subLoc.getLatitude() + minDistMeter * 0.000009259 * dLat, subLoc.getLongitude() + minDistMeter * 0.000009259 * dLon);
			} while(tooClose);
			subjectPoi.setLocation(subLoc);
		}
	}
}
