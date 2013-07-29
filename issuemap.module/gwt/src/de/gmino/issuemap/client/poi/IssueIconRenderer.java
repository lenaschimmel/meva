package de.gmino.issuemap.client.poi;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import de.gmino.geobase.client.map.GwtIconRenderer;
import de.gmino.geobase.shared.map.Hasher;
import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.domain.BicycleShop;
import de.gmino.issuemap.client.domain.Issue;

public class IssueIconRenderer extends GwtIconRenderer<Issue> {
	
	ImageUrlLoader loader = ImageUrlLoader.getInstance();
	
	public void getIconHash(de.gmino.geobase.shared.map.Hasher hash, Issue issue) 
	{
		hash.hashBoolean(issue.isResolved());
		hash.hashLong(issue.getMarkertype().getId());
		hash.hashInt(issue.getComments().size());
		hash.hashInt(issue.getRating());
	}
	
	@Override
	public void renderIcon(Canvas can, Issue issue) {
		can.setCoordinateSpaceWidth(getWidth(issue));
		can.setCoordinateSpaceHeight(getHeight(issue));
		Context2d con = can.getContext2d();
		
		String imageName = issue.getMarkertype().getImageName();
		if("null".equals(imageName+""))
			System.out.println("Marker with no icon name: " + issue);
		Image img = loader.getImageByUrl("/mapicon/" + imageName + ".png");
		
		String markerName = issue.getMarkertype().getMarkerName();
		long markerId = issue.getMarkertypeId();
		con.setFillStyle(getColor(issue, markerName, markerId));
		
		int imageWidth = issue.getMarkertype().getImageWidth();
		int imageHeight = issue.getMarkertype().getImageHeight();

		double x = 0.11		* imageWidth;
		double y = 0.1081	* imageHeight;
		double w = 0.77		* imageWidth;
		double h = 0.66		* imageHeight;
		con.fillRect(x, y, w, h);
		con.beginPath();
		con.moveTo(0.5 * imageWidth, 0.92 * imageHeight);
		con.lineTo(0.7 * imageWidth, 0.75 * imageHeight);
		con.lineTo(0.3 * imageWidth, 0.75 * imageHeight);
		con.lineTo(0.5 * imageWidth, 0.92 * imageHeight);
		con.fill();
		con.setFillStyle("#000000");
		//con.fillText(issue.getTitle(), 32,12);
		//con.fillText(issue.getDescription(), 32,24);
		
		final ImageElement face = ImageElement.as(img.getElement());
		con.drawImage(face, 0, 0, imageWidth, imageHeight);
		
		//int rating = issue.getRating();
		int commentCount = issue.getComments().size();
		if(commentCount != 0)
		{
			con.setLineWidth(1.5);
			con.setStrokeStyle("#000");
//			if(rating > 0)
//				con.setFillStyle("#0F0");
//			else
//				con.setFillStyle("#F00");
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

	private String getColor(Issue issue, String markerName, long markerId) {
		if (markerName.equals("Disco"))
			return("#33BB00");
		else if (markerName.equals("Theater"))
			return("#ef4122");
		else if (markerName.equals("Party"))
			return("#FF8800");
		else if (markerName.equals("Sport"))
			return("#3852AE");
		else if (markerName.equals("Konzert"))
			return("#B200FF");
		else if (markerId >= 80 && markerId <= 94)
			{
			switch((int)markerId)
			{
				case 82:
				case 87:
				case 88:
				case 89:
				case 91:
				case 92:
				case 94:
					return("#FF0000"); // Kultur
				case 80:
				case 81:
				case 85:
				case 90:
					return("#33BB00"); // Freizeit
				case 84:
				case 86:
				case 93:
				case 83:
					return("#FF8700"); // Infrastruktur
			}
		}
		else {

			if (issue.isResolved())
				return(issue.getMap_instance().getResolved_color());
			else
				return(issue.getMap_instance().getPrimary_color());
		}
		return "#FFFFFF";
	}

	@Override
	public int getWidth(Issue issue) {
		return issue.getMarkertype().getImageWidth();
	}

	@Override
	public int getHeight(Issue issue) {
		return issue.getMarkertype().getImageHeight();
	}
	

	@Override
	public void renderSmallIcon(Canvas canvas, Issue issue) {
		String markerName = issue.getMarkertype().getMarkerName();
		long markerId = issue.getMarkertypeId();
		String color = getColor(issue, markerName, markerId);
		drawDefaultCircle(canvas, color);
	}

	@Override
	public void getSmallIconHash(Hasher hash, Issue issue) {
		String markerName = issue.getMarkertype().getMarkerName();
		long markerId = issue.getMarkertypeId();
		String color = getColor(issue, markerName, markerId);
		hash.hashObject(color);
	}
	
	
}
