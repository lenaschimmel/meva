package de.gmino.issuemap.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.map.OpenLayersMapView;
import de.gmino.geobase.shared.map.MarkerLayer;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.request.QueryMapBySubdomain;
import de.gmino.issuemap.shared.EntityFactoryImpl;
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

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private OpenLayersMapView map;
	private MarkerLayer markerLayer;
	private String subdomain;
	private VerticalPanel verticalPanel = new VerticalPanel();
	private HorizontalPanel header =  new HorizontalPanel();
	private HorizontalPanel footer = new HorizontalPanel();

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

		//create header & bottom panels
		//TODO: Tillmann machts (aufteilung mit panels)
		verticalPanel.add(header);
		//not yet, we need a map widget first verticalPanel.add(map);
		verticalPanel.add(footer);
		
		//create buttons & textfields
		//nix ;)
		//TODO: Tillmann machts 
		
		final Button searchButton = new Button("Send");
		final TextBox searchField = new TextBox();
		searchField.setText("Straßenname");
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
		
		//TONI: methode auch hier umbenennen
		doExampleRequests();

	}

	//TONI: wieso wird die Anfrage nach einem Logo im example request gemacht??
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
