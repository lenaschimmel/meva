package de.gmino.issuemap.client.view.list;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.view.popup.Feedback_PopUp;
import de.gmino.issuemap.client.view.popup.List_PopUp;

public class List_Button extends Composite implements HasText {

	private static List_ButtonUiBinder uiBinder = GWT
			.create(List_ButtonUiBinder.class);
	
	public Feedback_PopUp feedbackPopup;
	private List_PopUp issueList_PopUp;

	interface List_ButtonUiBinder extends UiBinder<Widget, List_Button> {
	}

	public List_Button(List_PopUp issueList_PopUp) {
		initWidget(uiBinder.createAndBindUi(this));
		this.issueList_PopUp=issueList_PopUp;
	}

	@UiField
	PushButton list_button;

	@UiHandler("list_button")
	void onClick(ClickEvent e) {
		this.setVisible(false);
		issueList_PopUp.setParentVisible();
		IssuemapGwt.getInstance().updateListMargin(true);
	}

	public void setText(String text) {
		list_button.setText(text);
	}

	public String getText() {
		return list_button.getText();
	}
}
