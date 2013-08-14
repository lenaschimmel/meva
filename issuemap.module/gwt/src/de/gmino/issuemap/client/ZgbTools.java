package de.gmino.issuemap.client;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.UnsafeNativeLong;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.shared.domain.Route;
import de.gmino.meva.shared.Log;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public abstract class ZgbTools {
	
	private OpenLayersSmartLayer smartLayer;
	private JavaScriptObject mapJso;
	private JavaScriptObject layerJso;
	private int routesToShow;

	public ZgbTools(OpenLayersSmartLayer smartLayer) {
		super();
		this.smartLayer = smartLayer;
		this.mapJso = smartLayer.getMapView().getMapJso();
		this.layerJso = smartLayer.getLayerJso();
	}
	
	public void showRoutes() {
		
		Collection<Long> ids = new ArrayList<Long>();
		for(long l = 1; l <= 8; l++)
			ids.add(l);
		
		Requests.getLoadedEntitiesById(Route.type, ids , new RequestListener<Route>() {
			@Override
			public void onNewResult(Route route) {
				showRoute(route);
			}
		});
		
		try {
			showGpxFromUrl("/gpx/zgb_aussengrenze_2000.gpx",	"#000066", true, 999);
		} catch (RequestException e1) {
			Log.exception("Error showing GPX from URL.", e1);
		}
	}

	public void showBicycleShops() {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/werkstaetten.osm");

		try {
			rb.sendRequest("", new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					String osmString = response.getText();
					parseAndShowOsm(osmString);
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
	
	private void showRoute(final Route route) { // String url, final String color, final boolean dash, final long poiId) throws RequestException {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, route.getGpxUrl());
		routesToShow++;
		
		try {
			rb.sendRequest("", new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					String gpxString = response.getText();
					parseAndShowGpx(route.getColor(), false, gpxString, route.getId());
					smartLayer.addPoi(route);
					routesToShow--;
					System.out.println("Shown route " + route.getTitle() + ", " + routesToShow + " routes left.");

					if(routesToShow == 0)
						onAllRoutesShown();
				}

				@Override
				public void onError(Request request, Throwable exception) {
					throw new RuntimeException("Error while fetching " + route.getGpxUrl() + ": "
							+ exception.getMessage(), exception);
				}
			});
		} catch (RequestException e) {
			Log.exception("Error showing routes.", e);
		}
	}
	
	public void showGpxFromUrl(final String url, final String color, final boolean dash, final long poiId) throws RequestException {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
		routesToShow++;
		rb.sendRequest("", new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				String gpxString = response.getText();
				parseAndShowGpx(color, dash, gpxString, poiId);
				routesToShow--;
				System.out.println("Shown route " + url + ", " + routesToShow + " routes left.");
			//	if(routesToShow == 0)
			//		onAllRoutesShown();
			}

			@Override
			public void onError(Request request, Throwable exception) {
				throw new RuntimeException("Error while fetching " + url + ": "
						+ exception.getMessage(), exception);
			}
		});
	}

	private void parseAndShowGpx(final String color,
			final boolean dash, String gpxString, long poiId) {
		Document gxpDom = XMLParser.parse(gpxString);
		
		NodeList routeListe = gxpDom.getElementsByTagName("rte");
	  	if(routeListe.getLength() >= 1)
	  	{
	  		for(int n = 0; n < routeListe.getLength(); n++)
	  		{
	  			JavaScriptObject pointList = nCreatePointList();
		  		Node route = routeListe.item(n);
			    NodeList routeChildNodes = route.getChildNodes();
				for(int i = 0; i < routeChildNodes.getLength(); i++)
			    {
					Node childNode = routeChildNodes.item(i);
					if (childNode instanceof Element) {
						Element childElement = (Element) childNode;
						if(childElement.getTagName().equals("rtept"))
						{
							double latitude  = Double.parseDouble(childElement.getAttribute("lat"));
							double longitude = Double.parseDouble(childElement.getAttribute("lon"));
							nAddPoint(pointList, latitude, longitude, mapJso);
						}
					}
			    }
				if(!dash)
					nCreateLineString(pointList, mapJso, layerJso, "#000000", 5, dash, poiId);
				nCreateLineString(pointList, mapJso, layerJso, color, 3, dash, poiId);
	  		}
	  	}
	  	else
	  	{
	  		routeListe = gxpDom.getElementsByTagName("trkseg");
	  		for(int n = 0; n < routeListe.getLength(); n++)
	  		{
	  			JavaScriptObject pointList = nCreatePointList();
		  		Node route = routeListe.item(n);
			    NodeList routeChildNodes = route.getChildNodes();
				for(int i = 0; i < routeChildNodes.getLength(); i++)
			    {
					Node childNode = routeChildNodes.item(i);
					if (childNode instanceof Element) {
						Element childElement = (Element) childNode;
						if(childElement.getTagName().equals("trkpt"))
						{
							double latitude  = Double.parseDouble(childElement.getAttribute("lat"));
							double longitude = Double.parseDouble(childElement.getAttribute("lon"));
							nAddPoint(pointList, latitude, longitude, mapJso);
						}
					}
			    }
				if(!dash)
					nCreateLineString(pointList, mapJso, layerJso, "#000000", 5, dash, poiId);
				nCreateLineString(pointList, mapJso, layerJso, color, 3, dash, poiId);
	  		}
	  		
	  	}
	}

	private void parseAndShowOsm(String osmString) {
		Document osmDom = XMLParser.parse(osmString);
		
		NodeList shopList = osmDom.getElementsByTagName("node");
	  	for(int n = 0; n < shopList.getLength(); n++)
  		{
  			Element shopNode = (Element)shopList.item(n);
  			
  			boolean deleted  = "delete".equals(shopNode.getAttribute("action"));
  			if(deleted)
  				continue;
  			
  			double latitude  = Double.parseDouble(shopNode.getAttribute("lat"));
  			double longitude = Double.parseDouble(shopNode.getAttribute("lon"));
  			long id = Long.parseLong(shopNode.getAttribute("id"));
  			
  			String street = "", postcode = "", housenumber = "", city = "";
			
  			
			BicycleShop shop = new BicycleShop(Math.abs(id));
			shop.preventNulls();
			shop.setLocation(new LatLon(latitude, longitude));
			
		    NodeList tagList = shopNode.getChildNodes();
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
							shop.setTitle(value);
						if(key.equals("contact:email"))
							shop.setEmail(value);
						if(key.equals("contact:phone"))
							shop.setPhone(value);
						if(key.equals("contact:website"))
							shop.setWebsite(value);
						if(key.equals("opening_hours"))
							shop.setOpeningHours(value);
						if(key.equals("service:bicycle:repair") && value.equals("yes"))
							shop.setRepairService(true);

						if(key.equals("addr:street"))
							street = value;
						if(key.equals("addr:postcode"))
							postcode = value;
						if(key.equals("addr:housenumber"))
							housenumber = value;
						if(key.equals("addr:city"))
							city = value;
					}
				}
		    }
			
			shop.setAddress(new Address(shop.getTitle(), street, housenumber, postcode, city, ""));
			
			smartLayer.addPoi(shop);
  		}
	  	
	}
	
	private native JavaScriptObject nCreatePointList()
	/*-{
		return [];
	}-*/;

	private native void nAddPoint(JavaScriptObject pointList, double latitude,
			double longitude, JavaScriptObject mapJso)
	/*-{
		var newPoint = new $wnd.OpenLayers.Geometry.Point(longitude, latitude);
		newPoint = newPoint.transform(mapJso.pro1, mapJso.pro2);
		pointList.push(newPoint);
	}-*/;

	@UnsafeNativeLong
	private native void nCreateLineString(JavaScriptObject pointList,
			JavaScriptObject mapJso, JavaScriptObject layer, String color, int width, boolean dash, long poiId)
	/*-{
		var layer_style = $wnd.OpenLayers.Util.extend({},
				$wnd.OpenLayers.Feature.Vector.style['default']);
		layer_style.fillOpacity = 0.5;
		layer_style.graphicOpacity = 1;

		var style = $wnd.OpenLayers.Util.extend({}, layer_style);
		style.strokeColor = color;
		style.fillColor = "blue";
		style.graphicName = "star";
		style.pointRadius = 10;
		style.strokeWidth = width;
		style.rotation = 45;
		style.strokeLinecap = "round";
		style.strokeOpacity = 1;
		if(dash)
			style.strokeDashstyle = "dash";

		var lineStringBlack = new $wnd.OpenLayers.Geometry.LineString(pointList);
		var lineFeatureBlack = new $wnd.OpenLayers.Feature.Vector(
				lineStringBlack, null, style);
		lineFeatureBlack.poiId = poiId;
		layer.addFeatures([ lineFeatureBlack ]);

	}-*/;

	public abstract void onAllRoutesShown();
}
