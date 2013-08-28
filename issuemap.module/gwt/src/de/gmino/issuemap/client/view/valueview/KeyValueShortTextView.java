package de.gmino.issuemap.client.view.valueview;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import de.gmino.meva.client.domain.ShortText;
import de.gmino.meva.shared.ValueWrapper;

public class KeyValueShortTextView extends KeyValueView {

	public KeyValueShortTextView(ValueWrapper valueWrapper) {
		super(valueWrapper);
		setValue(valueWrapper);
	}
	
	@Override
	public void enableEditMode() {
		TextBox textBox = new TextBox();
		textBox.setText(getStringFromVal());
		value_view_right.clear();
		value_view_right.add(textBox);
	}

	@Override
	public void enableShowMode() {
		Label label = new Label(getStringFromVal());
		value_view_right.clear();
		value_view_right.add(label);
	}

	private String getStringFromVal() {
		String text = "";
		ShortText descriptionShortText = (ShortText) val.getValue();
		if(descriptionShortText != null)
			text = descriptionShortText.getText();
		return text;
	}
}
