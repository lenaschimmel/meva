package de.gmino.issuemap.client;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.geobase.client.map.OpenLayersMapView;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.map.Event;
import de.gmino.geobase.shared.map.MapListener;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Markertype;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.issuemap.client.view.CreateEvent_PopUp;
import de.gmino.issuemap.client.view.CreateIssue_PopUp;
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

	private static OpenLayersMapView mapView;
	private static Map mapObject;
	private static TreeMap<Issue, DivElement> divByIssue = new TreeMap<Issue, DivElement>();
	private OpenLayersSmartLayer markerLayer;
	private Footer footer = new Footer();
	private Header header = new Header();

	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());

		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"
				+ Location.getHost() + "/"));

		// Create the map
		mapView = new OpenLayersMapView("map", "mapquest");
		markerLayer = mapView.newSmartLayer("Issues");
		markerLayer.addMarkerIconRenderer(Issue.type, new IssueIconRenderer());
		mapView.setCenterAndZoom(new LatLon(20, 0), 0, false);

		// Add Header to RootPanel
		RootPanel.get("bar_top").add(header);

		RootPanel.get("bar_bottom").add(footer);

		// Add create-PopUp by Double-Click
		mapView.addEventListener(Event.dblclick, new MapListener() {

			@Override
			public void onEvent(LatLon location, Event event) {
				DivElement div = ((OpenLayersMapView) mapView).createPopup(
						location, "centerPopup", 100, 100);
				div.getStyle().setOverflow(Overflow.VISIBLE);
				div.getStyle().setBackgroundColor("transparent");
				div.getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
				div.getParentElement().getStyle().setBackgroundColor("transparent");
				div.getParentElement().getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
				div.getParentElement().getParentElement().getStyle().setBackgroundColor("transparent");
				if (mapObject.getMapTyp().equals("Issue")) {
					CreateIssue_PopUp popUp = new CreateIssue_PopUp(mapObject, location, markerLayer);
					HTMLPanel.wrap(div).add(popUp);
				} else {
					CreateEvent_PopUp popUp = new CreateEvent_PopUp(mapObject, location, markerLayer);
					HTMLPanel.wrap(div).add(popUp);
				}

			}
		});

		// fetch map-Objekt
		String[] domainSplit = Location.getHostName().split("\\.");
		String subdomain = domainSplit[0];
		mapRequest(subdomain);

	}

	void mapRequest(String subdomain) {
		EntityQuery q = new QueryMapBySubdomain(subdomain);
		Requests.getLoadedEntitiesByQuery(Map.type, q,
				new RequestListener<Map>() {

					@SuppressWarnings("unchecked")
					public void onNewResult(Map map) {
						mapObject = map;
						markerLayer.addMarkerPopupCreator(Issue.type,
								new IssuePopupCreator(map, markerLayer));

						header.setMap(map);
						Window.setTitle(map.getHeaderText());
						mapView.setCenterAndZoom(mapObject.getInitLocation(),
								mapObject.getInitZoomlevel(), false);
						header.setDesign(mapObject.getLogo().getUrl(),
								mapObject.getTitle(), mapObject.getColor());
						footer.setDesign(mapObject.getColor());

						map.loadMarkertypes(new RequestListener<Markertype>() {
							@Override
							public void onFinished(
									Collection<Markertype> results) {

								ImageUrlLoader.getInstance().setOnLoadListener(
										new RequestListener<Void>() {
											@Override
											public void onFinished(
													Collection<Void> results) {
												Collection<Issue> issues = mapObject
														.getIssues();
												Requests.loadEntities(
														issues,
														new RequestListener<Issue>() {
															@Override
															public void onFinished(
																	Collection<Issue> results) {
																Comparator<Issue> compare = new IssueLatitudeComparator();
																TreeSet<Issue> sortedIssues = new TreeSet<Issue>(
																		compare);
																sortedIssues
																		.addAll(results);
																for (Issue i : sortedIssues) {
																	if (i.isDeleted())
																		continue;
																	addMarker(i);
																}
															}
														});
											}
										});
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

	public void addMarker(Issue nIssue) {
		markerLayer.addPoi(nIssue);

	}

	public void deleteMarker(Issue nIssue) {
		markerLayer.removePoi(nIssue);

	}

}
