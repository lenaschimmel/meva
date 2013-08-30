package de.gmino.issuemap.client.view.valueview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
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

public abstract class KeyValueView extends Composite {

	private static String_KeyValueViewUiBinder uiBinder = GWT.create(String_KeyValueViewUiBinder.class);

	interface String_KeyValueViewUiBinder extends UiBinder<Widget, KeyValueView> {
	}
	
	boolean currentEditMode;

	@UiField
	Label name;
	@UiField
	SimplePanel value_view_right;
	@UiField
	SimplePanel value_view_bottom;
	@UiField
	HTMLPanel parent;
	@UiField
	VerticalPanel verticalPanel;

	protected ValueWrapper val;
	
	interface Style extends CssResource {
		String inputStyle();
	}
	
	@UiField
	Style style;
	
	
	public static KeyValueView getKeyValueView(ValueWrapper valueWrapper)
	{
		if(valueWrapper.getType() == LongText.type)
			return new KeyValueLongTextView(valueWrapper);
		if(valueWrapper.getType() == ShortText.type)
			return new KeyValueShortTextView(valueWrapper);
		if(valueWrapper.getType() == TypeName.String)
			return new KeyValueStringtView(valueWrapper);
		throw new RuntimeException("No KeyValueView class defined for value type " + valueWrapper.getType());
	}

	public KeyValueView(ValueWrapper valueWrapper) {
		initWidget(uiBinder.createAndBindUi(this));
		this.name.setText(valueWrapper.getName());
		this.parent.setTitle(valueWrapper.getDescription());

		setValue(valueWrapper);
	}
	
	public void setValue(ValueWrapper val)
	{
		this.val = val;
		if(currentEditMode)
			enableEditMode();
		else
			enableShowMode();
	}
	
	public void setEditMode(boolean edit)
	{
		if(currentEditMode == edit)
			return;
		currentEditMode = edit;
		
		if(edit)
			enableEditMode();
		else
			enableShowMode();
	}
	
	protected String preventNullString(String text)
	{
		if(text == null)
			return "";
		else
			return text;
	}
	
	public abstract void enableEditMode();
	
	public abstract void enableShowMode();

	public abstract void saveValue();
}
