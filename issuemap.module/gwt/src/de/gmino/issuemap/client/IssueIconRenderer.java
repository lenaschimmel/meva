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
		hash.hashObject(issue.getTitle());
		hash.hashObject(issue.getDescription());
	}

	@Override
	public void renderIcon(Canvas can, Issue object) {
		can.setCoordinateSpaceWidth(100);
		can.setCoordinateSpaceHeight(30);
		Context2d con = can.getContext2d();
		
		con.setFillStyle("#FF3300");
		con.fillRect(0, 0, 100, 30);
		con.setFillStyle("#000000");
		con.fillText(object.getTitle(), 0,12);
		con.fillText(object.getDescription(), 0,24);
	}
	
	
}
