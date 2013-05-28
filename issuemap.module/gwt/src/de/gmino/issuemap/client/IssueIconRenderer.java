package de.gmino.issuemap.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.issuemap.client.domain.Issue;

public class IssueIconRenderer extends GwtIconRenderer<Issue> {
	
	ImageUrlLoader loader = ImageUrlLoader.getInstance();
	
	public void getIconHash(de.gmino.geobase.shared.map.Hasher hash, Issue issue) 
	{
		hash.hashBoolean(issue.isResolved());
		hash.hashLong(issue.getMarkertype().getId());
		hash.hashObject(issue.getTitle());
		hash.hashObject(issue.getDescription());
	}

	@Override
	public void renderIcon(Canvas can, Issue issue) {
		can.setCoordinateSpaceWidth(getWidth(issue));
		can.setCoordinateSpaceHeight(getHeight(issue));
		Context2d con = can.getContext2d();
		
		String imageName = issue.getMarkertype().getImageName();
		Image img = loader.getImageByUrl("/mapicon/" + imageName + ".png");
		
		if (issue.getMarkertype().getMarkerName().equals("Disco"))
			con.setFillStyle("#C24704");
		else if (issue.getMarkertype().getMarkerName().equals("Theater"))
			con.setFillStyle("#D9CC3C");
		else if (issue.getMarkertype().getMarkerName().equals("Party"))
			con.setFillStyle("#33BB00");
		else if (issue.getMarkertype().getMarkerName().equals("Sport"))
			con.setFillStyle("#A0E0A9");
		else if (issue.getMarkertype().getMarkerName().equals("Konzert"))
			con.setFillStyle("#00ADA7");

		else {

			if (issue.isResolved())
				con.setFillStyle("#33BB00");
			else
				con.setFillStyle(issue.getMap_instance().getColor());
		}
		con.fillRect(3, 4, 25, 24);
		con.beginPath();
		con.moveTo(10, 28);
		con.lineTo(22, 28);
		con.lineTo(15, 34);
		con.lineTo(10, 28);
		con.fill();
		con.setFillStyle("#000000");
		//con.fillText(issue.getTitle(), 32,12);
		//con.fillText(issue.getDescription(), 32,24);
		
		final ImageElement face = ImageElement.as(img.getElement());
		con.drawImage(face, 0, 0);
	}

	@Override
	public int getWidth(Issue issue) {
		return issue.getMarkertype().getImageWidth();
	}

	@Override
	public int getHeight(Issue issue) {
		return issue.getMarkertype().getImageHeight();
	}
	
	
}
