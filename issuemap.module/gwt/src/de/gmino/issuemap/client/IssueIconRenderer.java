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
			con.setFillStyle("#33BB00");
		else if (issue.getMarkertype().getMarkerName().equals("Theater"))
			con.setFillStyle("#ef4122");
		else if (issue.getMarkertype().getMarkerName().equals("Party"))
			con.setFillStyle("#FF8800");
		else if (issue.getMarkertype().getMarkerName().equals("Sport"))
			con.setFillStyle("#3852AE");
		else if (issue.getMarkertype().getMarkerName().equals("Konzert"))
			con.setFillStyle("#B200FF");

		else {

			if (issue.isResolved())
				con.setFillStyle("#33BB00");
			else
				con.setFillStyle(issue.getMap_instance().getColor());
		}
		
		con.fillRect(0.11*issue.getMarkertype().getImageWidth(), 0.1081*issue.getMarkertype().getImageHeight(), 0.77*issue.getMarkertype().getImageWidth(), 0.66*issue.getMarkertype().getImageHeight());
		con.beginPath();
		con.moveTo(0.5*issue.getMarkertype().getImageWidth(), 0.92*issue.getMarkertype().getImageHeight());
		con.lineTo(0.7*issue.getMarkertype().getImageWidth(), 0.75*issue.getMarkertype().getImageHeight());
		con.lineTo(0.3*issue.getMarkertype().getImageWidth(), 0.75*issue.getMarkertype().getImageHeight());
		con.lineTo(0.5*issue.getMarkertype().getImageWidth(), 0.92*issue.getMarkertype().getImageHeight());
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
