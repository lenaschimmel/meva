package de.gmino.checkin.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.gwt.GwtSystem;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.standard.StandardConfig;

import de.gmino.checkin.server.request.QueryNearbyShops;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.ReturnEntityPolicy;

public class JsonServer extends HttpServlet {

	private static final long serialVersionUID = -3067797075737374489L;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			EntityFactory.setImplementations(new EntityFactoryImpl(),
					new EntityRequestSql());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("getRequestURI(): " + req.getRequestURI());
		String[] parts = req.getRequestURI().split("/");
		String lastPart = parts[parts.length - 1];

		if (lastPart.equals("getEntities")) {
			getEntities(req, resp);
		} else if (lastPart.equals("newEntities")) {
			newEntities(req, resp);
		} else if (lastPart.equals("saveEntities")) {
			saveEntities(req, resp);
		} else {
			otherRequest(req, resp, lastPart);
		}
	}

	private void otherRequest(HttpServletRequest req, HttpServletResponse resp,
			String lastPart) throws IOException {
		Query query = null;

		JsonSystem system = StandardConfig.createSystem();
		
		JsonObject request = system.parseReader(
				new InputStreamReader(req.getInputStream())).asObject();

		if (lastPart.equals("QueryNearbyShops"))
			query = new QueryNearbyShops(request);
		if (query == null)
			throw new RuntimeException("Unrecognized query type: " + lastPart);
		System.out.println("Got a JSON query of type " + lastPart);
		System.out.println(query.toString());
		Collection<Long> ids = query.evaluate();

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
		writeAnswer(resp.getOutputStream(), "OK", sb.toString());
	}

	private void writeAnswer(ServletOutputStream outputStream, String status,
			String content) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(outputStream);
			osw.write("{ \"status\":\"" + status + "\" , \"content\":" + content + "}");
			osw.flush();
			osw.close();
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void saveEntities(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		writeAnswer(resp.getOutputStream(), "ERROR", "This kind of request is currently not available as JSON. Use the binary interface or ask Lena.");

	}

	private void getEntities(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		JsonSystem system = StandardConfig.createSystem();
				//new ItemscriptSystem(new GwtConfig()); 
				//GwtSystem.SYSTEM;
		ServletInputStream inputStream = req.getInputStream();
		String requestString =new java.util.Scanner(inputStream).useDelimiter("\\A").next();
		JsonValue requestValue = system.parse(requestString);
		JsonObject requestObject = requestValue.asObject();
		
		String typeName = requestObject.getString("typeName");
		JsonArray idArray = requestObject.getArray("ids");
		
		Collection<Long> ids = new LinkedList<Long>();
		for (JsonValue id : idArray) {
			ids.add(Long.parseLong(id.asString().stringValue()));
		}

		System.out.println("Got a JSON query for ids of type " + typeName + ": "
				+ Arrays.toString(ids.toArray()));

		Collection<Entity> entities = EntityFactory.getEntitiesById(typeName,
				ids, ReturnEntityPolicy.BLOCK_ALWAYS);

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

		writeAnswer(resp.getOutputStream(), "OK", sb.toString());
	}

	private void newEntities(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		writeAnswer(resp.getOutputStream(), "ERROR", "This kind of request is currently not available as JSON. Use the binary interface or ask Lena.");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Huhu!");
		writeAnswer(resp.getOutputStream(), "ERROR", "\"Yeah, du lustige Tante, ich bin hier! Du solltest aber POST-Request machen, nicht GET. Versuche doch mal <a href=\\\"http://gmino.de/legacy/php/post.php\\\">http://gmino.de/legacy/php/post2.php</a>.\"");
	}

	String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}
}
