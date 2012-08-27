package de.gmino.checkin.client;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.itemscript.core.gwt.GwtSystem;
import org.itemscript.core.values.JsonObject;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.gmino.checkin.client.domain.Coupon;
import de.gmino.checkin.client.domain.Shop;
import de.gmino.checkin.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.checkin.client.request.QueryNearbyShops;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.shared.domain.Duration;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CheckinGwt implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	private Label errorLabel;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		EntityFactory.setImplementations(new EntityFactoryImpl());
		Requests.setImplementation(new NetworkRequestsImplAsyncJson(Util
				.getBaseUrl()));

		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				doExampleRequests();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doExampleRequests();
				}
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	void doExampleRequests() {
		// First, we validate the input.
		errorLabel.setText("");

		// Then, we send the input to the server.
		// Request all shops near you
		final LatLon myLocation = new LatLon(52.2723, 10.53547);
		Query q = new QueryNearbyShops(myLocation, 5000, 20);
		Requests.getLoadedEntitiesByQuery(Shop.type, q,
				new RequestListener<Shop>() {
					@Override
					public void onFinished(final Collection<Shop> shops) {
						// prepare a set of all the Coupons that we should
						// pre-load, because the shops may contain unloaded
						// coupons.
						Set<Coupon> couponsToLoad = new HashSet<Coupon>();

						int i = 0;
						for (Shop shop : shops) {
							String message = shop.getTitle()
									+ "("
									+ shop.getLocation().getDistanceTo(
											myLocation) + " entfernt)";
							Window.alert(message);
							couponsToLoad.addAll(shop.getCoupons());
						}

						Requests.loadEntities(couponsToLoad,
								new RequestListener<Coupon>() {
									@Override
									public void onFinished(
											Collection<Coupon> coupons) {
										StringBuilder sb = new StringBuilder(
												"Coupons: ");
										boolean first = true;
										for (Coupon c : coupons) {
											if (!first)
												sb.append(", ");
											sb.append(c.getTitle());
											first = false;
										}
										Window.alert(sb.toString());
									}
								});

						Requests.getNewEntities(Coupon.type, 1,
								new RequestListener<Coupon>() {
									@Override
									public void onFinished(
											Collection<Coupon> results) {
										Coupon c = results.iterator().next();
										c.setTitle("Einmal freuen gratis!");
										c.setDescription("Dieser Coupon wurde per GWT erstellt. Das sollte doch aussreichen, um sich zu freuen!");
										c.setShop(shops.iterator().next());
										c.setDuration(new Duration(3600000));
										c.setImage(new ImageUrl(
												"http://img.fotocommunity.com/Emotionen/Freude/Joy-Freude-a18796755.jpg"));

										Collection<Entity> entities = new LinkedList<Entity>();
										entities.add(c);
										Requests.saveEntities(entities, null);
									}
								});
					}

					@Override
					public void onError(String message, Throwable e) {
						Window.alert(message);
					}
				});
	}

	/**
	 * If can't get JSON, display error message.
	 * 
	 * @param error
	 */
	private void displayError(String error) {
		errorLabel.setText("Error: " + error);
		errorLabel.setVisible(true);
	}
}
