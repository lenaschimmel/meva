package de.gmino.issuemap.client;

import javax.swing.event.EventListenerList;

import com.gargoylesoftware.htmlunit.javascript.host.Event;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
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
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel header =  new HorizontalPanel();
	private HorizontalPanel footer = new HorizontalPanel();
	private Image maplogo = new Image();
	private Label title = new Label();

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
		markerLayer = map.newMarkerLayer("cycleway_problems_bs");
		map.addLayer(markerLayer);

		// Create header & bottom panels
		header.setStyleName("header");
		footer.setStyleName("footer");
		mainPanel.add(header);
		mainPanel.add(footer);
		
		//create buttons, textfields & logo
		final Button infoButton = new Button();
		infoButton.setHTML("<img id='icon' src='icons/info.png'></img>");
		infoButton.setStyleName("button");
		
		final Button searchButton = new Button();
		searchButton.setHTML("<img id='icon' src='icons/search.png'></img>");
		searchButton.setStyleName("button");
		
		final TextBox searchField = new TextBox();
		searchField.setText("Straßenname");
		searchField.setStyleName("searchField");
		
		final Button listButton = new Button();
		listButton.setHTML("<img id='icon' src='icons/chart_bar.png'></img>");
		listButton.setStyleName("button");
		listButton.addStyleName("button_list");
		
		final Button statsearchButton = new Button();
		statsearchButton.setHTML("<img id='icon' src='icons/list.png'></img>");
		statsearchButton.setStyleName("button");
		
		final Button preferencesearchButton = new Button();
		preferencesearchButton.setHTML("<img id='icon' src='icons/preferences.png'></img>");
		preferencesearchButton.setStyleName("button");
		
		final Image gminoLogo= new Image("logo_gmino.png");

		
		// Add Items to Header-Bar
		header.setWidth("100%");
		header.add(maplogo);
		header.add(title);
		header.add(infoButton);
		header.add(searchField);
		header.add(searchButton);
		header.setCellWidth(maplogo, "30%");
		header.setCellWidth(title, "38%");
		header.setCellWidth(infoButton, "2%");
		header.setCellWidth(searchField, "25%");
		header.setCellWidth(searchButton, "5%");
		header.setCellHorizontalAlignment(infoButton, HasHorizontalAlignment.ALIGN_LEFT);
		header.setCellHorizontalAlignment(searchField, HasHorizontalAlignment.ALIGN_RIGHT);


		// Add Items to Footer-Bar		
		footer.setWidth("100%");
		footer.add(listButton);
		footer.add(statsearchButton);
		footer.add(preferencesearchButton);
		footer.add(gminoLogo);
		footer.setCellHorizontalAlignment(gminoLogo, HasHorizontalAlignment.ALIGN_RIGHT);
		footer.setCellWidth(statsearchButton, "30px");
		footer.setCellWidth(listButton, "30px");
		footer.setCellWidth(preferencesearchButton, "30px");
		
		//Add Header to RootPanel
		RootPanel.get("bar_top").add(header);
		RootPanel.get("bar_bottom").add(footer);
		




		
		// Focus the cursor on the search field when the app loads
		searchField.setFocus(true);
		searchField.selectAll();
		
		//fetch map-Objekt
		String[] domainSplit = Location.getHostName().split("\\.");
		String subdomain = domainSplit[0];
		mapRequests(subdomain);

	}
	void mapRequests(String subdomain) {
		// Then, we send the input to the server.
		// Request all shops near you
		EntityQuery q = new QueryMapBySubdomain(subdomain);
		Requests.getLoadedEntitiesByQuery(Map.type, q,
				new RequestListener<Map>() {

					public void onNewResult(Map result) {
						
						maplogo.setUrl(result.getLogo().getUrl());
						maplogo.setHeight("50px");
						title.setText(result.getTitle());
						title.getElement().getStyle().setColor(result.getColor());
						title.addStyleName("title");
						header.getElement().getStyle().setBorderColor(result.getColor());
						footer.getElement().getStyle().setBorderColor(result.getColor());
						map.setCenterAndZoom(result.getInitLocation(), result.getInitZoomlevel(), false);

						
						
						

						
						
					};

					@Override
					public void onError(String message, Throwable e) {
						Window.alert(message);
					}
				});
	}
}
