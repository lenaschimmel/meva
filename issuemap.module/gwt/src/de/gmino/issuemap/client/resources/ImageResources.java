package de.gmino.issuemap.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageResources extends ClientBundle {
	
	@Source("img/chart.png")
    ImageResource chart();

	@Source("img/chart_hover.png")
	ImageResource chart_hover();
	
	@Source("img/list.png")
    ImageResource list();

	@Source("img/list_hover.png")
	ImageResource list_hover();
	
	@Source("img/preferences.png")
    ImageResource preferences();

	@Source("img/preferences_hover.png")
	ImageResource preferences_hover();
	
	@Source("img/search.png")
	ImageResource search();
	
	@Source("img/search_hover.png")
	ImageResource search_hover();
	
	@Source("img/info.png")
	ImageResource info();
	
	@Source("img/info_hover.png")
	ImageResource info_hover();

}
