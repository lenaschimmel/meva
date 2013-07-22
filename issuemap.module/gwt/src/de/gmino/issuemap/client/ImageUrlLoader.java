package de.gmino.issuemap.client;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.meva.shared.request.RequestListener;

public class ImageUrlLoader {

	private static ImageUrlLoader instance;

	public static ImageUrlLoader getInstance()
	{
		if(instance == null)
			instance = new ImageUrlLoader();
		return instance;
	}
	
	private class InternalImageLoadListener implements LoadHandler, ErrorHandler
	{
		String url;
		
		public InternalImageLoadListener(String url) {
			this.url = url;		
		}

		@Override
	    public void onLoad(LoadEvent event) {
			onImageLoaded(url);
	    }
		
		@Override
		public void onError(ErrorEvent event) {
			onImageError(url);
		}
	}
	
	public static abstract class ImageLoadListener
	{
		Collection<String> urlsToLoad;
		
		public abstract void onLoaded();
		
		public void onError(String url)
		{
			throw new RuntimeException("Error while loading one of the images: " + url);
		}

		/**
		 * Notifies this listener that an image - maybe one it is listening for - has been loaded. Calls onLoad if this indicates that all images were loaded by now.
		 * @param url The url of the image that has just been loaded.
		 * @return true if this was the last image to load and onLoaded has been called.
		 */
		private boolean imageLoaded(String url)
		{
			if(urlsToLoad.remove(url) && urlsToLoad.isEmpty())
			{
				debugPrint("All images for this listener have been loaded, calling client code.");
				onLoaded();
				return true;
			}
			else
				debugPrint("Loaded one image for this loader, " + urlsToLoad.size() + " more to go.");
			return false;
		}

		private boolean imageError(String url)
		{
			if(urlsToLoad.remove(url))
			{
				onError(url);
				return true;
			}
			return false;
		}
		
//		public void load()
//		{
//			ImageUrlLoader loaderInstance = getInstance();
//			if(loaderInstance.loadedUrls.containsAll(urlsToLoad))
//				onLoaded();
//			
//			else
//			{
//				loaderInstance.listeners.add(this);
//				for(String url : urlsToLoad)
//					loaderInstance.loadUrl(url);
//			}
//		}
	}

	private Map<String, Image> imagesByUrl;
	private Set<String> loadedUrls;
	private Set<ImageLoadListener> listeners;
	
	// public:

	public void loadImage(String url, ImageLoadListener listener)
	{
		Collection<String> urlList = new LinkedList<String>();
		urlList.add(url);
		loadImages(urlList, listener);
	}
	
	public void loadImages(Collection<String> urls, ImageLoadListener listener)
	{
		debugPrint("Load request for " + urls.size() + " images.");
		if(loadedUrls.containsAll(urls))
		{
			debugPrint("All images already loaded, calling listener now.");
			if(listener != null)
				listener.onLoaded();
			return;
		}
		if(listener != null)
		{
			listener.urlsToLoad = urls;
			listeners.add(listener);
		}
		for(String url : urls)
			if(!imagesByUrl.containsKey(url))
			{
				Image img = new Image(url);
				RootPanel.get().add(img);
				img.getElement().getStyle().setDisplay(Display.NONE);
				InternalImageLoadListener singleListener = new InternalImageLoadListener(url);
				img.addLoadHandler(singleListener);
				img.addErrorHandler(singleListener);
				imagesByUrl.put(url, img);
				debugPrint("Now loading image: " + url);
			}
			else
				debugPrint("Single image already loaded or loading: " + url);
	}
	
	public Image getImageByUrl(String url) {
		if(!imagesByUrl.containsKey(url))
			throw new RuntimeException("Can't return image für " + url + " since it was never requested.");
		else if(!loadedUrls.contains(url))
			throw new RuntimeException("Image loading not finished, still waiting for " + url + " to load.");
		return imagesByUrl.get(url);
	}
	
	// private:
	
	private static void debugPrint(String message)
	{
		// System.out.println("ImageUrlLoader Debug: " + message);
	}
	
	private ImageUrlLoader() {
		imagesByUrl = new TreeMap<String, Image>();
		listeners = new HashSet<ImageLoadListener>();
		loadedUrls = new TreeSet<String>();
	}
	
	private void onImageError(String url) {
		debugPrint("Error while loading " + url + ", now notifying all listeners.");
		for(Iterator<ImageLoadListener> it = listeners.iterator(); it.hasNext(); )
		{
			if(it.next().imageError(url))
				it.remove();
		}
	}

	private void onImageLoaded(String url) {
		debugPrint("Finished loading " + url + ", now notifying all listeners.");
		loadedUrls.add(url);
		for(Iterator<ImageLoadListener> it = listeners.iterator(); it.hasNext(); )
		{
			if(it.next().imageLoaded(url))
				it.remove();
		}
	}

}
