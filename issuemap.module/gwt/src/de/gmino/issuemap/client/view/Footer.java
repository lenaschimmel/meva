package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.client.view.popup.Info_PopUp;
import de.gmino.issuemap.client.view.popup.List_PopUp;
import de.gmino.issuemap.shared.domain.Markertype;
import de.gmino.meva.client.domain.KeyValueSet;

public class Footer extends Composite {
	private static UIUiBinder uiBinder = GWT.create(UIUiBinder.class);
	
	private List_PopUp issueList_PopUp;
	private DecoratedPopupPanel decoratedPopupPanel;
	private Info_PopUp infoPopup;
	
	private Map map;
	private String iconUrl;
	private String resolvedUrl;

	interface UIUiBinder extends UiBinder<Widget, Footer> {
	}

	public Footer(DecoratedPopupPanel decorated_panel) {
		initWidget(uiBinder.createAndBindUi(this));
		text.setVisible(false);
		footer.setHeight("50px");
		this.decoratedPopupPanel=decorated_panel;
	}

	@UiField
	HorizontalPanel footer;
	@UiField
	Image gmino_logo;
	@UiField
	Image counter_icon;
	@UiField
	Image resolvedCounter_icon;
	@UiField
	Image cursor;
	@UiField
	Label text;
	@UiField
	Label counter;
	@UiField
	Label resolvedCounter;
	@UiField
	FocusPanel counterPanel;
	@UiField
	FocusPanel resolvedCounterPanel;
	@UiField
	FocusPanel doubleClickInfoPanel;


	@UiHandler("gmino_logo")
	void onLogoClick(ClickEvent e) {
		Window.open("http://geoengine.de", "greenmobile geoengine", "");
	}

	@UiHandler("counterPanel")
	void onCounterClick(ClickEvent e) {
		if (RootPanel.get("list").getElement().getStyle().getDisplay().equals("block")) {
			RootPanel.get("list").getElement().getStyle().setDisplay(Display.NONE); // setZIndex(-1000);
			issueList_PopUp.getListButton().setVisible(true);
			IssuemapGwt.getInstance().updateListMargin(false);
		} else {
			issueList_PopUp.setParentVisible();
			IssuemapGwt.getInstance().updateListMargin(true);
		}
	}
	
	@UiHandler("resolvedCounterPanel")
	void onResolvedCounterClick(ClickEvent e) {
		if (decoratedPopupPanel.isShowing()&&infoPopup.getActiveTab()==2) {
			decoratedPopupPanel.hide();
		} else {
			decoratedPopupPanel.setPopupPosition(Window.getClientWidth() / 2, (int) (Window.getClientHeight()*0.15));
			decoratedPopupPanel.show();
			infoPopup.activateTab(2);
		}
	}
	
	@UiHandler("doubleClickInfoPanel")
	void onInfoClick(ClickEvent e) {
		if (decoratedPopupPanel.isShowing()&&infoPopup.getActiveTab()==1) {
			decoratedPopupPanel.hide();
		} else {
			decoratedPopupPanel.setPopupPosition(Window.getClientWidth() / 2, (int) (Window.getClientHeight()*0.15));
			decoratedPopupPanel.show();
			infoPopup.activateTab(1);
		}
	}
	
	public void setDesign(Map map) {
		this.map = map;
		text.getElement().getStyle().setColor(map.getBarTextColor());
		counter.getElement().getStyle().setColor(map.getBarTextColor());
		resolvedCounter.getElement().getStyle().setColor(map.getBarTextColor());
		text.setVisible(true);
		cursor.setVisible(true);
		footer.getElement().getStyle().setBackgroundColor(map.getBarBackgroundColor());
		if(map.isCreate()){
			counterPanel.setVisible(true);
			doubleClickInfoPanel.setVisible(true);
		}	
		if(map.isMark()) 
			resolvedCounterPanel.setVisible(true);
	}

	public void setMarkerIcon(OpenLayersSmartLayer smartLayer) {
		Poi poi = new Poi(-1);
		
		Markertype chosenMarkertype = map.getHasMarkertypes().iterator().next();
		for(Markertype mt : map.getHasMarkertypes())
		{
			if(mt.getMarkerName().equals("Sonstiges"))
				chosenMarkertype = mt;
		}
		final KeyValueSet markerClass = (KeyValueSet) chosenMarkertype.getMarkerClass();
		poi.setKeyvalueset(markerClass);
		poi.setMap_instance(map);
		poi.setCreationTimestamp(Timestamp.now());
		poi.setMarkertype(chosenMarkertype);
		GwtIconRenderer<? super Poi> renderer =  smartLayer.getRendererForPoi(poi);
		iconUrl = renderer.getIconUrl(poi);
		counter_icon.setVisible(true);
		poi.setMarked(true);
		resolvedUrl = renderer.getIconUrl(poi);
		resolvedCounter_icon.setVisible(true);
		counter.setText("Einträge werden geladen...");
		resolvedCounter.setText("Einträge werden geladen...");
	}

	public void setList(List_PopUp list){
		this.issueList_PopUp=list;
	}

	public void setCounter(int count) {
		counter.setText("Bisher wurden " + count+" Einträge erstellt");
		counter_icon.setUrl(iconUrl);
	}
	
	public void setResolvedCounter(int resolvedCount) {
		resolvedCounter.setText("Bisher wurden " + resolvedCount+" Einträge erledigt");
		resolvedCounter_icon.setUrl(resolvedUrl);
	}

	public void setInfoPopup(Info_PopUp infoPopUp) {
		this.infoPopup=infoPopUp;
	}
}
