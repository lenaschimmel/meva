package de.gmino.issuemap.client.view;

import java.util.ArrayList;
import java.util.List;

import org.mortbay.log.Log;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Issue;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.poi.IssueIconRenderer;
import de.gmino.issuemap.shared.request.SendFeedback;
import de.gmino.meva.shared.request.Requests;

public class IssueList_PopUp extends Composite implements HasText {

	private static IssueList_PopUpUiBinder uiBinder = GWT
			.create(IssueList_PopUpUiBinder.class);
	
	interface IssueList_PopUpUiBinder extends UiBinder<Widget, IssueList_PopUp> {
	}
	
	private List_Button listButton;

	public IssueList_PopUp(Map mapObject, ArrayList<Issue> data, IssueIconRenderer issueRenderer, OpenLayersSmartLayer layer) {
		initWidget(uiBinder.createAndBindUi(this));
		listButton = new List_Button(this);
		RootPanel.get("list").add(listButton);
		listButton.setVisible(false);
		title.getElement().getStyle().setColor(mapObject.getSecondary_color());
	
	    
<<<<<<< HEAD
	    for (Issue i : data)	    IssueItemsPanel.add(new IssueList_Item(i, issueRenderer));
=======
	    for (Issue i : data)	    parent.add(new IssueList_Item(i, issueRenderer, layer));
>>>>>>> 0f91e66ef588b02d996d39943a36473d526fde98


	}

	@UiField
	Image close;
	@UiField
	Label title;
	@UiField
	VerticalPanel IssueItemsPanel;
	




	@UiHandler("close")
	void onClick(ClickEvent e) {
		this.setVisible(false);
		listButton.setVisible(true);
	}


	public void setText(String text) {

	}

	public String getText() {
		return null;
	}
	

}
