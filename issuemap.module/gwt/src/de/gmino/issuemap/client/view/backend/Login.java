package de.gmino.issuemap.client.view.backend;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.BaseApp;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public class Login extends Composite {
	
	BaseApp app;

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	public Login(BaseApp currentInstance) {
		initWidget(uiBinder.createAndBindUi(this));
		this.app = currentInstance;
	}
	
	@UiField
	public TextBox user;
	@UiField
	public TextBox password;
	@UiField
	public Button loginButton;
	
	
	private void doLogin() {
		Requests.login(user.getText(), password.getText(), new RequestListener<Long>() {
			@Override
			public void onNewResult(Long loggedInUserId) {
				app.onLogin();
			}
		});
	}
	
	@UiHandler("loginButton")
	void onLoginButtonClicked(ClickEvent e) {
		doLogin();
	}
	@UiHandler("password")
	void onKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			doLogin();
		}
	}
	

	@UiHandler("registerButton")
	void onRegisterButtonClicked(ClickEvent e) {
		Register signin= new Register(this);
		this.setVisible(false);
		RootPanel.get("parent").add(signin);
	}
	
	public String getUser(){
		return user.getText();
	}
	
	public String getPassword(){
		return password.getText();
	}


}
