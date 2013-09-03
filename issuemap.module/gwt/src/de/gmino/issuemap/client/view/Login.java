package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
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
	TextBox user;
	@UiField
	TextBox password;
	
	@UiHandler("loginButton")
	void onClick(ClickEvent e) {
		

		currentInstance.onLogin();
	}
	
	public String getUser(){
		return user.getText();
	}
	
	public String getPassword(){
		return password.getText();
	}


}
