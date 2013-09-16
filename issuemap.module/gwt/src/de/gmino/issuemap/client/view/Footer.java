/**
 * 
 */
package de.gmino.issuemap.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.client.map.OpenLayersSmartLayer;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.client.domain.Map;
import de.gmino.issuemap.client.domain.Poi;
import de.gmino.issuemap.shared.domain.Markertype;
import de.gmino.meva.client.domain.KeyValueSet;

/**
 * @author greenmobile
 *
 */
public class Footer extends Composite {

	private static UIUiBinder uiBinder = GWT.create(UIUiBinder.class);
	Map map;

	interface UIUiBinder extends UiBinder<Widget, Footer> {
	}

	public Footer() {
		initWidget(uiBinder.createAndBindUi(this));
		text.setVisible(false);
		footer.setHeight("50px");
	}

	@UiField
	HorizontalPanel footer;
	@UiField
	Image gmino_logo;
	@UiField
	Image counter_icon;
	@UiField
	Image cursor;
	@UiField
	Label text;
	@UiField
	Label counter;
	@UiField
	Panel counterPanel;
	@UiField
	Panel doubleClickInfoPanel;
	
	public Footer(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("gmino_logo")
	void onClick(ClickEvent e) {
		Window.open("http://geoengine.de", "greenmobile geoengine", "");
	}
	
	public void setDesign() {
		text.getElement().getStyle().setColor(map.getBarTextColor());
		counter.getElement().getStyle().setColor(map.getBarTextColor());
		text.setVisible(true);
		cursor.setVisible(true);
		footer.getElement().getStyle().setBackgroundColor(map.getBarBackgroundColor());
		if(map.isEdit()){
			counterPanel.setVisible(true);
			doubleClickInfoPanel.setVisible(true);
		}			
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
		String iconUrl = renderer.getIconUrl(poi);
		counter_icon.setUrl(iconUrl);
		counter_icon.setVisible(true);
		counter.setText("Einträge werden geladen...");
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setCounter(int count) {
		counter.setText("Bisher wurden " + count+" Stellen markiert");
	}
}
