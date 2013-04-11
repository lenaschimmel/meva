package de.gmino.issuemap.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.map.OpenLayersMapView;
import de.gmino.geobase.shared.map.MarkerLayer;
import de.gmino.issuemap.shared.EntityFactoryImpl;
import de.gmino.issuemap.shared.FieldVerifier;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Util;
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
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private OpenLayersMapView map;

	private MarkerLayer markerLayer;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Util.setImpl(new UtilClient());
		
		Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://"+Location.getHost()));

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
		Image logo = new Image("http://gmino.de/static/img/map_logos/"+ domainSplit[0]+"_logo.png");
		logo.setHeight("80px");
		

		// We can add style names to widgets
		searchButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("searchFieldContainer").add(searchField);
		RootPanel.get("searchButtonContainer").add(searchButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("logoContainer").add(logo);

		// Focus the cursor on the search field when the app loads
		searchField.setFocus(true);
		searchField.selectAll();


	}
}
