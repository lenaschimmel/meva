package de.gmino.issuemap.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.map.OpenLayersMapView;
import de.gmino.geobase.shared.map.MarkerLayer;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.issuemap.client.EntityFactoryImpl;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
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

	private String subdomain;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());

		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"
				+ Location.getHost()+"/"));

		// Create the map
		map = new OpenLayersMapView("map");
		map.setCenterAndZoom(new LatLon(52.27617, 10.53216), 15, false);
		markerLayer = map.newMarkerLayer("cycleway_problems_bs");
		map.addLayer(markerLayer);

		final Button searchButton = new Button("Send");
		final TextBox searchField = new TextBox();
		searchField.setText("Rebenring");
		final Label errorLabel = new Label();
		String[] domainSplit = Location.getHostName().split("\\.");
		subdomain = domainSplit[0];

		// We can add style names to widgets
		searchButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("searchFieldContainer").add(searchField);
		RootPanel.get("searchButtonContainer").add(searchButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		

		// Focus the cursor on the search field when the app loads
		searchField.setFocus(true);
		searchField.selectAll();
		
		doExampleRequests();

	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	void doExampleRequests() {
		// Then, we send the input to the server.
		// Request all shops near you
		EntityQuery q = new QueryMapBySubdomain(subdomain);
		Requests.getLoadedEntitiesByQuery(Map.type, q,
				new RequestListener<Map>() {

					public void onNewResult(Map result) {
						
						Image logo = new Image(""+ result.getLogo());
						logo.setHeight("80px");
						RootPanel.get("logoContainer").add(logo);

						
						
					};

					@Override
					public void onError(String message, Throwable e) {
						Window.alert(message);
					}
				});
	}
}
