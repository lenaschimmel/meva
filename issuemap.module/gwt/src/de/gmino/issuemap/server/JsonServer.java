package de.gmino.issuemap.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.standard.StandardConfig;

import de.gmino.issuemap.client.IssuemapGwt;
import de.gmino.issuemap.server.request.LocalRequetsImpl;
import de.gmino.issuemap.server.request.NetworkRequestsImplAsyncLocalSql;
import de.gmino.issuemap.server.request.QueryMapBySubdomain;
import de.gmino.issuemap.server.request.SendFeedback;
import de.gmino.issuemap.server.EntityFactoryImpl;
import de.gmino.issuemap.shared.Log;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.shared.ValueQuery;
import de.gmino.meva.shared.request.Requests;

public class JsonServer extends HttpServlet {

	private static final long serialVersionUID = -3067797075737374489L;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			EntityFactory.setImplementations(new EntityFactoryImpl());
			Requests.setImplementation(new NetworkRequestsImplAsyncLocalSql());
		} catch (Exception e) {
			Log.exception("Error initializing Servlet..", e);
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("getRequestURI(): " + req.getRequestURI());
		String[] parts = req.getRequestURI().split("/");
		String lastPart = parts[parts.length - 1];

		try {
			if (lastPart.equals("getEntities")) {
				getEntities(req, resp);
			} else if (lastPart.equals("newEntities")) {
				newEntities(req, resp);
			} else if (lastPart.equals("saveEntities")) {
				saveEntities(req, resp);
			} else {
				otherRequest(req, resp, lastPart);
			}
		} catch (Exception e) {
			resp.setContentType("text/json");
			Log.exception("Error while answering a POST-JSON-request. Error will be reportet to the client as short, JSON-Encoded answer. Full stack trace below:", e);
			writeAnswer(resp.getOutputStream(), "ERROR", "\"An Exception occured while processing the request: " + Util.escapeForJson(e.toString())
					+ " A more detailed cause has been written to stderr on the server.\"");
		}
	}

	private void otherRequest(HttpServletRequest req, HttpServletResponse resp, String lastPart) throws IOException {
		EntityQuery entityQuery = null;
		ValueQuery valueQuery = null;

		JsonSystem system = StandardConfig.createSystem();

		String input = convertStreamToString(req.getInputStream());
		System.out.println("Query: " + input);
		JsonObject request = system.parse(input).asObject();

//		if (lastPart.equals("QueryNearbyShops"))
//			entityQuery = new QueryNearbyShops(request);
//		else if (lastPart.equals("QueryShopByCode"))
//			entityQuery = new QueryShopByCode(request);
//		else if (lastPart.equals("QueryConsumerByFid"))
//			entityQuery = new QueryConsumerByFid(request);
//		else if (lastPart.equals("QueryPerformCheckin"))
//			valueQuery = new QueryPerformCheckin(request);
		if (lastPart.equals("QueryMapBySubdomain"))
			entityQuery = new QueryMapBySubdomain(request);
		else if (lastPart.equals("SendFeedback"))
			valueQuery = new SendFeedback(request);
		else
			throw new RuntimeException("Unrecognized query type: " + lastPart);

		StringBuilder sb = new StringBuilder();
		sb.append("[");

		if (entityQuery != null) {
			Collection<Long> ids = entityQuery.evaluate();
			boolean first = true;
			for (long id : ids) {
				if (!first)
					sb.append(',');
				sb.append('"');
				sb.append(id);
				sb.append('"');
				first = false;
			}
		} else {
			Collection<Value> values = valueQuery.evaluate();
			boolean first = true;
			for (Value val : values) {
				if (!first)
					sb.append(',');
				sb.append('"');
				val.serializeJson(sb);
				sb.append('"');
				first = false;
			}
		}

		sb.append("]");
		resp.setContentType("text/json");
		writeAnswer(resp.getOutputStream(), "OK", sb.toString());
	}

	/**
	 * 
	 * @param outputStream
	 * @param status
	 * @param content
	 *            Please not that you must wrap Strings in an extra pair of
	 *            (escaped) quotes if they are used as Json Strong values,
	 *            because this parameter is written "as is", so that you can
	 *            pass a Number or Array or Object.
	 */
	private void writeAnswer(ServletOutputStream outputStream, String status, String content) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
			//OutputStreamWriter osw = new OutputStreamWriter(outputStream, Charset.forName("ISO-8859-1"));
			osw.write("{ \"status\":\"" + status + "\" , \"content\":" + content + "}");
			osw.flush();
			osw.close();
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void saveEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonSystem system = StandardConfig.createSystem();
		ServletInputStream inputStream = req.getInputStream();
		String requestString = new java.util.Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
		JsonValue requestValue = system.parse(requestString);
		JsonObject requestObject = requestValue.asObject();

		String typeName = requestObject.getString("typeName");
		EntityTypeName type = EntityTypeName.getByString(typeName);
		JsonObject entitiesMap = requestObject.getObject("entities");

		Collection<Entity> entitiesToSave = new ArrayList<Entity>(entitiesMap.size());

		for (Entry<String, JsonValue> entry : entitiesMap.entrySet()) {
			long id = Long.parseLong(entry.getKey());
			Entity e = EntityFactory.getUnloadedEntityById(type, id);
			e.deserializeJson(entry.getValue().asObject());
			entitiesToSave.add(e);
		}
		LocalRequetsImpl.saveEntities(entitiesToSave);

		resp.setContentType("text/json");
		writeAnswer(resp.getOutputStream(), "OK", "\"All entities saved to the database.\"");
	}

	private void getEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonSystem system = StandardConfig.createSystem();
		ServletInputStream inputStream = req.getInputStream();
		String requestString = new java.util.Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
		JsonValue requestValue = system.parse(requestString);
		JsonObject requestObject = requestValue.asObject();

		String typeName = requestObject.getString("typeName");
		EntityTypeName type = EntityTypeName.getByString(typeName);
		JsonArray idArray = requestObject.getArray("ids");

		Collection<Long> ids = new LinkedList<Long>();
		for (JsonValue id : idArray) {
			ids.add(Long.parseLong(id.asString().stringValue()));
		}

		System.out.println("Got a JSON query for ids of type " + typeName + ": " + Arrays.toString(ids.toArray()));

		Collection<Entity> entities = LocalRequetsImpl.getLoadedEntitiesById(type, ids);

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		for (Entity e : entities) {
			if (!first)
				sb.append(',');
			e.serializeJson(sb);
			first = false;
		}
		sb.append("]");

		resp.setContentType("text/json; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		writeAnswer(resp.getOutputStream(), "OK", sb.toString());
	}

	private void newEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		JsonSystem system = StandardConfig.createSystem();
		ServletInputStream inputStream = req.getInputStream();
		String requestString = new java.util.Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
		JsonValue requestValue = system.parse(requestString);
		JsonObject requestObject = requestValue.asObject();

		String typeName = requestObject.getString("typeName");
		EntityTypeName type = EntityTypeName.getByString(typeName);
		int count = Integer.parseInt(requestObject.getString("count"));

		Collection<Long> ids = LocalRequetsImpl.getNewIds(type, count);

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		for (long id : ids) {
			if (!first)
				sb.append(',');
			sb.append('"');
			sb.append(id);
			sb.append('"');
			first = false;
		}
		sb.append("]");
		resp.setContentType("text/json");
		writeAnswer(resp.getOutputStream(), "OK", sb.toString());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Huhu!");
		resp.setContentType("text/json");
		writeAnswer(
				resp.getOutputStream(),
				"ERROR",
				"\"Yeah, du lustige Tante, ich bin hier! Du solltest aber POST-Request machen, nicht GET. Versuche doch mal <a href=\\\"http://gmino.de/legacy/php/post.php\\\">http://gmino.de/legacy/php/post2.php</a>.\"");
	}

	String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is, "UTF-8").useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}
}
