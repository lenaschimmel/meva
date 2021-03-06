package de.gmino.issuemap.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
import de.gmino.geobase.shared.domain.PoiInterface;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.issuemap.client.ImageUrlLoader.ImageLoadListener;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.DecentralizedGeneration;
import de.gmino.issuemap.client.domain.ElectricalSubstation;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.issuemap.client.domain.Poi;
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
import de.gmino.issuemap.client.view.Feedback_Button;
import de.gmino.issuemap.client.view.Footer;
import de.gmino.issuemap.client.view.Header;
import de.gmino.issuemap.client.view.popup.List_PopUp;
import de.gmino.issuemap.client.view.popup.Show_PopUp;
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

		private ArrayList<Poi> issues;
		private int i = 0;

		public AddIssuesCommand(ArrayList<Poi> issues) {
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

	private final class IssueLatitudeComparator implements Comparator<Poi> {
		@Override
		public int compare(Poi i1, Poi i2) {
			int latitudeComparision = -Double.compare(i1.getLocation()
					.getLatitude(), i2.getLocation().getLatitude());
			if (latitudeComparision != 0)
				return latitudeComparision;
			else
				return compareLong(i1.getId(), i2.getId());
		}
	}

	private final class IssueRatingComparator implements Comparator<Poi> {
		@Override
		public int compare(Poi i1, Poi i2) {
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

	public static OpenLayersMapView mapView;
	private static Map mapObject;
	private OpenLayersSmartLayer markerLayer;
	private Footer footer = new Footer();
	private Header header = new Header();
	
	Collection<DecentralizedGeneration> generations;
	Collection<Poi> pois;

	int counter = 0;

	private static IssuemapGwt instance;

	private IssueIconRenderer issueRenderer;

	private static final int GENERAL_POPUP_MARGIN = 20;

	private List_PopUp list;

	private String subdomain;

	public IssuemapGwt() {
		if (instance != null)
			throw new RuntimeException("Can't create multiple instances!");
		instance = this;
		pois = new ArrayList<Poi>();
	}

	public static IssuemapGwt getInstance() {
		return instance;
	}
	
	@Override
	public void onUncaughtException(Throwable e) {
		System.err.println("### UncaughtExceptionHandler ###");
		e.printStackTrace();
		Log.exception("### UncaughtExceptionHandler ###", e);
	}

	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(this);
		
		try {
			EntityFactory.setImplementations(new EntityFactoryImpl());
			Util.setImpl(new UtilClient());

			Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://" + Location.getHost() + "/"));

			String[] domainSplit = Location.getHostName().split("\\.");
			subdomain = domainSplit[0];
			if (subdomain.equalsIgnoreCase("www"))
				subdomain = domainSplit[1];

			// Create the map view
			mapView = new OpenLayersMapView("map", "mapquest");
			
			mapView.setBorders(30 + GENERAL_POPUP_MARGIN, 58 + GENERAL_POPUP_MARGIN, 357 + GENERAL_POPUP_MARGIN, 53 +GENERAL_POPUP_MARGIN);
			
			markerLayer = mapView.newSmartLayer("Issues", 12);
			mapView.setCenterAndZoom(new LatLon(20, 0), 0, false);
			issueRenderer = new IssueIconRenderer();
			markerLayer.addMarkerIconRenderer(Poi.type, issueRenderer);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.exception("Error in onModuleLoad caught.", e);
		}
	}

	public String getSubdomain() {
		return subdomain;
	}

	void mapRequest() {
		EntityQuery q = new QueryMapBySubdomain(subdomain);
		Requests.getLoadedEntitiesByQuery(Map.type, q, new RequestListener<Map>() {

			@SuppressWarnings("unchecked")
			public void onNewResult(final Map map) {

				mapObject = map;
				if (mapObject.isCreate())
					addDblClickListenerToMapView();
				markerLayer.setzoomThreshold(map.getInitZoomlevel() - 3);

				addFeedback_Button();
				markerLayer.addMarkerPopupCreator(Poi.type, new IssuePopupCreator(map, markerLayer));

				header.setFrontendDesign(map);

				footer.setMap(map);
				Window.setTitle(map.getTitle());

				mapView.newMapLayer(map.getLayer());
				mapView.setZoom(mapObject.getInitZoomlevel());

				Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {

					@Override
					public boolean execute() {
						mapView.setCenter(mapObject.getInitLocation(), false);
						return false;
					}
				}, 1000);

				header.setFrontendDesign(mapObject);
				footer.setDesign(mapObject.getPrimary_color());

				map.loadMarkertypes(new RequestListener<Markertype>() {
					@Override
					public void onFinished(Collection<Markertype> results) {
						if (subdomain.equals("zgb"))
							fillMapZgb();
						else {
							showOrHideList();
							loadIssues();
						}
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
	
	public void showOrHideList() {
		if (mapObject.isHas_list()) {
			list = new List_PopUp(mapObject, issueRenderer, markerLayer);
			RootPanel.get("list").add(list);
		} else {
			RootPanel.get("list").getElement().getStyle().setDisplay(Display.NONE);
			updateListMargin(false);
		}
	}

	private void fillMapZgb() {
		markerLayer.addMarkerPopupCreator(BicycleShop.type,	new BicycleShopPopupCreator(mapObject, markerLayer));
		markerLayer.addMarkerPopupCreator(Route.type, new RoutePopupCreator(mapObject, markerLayer));

		ZgbTools zgb = new ZgbTools(markerLayer) {
			@Override
			public void onAllRoutesShown() {
				System.out.println("All routes shown, now loading bicycle shops and Issues.");
				showBicycleShops();
				showOrHideList();
				loadIssues();
			}
		};
		zgb.showRoutes();
	}
	
	static void setMapPosition(LatLon position) {
		mapView.setCenter(position, true);
	}

	public void addMarker(Poi poi) {
		pois.add(poi);
		markerLayer.addPoi(poi);
		counter++;
		fillList();
	}

	public void deleteMarker(Poi poi) {
		pois.remove(poi);
		markerLayer.removePoi(poi);
		counter--;
		fillList();
	}

	public void showAllPoisOnMap() {
		Scheduler scheduler = Scheduler.get();
		
		Comparator<Poi> latitudeCompare = new IssueLatitudeComparator();
		TreeSet<Poi> latitudeSortedIssues = new TreeSet<Poi>(latitudeCompare);
		latitudeSortedIssues.addAll(pois);
		final ArrayList<Poi> filteredLatitudeIssues = new ArrayList<Poi>();
		for (final Poi i : latitudeSortedIssues) {
			if (!i.isDeleted())
				filteredLatitudeIssues.add(i);					
		}
		scheduler.scheduleIncremental(new AddIssuesCommand(filteredLatitudeIssues));
		
		counter = filteredLatitudeIssues.size();
		updateCounter();
		counter = 0; // will be incremented by the deferred command and we don't want to count everything twice.
	}
	
	public void loadIssues() {
		pois = IssuemapGwt.<Poi, de.gmino.issuemap.shared.domain.Poi>convertCollection(mapObject.getIssues());
		Requests.loadEntities(pois, new RequestListener<Poi>() {
			@Override
			public void onFinished(Collection<Poi> results) {
				showAllPoisOnMap();	
				fillList();
			}
		});
	}
	
	public void fillList() {
		if(!mapObject.isHas_list())
			return;
		
		Comparator<Poi> ratingCompare = new IssueRatingComparator();
		TreeSet<Poi> ratingSortedIssues = new TreeSet<Poi>(ratingCompare);
		ratingSortedIssues.addAll(pois);
		
		int count = 0;
		final ArrayList<Poi> filteredRatingIssues = new ArrayList<Poi>();
		for (final Poi i : ratingSortedIssues) {
			if (!i.isDeleted())
				filteredRatingIssues.add(i);
			if(count++ > 30)
				break;
		}
		list.updateData(filteredRatingIssues);
	}
	
	public void updateCounter() {
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

	public void updateListMargin(boolean listVisible) {
		if(listVisible)
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
	  		}
	  	}
	}
	
	private void scatterPois(Collection<? extends PoiInterface> pois) {
		double minDistMeter;

		for(PoiInterface subjectPoi : pois)
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
				for(PoiInterface objectPoi : pois)
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
	
	public void addDblClickListenerToMapView(){
		mapView.addEventListener(Event.dblclick, new MapListener() {

			@Override
			public void onEvent(final LatLon location, Event event) {
				DivElement div = ((OpenLayersMapView) mapView).createPopup(
						location, "centerPopup", 1, 1);
				final Show_PopUp popUp = new Show_PopUp(mapObject, markerLayer);
				popUp.createNewPoi(location);
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
		});
	}
}
