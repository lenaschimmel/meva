package de.gmino.issuemap.client.view.valueview;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;

import de.gmino.meva.client.domain.LongText;
import de.gmino.meva.shared.ValueWrapper;

public class KeyValueLongTextView extends KeyValueView {

	public KeyValueLongTextView(ValueWrapper valueWrapper) {
		super(valueWrapper);
		setValue(valueWrapper);
	}

	@Override
	public void enableEditMode() {
		TextArea textBox = new TextArea();
		textBox.setText(getStringFromVal());
		value_view_bottom.clear();
		value_view_bottom.add(textBox);
	}

	@Override
	public void enableShowMode() {
		HTMLPanel description = new HTMLPanel(new SafeHtmlBuilder().appendEscapedLines(getStringFromVal()).toSafeHtml());
		value_view_bottom.clear();
		value_view_bottom.add(description);
	}

	private String getStringFromVal() {
		String text = "";
		LongText descriptionLongText = (LongText) val.getValue();
		if(descriptionLongText != null)
			text = descriptionLongText.getText();
		return text;
	}

}
