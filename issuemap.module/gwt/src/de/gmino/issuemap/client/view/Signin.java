package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.shared.domain.Address;
import de.gmino.issuemap.client.domain.User;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class Signin extends Composite {

	private Login login;
	
	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	interface LoginUiBinder extends UiBinder<Widget, Signin> {
	}

	public Signin(Login login) {
		initWidget(uiBinder.createAndBindUi(this));
		this.login=login;
	}
	
	@UiField
	TextBox user;
	@UiField
	TextBox password1;
	@UiField
	TextBox mail;
	@UiField
	TextBox firstname;
	@UiField
	TextBox name;
	@UiField
	TextBox street;
	@UiField
	TextBox housenumber;
	@UiField
	TextBox zip;
	@UiField
	TextBox city;
	@UiField
	Button next;
	@UiField
	Button signinButton;
	@UiField
	VerticalPanel inputWrapper;
	
	@UiHandler("signinButton")
	void onClick(ClickEvent e) {
//		Requests.getNewEntity(User.type, new RequestListener<User>() {
//			@Override
//			public void onNewResult(User newUser) {
//				newUser.setUserName(user.getText());
//				newUser.setPassword(password1.getText());
//				newUser.setEmail(mail.getText());
//				newUser.setPostal_address(new Address(firstname.getText() + " " + name.getText(), street.getText(), housenumber.getText(), zip.getText(), city.getText(), ""));
//				Requests.saveEntity(newUser, null);
//			}
//		});
		
		inputWrapper.clear();

		inputWrapper.add(new Label("In Kürze erhalten Sie eine Mail mit dem Aktivierungs-Link für Ihren Account"));
		next.setVisible(true);
		signinButton.setVisible(false);
		
		

	}
	@UiHandler("next")
	void onClick2(ClickEvent e) {
		this.removeFromParent();
		login.setVisible(true);
		
		
	}


}
