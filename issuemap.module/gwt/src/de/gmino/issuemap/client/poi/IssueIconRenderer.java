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
		hash.hashInt(issue.getPhotos().size());
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
		ImageElement mapiconImg = loader.getImageElementByUrl("/mapicon/" + imageName + ".png");
		ImageElement cameraImg = loader.getImageElementByUrl("/camera.png");
		ImageElement bubbleImg = loader.getImageElementByUrl("/bubble.png");
		
		String markerName = issue.getMarkertype().getMarkerName();
		long markerId = issue.getMarkertypeId();
		con.setFillStyle(getColor(issue, markerName, markerId));
		
		int imageWidth = getWidth(issue);// issue.getMarkertype().getImageWidth();
		int imageHeight = getHeight(issue); //issue.getMarkertype().getImageHeight();

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
		
		con.drawImage(mapiconImg, 0, 0, imageWidth, imageHeight);
		
		//int rating = issue.getRating();
		
		if(issue.getComments().size() > 0)
			con.drawImage(bubbleImg, imageWidth * 0.0, 0, imageWidth * 0.4, imageHeight * 0.4);
		if(issue.getPhotos().size() > 0)
			con.drawImage(cameraImg, imageWidth * 0.6, 0, imageWidth * 0.4, imageHeight * 0.4);
		
		
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
		return (int)(issue.getMarkertype().getImageWidth() * getRelevanceFactor(issue));
	}

	@Override
	public int getHeight(Issue issue) {
		return (int)(issue.getMarkertype().getImageHeight() * getRelevanceFactor(issue));
	}
	

	private float getRelevanceFactor(Issue issue) {
		float rel = issue.getRating();
		if(rel < -5)
			rel = -5;
		if(rel > 5)
			rel = 5;
		return 1.0f + rel * 0.1f;
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
