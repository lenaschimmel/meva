package de.gmino.checkin.client;

import java.io.IOException;

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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.gmino.checkin.client.domain.Shop;

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
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();

				// Then, we send the input to the server.

				// Send request to server and catch any errors.
				RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
						"http://127.0.0.1:8888/jsonserver?facebookId="
								+ URL.encode(textToServer));

				try {
					Request request = builder.sendRequest(null,
							new RequestCallback() {
								public void onError(Request request,
										Throwable exception) {
									displayError("Couldn't retrieve JSON");
								}

								public void onResponseReceived(Request request,
										Response response) {
									if (200 == response.getStatusCode()) {

										 
										JsonObject json = GwtSystem.SYSTEM.parse(response.getText()).asObject();

										
										Shop shop;
										try {
											// TODO solchen Code gibts nicht! Direkte Konstruktoraufrufe, wo kommen wir da hin?! 
											shop =  new Shop(0);
											shop.deserializeJson(json);
											serverResponseLabel
											.setText("Hier gibts nichts zu sehen, außer "
													+ shop.getTitle() + "!");
											

											sendButton.setEnabled(false);
											
											dialogBox.setText("Remote Procedure Call");
											serverResponseLabel.removeStyleName("serverResponseLabelError");
											dialogBox.center();
											closeButton.setFocus(true);
											
										} catch (IOException e) {
											e.printStackTrace();
											displayError("Couldn't parse Shop ("
													+ response.getStatusText()
													+ ")");
										}
										
										
										

									} else {
										displayError("Couldn't retrieve JSON ("
												+ response.getStatusText()
												+ ")");
									}
								}
							});
				} catch (Exception e) {
					e.printStackTrace();
					displayError("Couldn't retrieve JSON: " + e.toString());
				}


			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
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
