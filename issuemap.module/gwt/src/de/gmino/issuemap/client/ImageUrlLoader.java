package de.gmino.issuemap.client;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.meva.shared.request.RequestListener;

public class ImageUrlLoader implements LoadHandler, ErrorHandler {

	private static ImageUrlLoader instance;

	public static ImageUrlLoader getInstance()
	{
		if(instance == null)
			instance = new ImageUrlLoader();
		return instance;
	}
	
	Map<String, Image> imagesByUrl = new TreeMap<String, Image>();
	int imagesToLoad = 0;
	private RequestListener<Void> listener;
	
	public void addUrl(String url)
	{
		if(imagesByUrl.containsKey(url))
			return;
		Image img = new Image(url);
		RootPanel.get().add(img);
		img.getElement().getStyle().setDisplay(Display.NONE);
		img.addLoadHandler(this);
		img.addErrorHandler(this);
		imagesByUrl.put(url, img);
		imagesToLoad++;
	}
	
	public void setOnLoadListener(RequestListener<Void> listener)
	{
		this.listener = listener;
	}
	
	@Override
    public void onLoad(LoadEvent event) {
		imagesToLoad--;
		if(imagesToLoad == 0 && listener != null)
			listener.onFinished(null);
    }
	
	public Image getImageByUrl(String url) {
		if(!imagesByUrl.containsKey(url))
			throw new RuntimeException("Can't return image für " + url + " since it was never requested.");
		else if(imagesToLoad > 0)
			throw new RuntimeException("Image loading not finished, still waiting for " + imagesToLoad + " images to load.");
		return imagesByUrl.get(url);
	}
//	
//	public void waitForLoad()
//	{
//		while(imagesToLoad > 0)
//		{
//			try {
//				System.out.println("Waiting: " + imagesToLoad);
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}			
//		}
//	}

	@Override
	public void onError(ErrorEvent event) {
		imagesToLoad--;
		System.err.println("Image Load Error, trying to resume: " + event);
		if(imagesToLoad == 0)
			listener.onFinished(null);
	}
}
