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

import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.LatLon;

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
		searchLocationByUrl(listener, url);
	}

	private void searchLocationByUrl(final SearchLocationListener listener,
			String url) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
		rb.setHeader("User-Agent", "geoEngine/3 (gmino.de, semi-manual, semi-automatic one-time bulk request)");
		try {
			rb.sendRequest("", new RequestCallback() {

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
	

	public void searchLocationByAddress(Address address,
			SearchLocationListener searchLocationListener) {
		StringBuilder query = new StringBuilder();
		addComponentIfNotEmpty(query, address.getCity(), "city");
		addComponentIfNotEmpty(query, address.getHouseNumber() + " " + address.getStreet(), "street");
		addComponentIfNotEmpty(query, address.getZip(), "postalcode");
		String url = "http://open.mapquestapi.com/nominatim/v1/search?format=json&q=" + query.toString() + "&addressdetails=1&limit=1";
		System.out.println("Geocoder Search URL: " + url);
		searchLocationByUrl(searchLocationListener, url);
	}
	

	private void addComponentIfNotEmpty(StringBuilder sb,
			String value, String paramName) {
		if(value == null || value.length() == 0)
			return;
		sb.append("&");
		sb.append(paramName + "=" + URL.encodeQueryString(value));
	}
}
