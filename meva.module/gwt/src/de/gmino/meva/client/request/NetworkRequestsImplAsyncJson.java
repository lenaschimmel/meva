package de.gmino.meva.client.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.gwt.GwtSystem;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

import de.gmino.meva.client.domain.KeyValueDef;
import de.gmino.meva.client.domain.KeyValueSet;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueQuery;
import de.gmino.meva.shared.request.NetworkRequests;
import de.gmino.meva.shared.request.RequestListener;

public class NetworkRequestsImplAsyncJson implements NetworkRequests {

	private String baseUrl;

	public NetworkRequestsImplAsyncJson(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public <ValueClass extends Value> void getValuesByQuery(final ValueQuery query, final RequestListener<ValueClass> listener) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, baseUrl + "Json/" + query.getUrlPostfix());
		rb.setHeader("Content-Type", "application/json");
		try {
			rb.sendRequest(query.toString(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Collection<Value> values = new LinkedList<Value>();
					JsonSystem system = GwtSystem.SYSTEM;
					String jsonString = response.getText();
					JsonObject answer = system.parse(jsonString).asObject();
					String status = answer.getString("status");
					if (status.equals("ERROR")) {
						String message = answer.getString("content");
						if(listener != null)
							listener.onError("The server reported an error: " + message, null);
					} else {
						try {
							JsonArray idValues = answer.getArray("content");
							for (JsonValue json : idValues) {
								ValueClass val = (ValueClass) query.valueFromJson(json.asObject());
								values.add(val);
								if(listener != null)
									listener.onNewResult(val);
							}
							if(listener != null)
								listener.onFinished((Collection<ValueClass>) values);
						} catch (IOException e) {
							e.printStackTrace();
							if(listener != null)
								listener.onError("Error while parsing Json reply to ValueQuery", e);
						}
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					if(listener != null)
						listener.onError("Json request generated an exception (via method).", exception);
				}
			});
		} catch (Exception exception) {
			if(listener != null)
				listener.onError("Json request generated an exception (thrown).", exception);
		}
	}

	@Override
	public void getIdsByQuery(EntityQuery query, final RequestListener<Long> listener) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, baseUrl + "Json/" + query.getUrlPostfix());
		rb.setHeader("Content-Type", "application/json");
		try {
			rb.sendRequest(query.toString(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Collection<Long> ids = new LinkedList<Long>();
					JsonSystem system = GwtSystem.SYSTEM;
					String jsonString = response.getText();
					System.out.println("JSON answer: " + jsonString);
					JsonObject answer = system.parse(jsonString).asObject();
					String status = answer.getString("status");
					if (status.equals("ERROR")) {
						String message = answer.getString("content");
						if(listener != null)
							listener.onError("The server reported an error: " + message, null);
					} else {
						JsonArray idValues = answer.getArray("content");
						for (JsonValue idValue : idValues) {
							Long id = Long.parseLong(idValue.asString().stringValue());
							ids.add(id);
							if(listener != null)
								listener.onNewResult(id);
						}
						if(listener != null)
							listener.onFinished(ids);
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					if(listener != null)
						listener.onError("Json request generated an exception (via method).", exception);
				}
			});
		} catch (Exception exception) {
			if(listener != null)
				listener.onError("Json request generated an exception (thrown).", exception);
		}
	}

	@Override
	public void getNewIds(TypeName type, int count, final RequestListener<Long> listener) {
		if(count == 0)
		{
			Collection<Long> ids = new LinkedList<Long>();
			listener.onFinished(ids);
			return;
		}
		
		final String typeName = type.toString();

		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, baseUrl + "Json/newEntities");
		rb.setHeader("Content-Type", "application/json");
		try {
			rb.sendRequest("{\"typeName\":\"" + typeName + "\", \"count\":\"" + count + "\"}", new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Collection<Long> ids = new LinkedList<Long>();
					JsonSystem system = GwtSystem.SYSTEM;
					String jsonString = response.getText();
					JsonObject answer = system.parse(jsonString).asObject();
					String status = answer.getString("status");
					if (status.equals("ERROR")) {
						String message = answer.getString("content");
						listener.onError("The server reported an error: " + message, null);
					} else {
						JsonArray idValues = answer.getArray("content");
						for (JsonValue idValue : idValues) {
							Long id = Long.parseLong(idValue.asString().stringValue());
							ids.add(id);
							listener.onNewResult(id);
						}
						listener.onFinished(ids);
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					listener.onError("Json request generated an exception (via method).", exception);
				}
			});
		} catch (Exception exception) {
			listener.onError("Json request generated an exception (thrown).", exception);
		}
	}

	@Override
	public <EntityClass extends Entity> void loadEntities(final Collection<EntityClass> entities, final RequestListener<EntityClass> listener) {
		if(entities.isEmpty())
		{
			listener.onFinished(entities);
			return;
		}

		final Collection<EntityClass> entitiesToLoad = new ArrayList<EntityClass>();
		
		long now = System.currentTimeMillis();
		long loadTreshold = now - 2 * 1000; // 2 Seconds
		
		TypeName type = entities.iterator().next().getType();
		if(type == KeyValueSet.type || type == KeyValueDef.type)
			loadTreshold = now - 120 * 1000; // 2 Minutes
		
		for (EntityClass e : entities) {
			if (!e.isReady() || e.getJsonLoadTime() < loadTreshold)
			{
				entitiesToLoad.add(e);
				e.setJsonLoadTime(now);
			}
			else
				listener.onNewResult(e);
		}
		
		if(entitiesToLoad.isEmpty())
		{
			//System.out.println("Nothing to load, all present.");
			listener.onFinished(entities);
			return;
		}
		
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, baseUrl + "Json/getEntities");
		rb.setHeader("Content-Type", "application/json");
		try {
			String typeName = entitiesToLoad.iterator().next().getTypeName();
			StringBuilder sbIdArrayString = new StringBuilder();
			for (EntityClass e : entitiesToLoad) {
				if (sbIdArrayString.length() > 0)
					sbIdArrayString.append(",");
				sbIdArrayString.append('"');
				sbIdArrayString.append(e.getId());
				sbIdArrayString.append('"');
			}
			rb.sendRequest("{\"typeName\":\"" + typeName + "\", \"ids\":[" + sbIdArrayString.toString() + "]}", new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Collection<Long> ids = new LinkedList<Long>();
					JsonSystem system = GwtSystem.SYSTEM;
					String jsonString = response.getText();
					JsonObject answer = system.parse(jsonString).asObject();
					String status = answer.getString("status");
					if (status.equals("ERROR")) {
						String message = answer.getString("content");
						listener.onError("The server reported an error: " + message, null);
					} else {
						JsonArray entityValues = answer.getArray("content");
						Iterator<EntityClass> it = entitiesToLoad.iterator();
						for (JsonValue entityValue : entityValues) {
							EntityClass toRead = it.next();
							try {
								toRead.deserializeJson(entityValue.asObject());
							} catch (IOException e) {
								e.printStackTrace();
								throw new RuntimeException(e);
							}
							listener.onNewResult(toRead);
						}
						listener.onFinished(entities);
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					listener.onError("Json request generated an exception (via method).", exception);
				}
			});
		} catch (Exception exception) {
			listener.onError("Json request generated an exception (thrown).", exception);
		}
	}

	@Override
	public <EntityClass extends Entity> void saveEntities(final Collection<EntityClass> entities, final RequestListener<EntityClass> listener) {
		if(entities.isEmpty())
		{
			listener.onFinished(entities);
			return;
		}
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, baseUrl + "Json/saveEntities");
		rb.setHeader("Content-Type", "application/json");
		try {
			String typeName = entities.iterator().next().getTypeName();
			StringBuilder requestBody = new StringBuilder("{\"typeName\":\"" + typeName + "\", \"entities\":{");
			boolean first = true;
			for (EntityClass e : entities) {
				if (!first)
					requestBody.append(',');
				requestBody.append('"');
				requestBody.append(e.getId());
				requestBody.append('"');
				requestBody.append(':');
				e.serializeJson(requestBody);
				first = false;
			}
			requestBody.append("}}");

			rb.sendRequest(requestBody.toString(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Collection<Long> ids = new LinkedList<Long>();
					JsonSystem system = GwtSystem.SYSTEM;
					String jsonString = response.getText();
					JsonObject answer = system.parse(jsonString).asObject();
					String status = answer.getString("status");
					if (status.equals("ERROR")) {
						String message = answer.getString("content");
						if (listener != null)
							listener.onError("The server reported an error: " + message, null);
						else
							throw new RuntimeException("The server reported an error: " + message, null);
					} else {
						if (listener != null)
							listener.onFinished(entities);
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					if (listener != null)
						listener.onError("Json request generated an exception (via method).", exception);
					else
						throw new RuntimeException("Json request generated an exception (via method).", exception);
				}
			});
		} catch (Exception exception) {
			if (listener != null)
				listener.onError("Json request generated an exception (thrown).", exception);
			else
				throw new RuntimeException("Json request generated an exception (thrown).", exception);
		}
	}

	@Override
	public void getIdsByType(TypeName type, final RequestListener<Long> listener) {
		final String typeName = type.toString();

		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, baseUrl + "Json/allEntities");
		rb.setHeader("Content-Type", "application/json");
		try {
			rb.sendRequest("{\"typeName\":\"" + typeName + "\"}", new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Collection<Long> ids = new LinkedList<Long>();
					JsonSystem system = GwtSystem.SYSTEM;
					String jsonString = response.getText();
					JsonObject answer = system.parse(jsonString).asObject();
					String status = answer.getString("status");
					if (status.equals("ERROR")) {
						String message = answer.getString("content");
						listener.onError("The server reported an error: " + message, null);
					} else {
						JsonArray idValues = answer.getArray("content");
						for (JsonValue idValue : idValues) {
							Long id = Long.parseLong(idValue.asString().stringValue());
							ids.add(id);
							listener.onNewResult(id);
						}
						listener.onFinished(ids);
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					listener.onError("Json request generated an exception (via method).", exception);
				}
			});
		} catch (Exception exception) {
			listener.onError("Json request generated an exception (thrown).", exception);
		}
	}
}
