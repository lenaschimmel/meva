package de.gmino.geobase.client.request;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.gwt.GwtSystem;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

import de.gmino.geobase.client.domain.LatLon;

public class Geocoder {
	public interface SearchLocationListener
	{
		public void onLocationFound(LatLon location);
		public void onLocationNotFound();
		public void onError(String message);
	}
	
	public void searchLocationByString(String query, final SearchLocationListener listener)
	{
		String url = "http://open.mapquestapi.com/nominatim/v1/search?format=json&q=" + URL.encodeQueryString(query) + "&addressdetails=1&limit=1";
		
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
		try {
			rb.sendRequest(query.toString(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					JsonSystem system = GwtSystem.SYSTEM;
					String jsonString = response.getText();
					JsonArray results = system.parse(jsonString).asArray();
					if(results.size() == 0)
					{
						listener.onLocationNotFound();
						return;
					}
					JsonObject firstResult = results.get(0).asObject();
					double lat = Double.parseDouble(firstResult.get("lat").asString().stringValue());
					double lon = Double.parseDouble(firstResult.get("lon").asString().stringValue());
					listener.onLocationFound(new LatLon(lat, lon));
				}

				@Override
				public void onError(Request request, Throwable exception) {
					listener.onError("Exception while performing GeoQuery (inner): " + exception.getMessage());
				}
			});
		} catch (Exception exception) {
			exception.printStackTrace();
			listener.onError("Exception while performing GeoQuery (outer): " + exception.getMessage());
		}
	}
}
