package de.gmino.issuemap.client.view;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Image;

import de.gmino.issuemap.client.ImageUrlLoader;
import de.gmino.issuemap.client.ImageUrlLoader.ImageLoadListener;

public class ThumbnailView extends AbsolutePanel implements ClickHandler {
	private final String photoBaseUrl;
	private ImageUrlLoader loader;
	private String scaledUrl;
	private boolean loaded;
	
	private Image photo;
	private Image loadAnimation;

	public ThumbnailView(String photoBaseUrl) {
		this.photoBaseUrl = photoBaseUrl;
		
		setWidth("100px");
		setHeight("100px");

		loadAnimation = new Image("loading.gif");
		loadAnimation.setSize("41px","39px");
		add(loadAnimation, 30, 30);
		
		loader = ImageUrlLoader.getInstance();
		final String thumbUrl = photoBaseUrl+"&h=100&w=100";
		loader.loadImage(thumbUrl, new ImageLoadListener() {
			@Override
			public void onLoaded() {
				System.out.println("Loaded " + thumbUrl);
				photo =  loader.getImageByUrl(thumbUrl);
				photo.getElement().getStyle().setCursor(Cursor.POINTER);
				photo.addClickHandler(ThumbnailView.this);
				photo.getElement().getStyle().setDisplay(Display.BLOCK);
				int w = photo.getWidth();
				int h = photo.getHeight();
				int l = (100 - w) / 2;
				int t = (100 - h) / 2;
				add(photo, l, t);
			}
		});
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