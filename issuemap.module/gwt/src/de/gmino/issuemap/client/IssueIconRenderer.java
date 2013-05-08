package de.gmino.issuemap.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.geobase.shared.map.IconRenderer;
import de.gmino.issuemap.client.domain.Issue;

public class IssueIconRenderer extends GwtIconRenderer<Issue> {
	public void getIconHash(de.gmino.geobase.shared.map.Hasher hash, Issue issue) 
	{
		
	}

	@Override
	public void renderIcon(Canvas can, Issue object) {
		can.setWidth("30px");
		can.setHeight("30px");
		Context2d con = can.getContext2d();
		con.setFillStyle("#FF3300");
		con.fillRect(0, 0, 30, 30);
	}
	
	
}
