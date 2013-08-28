package de.gmino.issuemap.client.view.valueview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.meva.client.domain.LongText;
import de.gmino.meva.client.domain.ShortText;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.ValueWrapper;

public class KeyValueView extends Composite {

	private static String_KeyValueViewUiBinder uiBinder = GWT.create(String_KeyValueViewUiBinder.class);

	interface String_KeyValueViewUiBinder extends UiBinder<Widget, KeyValueView> {
	}
	
	boolean currentEditMode;

	@UiField
	Label name;
	@UiField
	SimplePanel value_view;
	@UiField
	HTMLPanel parent;
	@UiField
	VerticalPanel verticalPanel;

	public KeyValueView(ValueWrapper valueWrapper) {
		initWidget(uiBinder.createAndBindUi(this));
		this.name.setText(valueWrapper.getName());
	
		if(valueWrapper.getType() == LongText.type)
		{
			String text = "";
			LongText descriptionLongText = (LongText) valueWrapper.getValue();
			if(descriptionLongText != null)
				text = descriptionLongText.getText();
			HTMLPanel description = new HTMLPanel(new SafeHtmlBuilder().appendEscapedLines(text).toSafeHtml());
			verticalPanel.add(description);
		}
		
		if(valueWrapper.getType() == TypeName.String)
		{
			Label label = new Label(valueWrapper.getString());
			value_view.add(label);
		}
	
		if(valueWrapper.getType() == ShortText.type)
		{
			ShortText valueShortText = (ShortText) valueWrapper.getValue();
			Label label = new Label(valueShortText.getText());
			value_view.add(label);
		}
		this.parent.setTitle(valueWrapper.getDescription());
	}
	
	private String preventNullString(String text) {
		if(text == null)
			return "";
		else
			return text;
	}

	public void setEditMode(boolean edit)
	{
		if(currentEditMode == edit)
			return;
		currentEditMode = edit;
		
		if(edit)
		{
			name.getElement().getStyle().setColor("#FF0000");
		}
		else
		{
			name.getElement().getStyle().setColor("#FFFFFF");
		}
	}
}
