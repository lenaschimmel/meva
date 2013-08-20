package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class String_KeyValueView extends Composite {

	private static String_KeyValueViewUiBinder uiBinder = GWT.create(String_KeyValueViewUiBinder.class);

	interface String_KeyValueViewUiBinder extends UiBinder<Widget, String_KeyValueView> {
	}

	public String_KeyValueView(String keyName, String value, String description) {
		initWidget(uiBinder.createAndBindUi(this));
		this.key.setText(keyName);
		this.value.setText(value);
		this.parent.setTitle(description);
		
	}




@UiField
Label key;
@UiField
Label value;
@UiField
HTMLPanel parent;




}
