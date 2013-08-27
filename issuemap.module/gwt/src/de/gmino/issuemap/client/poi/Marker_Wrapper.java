package de.gmino.issuemap.client.poi;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.AbsolutePanel;

import de.gmino.geobase.shared.domain.PoiInterface;
import de.gmino.issuemap.client.domain.Map;

public class Marker_Wrapper extends AbsolutePanel { // implements MouseOverHandler, MouseOutHandler, ClickHandler { // Composite implements HasText {
	PoiInterface mIssue;
	Map mMapObject;

	public Marker_Wrapper(PoiInterface issue, Map mapObject) {
		this.setSize("20px", "20px");
		this.getElement().getStyle().setOverflow(Overflow.VISIBLE);
		this.mIssue=issue ;
		this.mMapObject=mapObject;
	}

}
