package de.gmino.issuemap.client.view;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Image;

import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.ImageUrlLoader.ImageLoadListener;

public class ThumbnailView extends Image implements ClickHandler {
	private final String photoBaseUrl;
	private ImageUrlLoader loader;
	private String scaledUrl;
	private boolean loaded;

	public ThumbnailView(String photoBaseUrl) {
		super(photoBaseUrl + "&h=100&w=100");
		this.photoBaseUrl = photoBaseUrl;
		getElement().getStyle().setCursor(Cursor.POINTER);
		getElement().getStyle().setProperty("margin", "auto");
		addClickHandler(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		if(loaded)
			showPopup();
		else
		{
			loader = ImageUrlLoader.getInstance();
			scaledUrl = photoBaseUrl+"&h=500";
			loader.loadImage(scaledUrl, new ImageLoadListener() {
				@Override
				public void onLoaded() {
					loaded = true;
					showPopup();
				}
			});
		}
	}
	
	private void showPopup() {
		Image popupImage = loader.getImageByUrl(scaledUrl);
		popupImage.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		
		DecoratedPopupPanel popup = new DecoratedPopupPanel(true, true);
		popup.setGlassEnabled(true);
		
		popupImage.setHeight("500px");
		popup.add(popupImage);
		popup.show();
		popup.center();
	}
}