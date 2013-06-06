package de.gmino.issuemap.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
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
		//hash.hashObject(issue.getTitle());
		//hash.hashObject(issue.getDescription());
		hash.hashInt(issue.getComments().size());
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
		
		double x = 0.11*issue.getMarkertype().getImageWidth();
		double y = 0.1081*issue.getMarkertype().getImageHeight();
		double w = 0.77*issue.getMarkertype().getImageWidth();
		double h = 0.66*issue.getMarkertype().getImageHeight();
		con.fillRect(x, y, w, h);
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
		
		int commentCount = issue.getComments().size();
		if(commentCount > 0)
		{
			con.setLineWidth(1.5);
			con.setStrokeStyle("#000");
			con.setFillStyle("#FF0");
			con.beginPath();
			con.arc(x+w*0.85,y+w*0.15,w*0.25,0,Math.PI*2);
			con.fill();
			con.stroke();
			con.setFillStyle("#000");
			String countText = ""+commentCount;
			if(commentCount > 9)
				countText = "+";
			con.setFont("9px sans-serif");
			con.fillText(countText, x+w*0.725, y+w*0.3);
		}
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
