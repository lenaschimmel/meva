package de.gmino.issuemap.client.view.backend;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.MasterBackend;

public class Login extends Composite {
	
	MasterBackend currentInstance;

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	public Login(MasterBackend currentInstance) {
		initWidget(uiBinder.createAndBindUi(this));
		this.currentInstance=currentInstance;
	}
	
	@UiField
	public TextBox user;
	@UiField
	public TextBox password;
	@UiField
	public Button loginButton;
	
	@UiHandler("loginButton")
	void onLoginButtonClicked(ClickEvent e) {
		
		if(password.getText().equals("caputisimo")&&user.getText().equals("gmino")) currentInstance.onLogin();
	}
	
	@UiHandler("password")
	void onKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			if(password.getText().equals("caputisimo")&&user.getText().equals("gmino")) currentInstance.onLogin();
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
