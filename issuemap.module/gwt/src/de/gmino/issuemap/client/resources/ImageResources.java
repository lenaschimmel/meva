package de.gmino.issuemap.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageResources extends ClientBundle {
	
	@Source("img/chart.png")
    ImageResource chart();

	@Source("img/chart_hover.png")
	ImageResource chart_hover();
	
	@Source("img/search.png")
	ImageResource search_button();

}
