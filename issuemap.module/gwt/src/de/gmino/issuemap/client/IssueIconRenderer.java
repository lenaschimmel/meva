package de.gmino.issuemap.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.issuemap.client.domain.Issue;

public class IssueIconRenderer extends GwtIconRenderer<Issue> {
	public void getIconHash(de.gmino.geobase.shared.map.Hasher hash, Issue issue) 
	{
		hash.hashLong(issue.getMarkertype().getId());
		hash.hashObject(issue.getTitle());
		hash.hashObject(issue.getDescription());
	}

	@Override
	public void renderIcon(Canvas can, Issue issue) {
		can.setCoordinateSpaceWidth(100);
		can.setCoordinateSpaceHeight(36);
		Context2d con = can.getContext2d();
		
		String imageName = issue.getMarkertype().getImageName();
		String url = "/mapicon/" + imageName + ".png";
		Image img = new Image(url);
		
		if(issue.isResolved())
			con.setFillStyle("#33BB00");
		else
			con.setFillStyle("#FF3300");
		
		con.fillRect(3, 4, 25, 24);
		con.beginPath();
		con.moveTo(10, 28);
		con.lineTo(22, 28);
		con.lineTo(15, 34);
		con.lineTo(10, 28);
		con.fill();
		con.setFillStyle("#000000");
		con.fillText(issue.getTitle(), 32,12);
		con.fillText(issue.getDescription(), 32,24);
		
		final ImageElement face = ImageElement.as(img.getElement());
		con.drawImage(face, 0, 0);
	}
	
	
}
