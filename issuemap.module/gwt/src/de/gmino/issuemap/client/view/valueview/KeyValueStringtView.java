package de.gmino.issuemap.client.view.valueview;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import de.gmino.meva.shared.ValueWrapper;

public class KeyValueStringtView extends KeyValueView {

	public KeyValueStringtView(ValueWrapper valueWrapper) {
		super(valueWrapper);
		setValue(valueWrapper);
	}
	
	@Override
	public void enableEditMode() {
		TextBox textBox = new TextBox();
		textBox.setText(val.getString());
		value_view_right.clear();
		value_view_right.add(textBox);
	}

	@Override
	public void enableShowMode() {
		Label label = new Label(val.getString());
		value_view_right.clear();
		value_view_right.add(label);
	}

}
