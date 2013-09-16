package de.gmino.meva.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.standard.StandardConfig;

import de.gmino.meva.server.request.LocalRequetsImpl;
import de.gmino.meva.server.request.NetworkRequestsImplAsyncLocalSql;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityFactoryInterface;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueQuery;
import de.gmino.meva.shared.request.Requests;

public class JsonServer extends HttpServlet {

	private static final long serialVersionUID = -3067797075737374489L;
	private JsonSystem jsonSystem;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			String entityFactoryClass = getServletContext().getInitParameter("entityFactoryClass");
			EntityFactoryInterface factory = (EntityFactoryInterface)Class.forName(entityFactoryClass).newInstance();
			EntityFactory.setImplementations(factory);
			Requests.setImplementation(new NetworkRequestsImplAsyncLocalSql());
			String sqlConnectionUrl = getServletContext().getInitParameter("sqlConnectionUrl");
			SqlHelper.setConnectionUrl(sqlConnectionUrl);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("getRequestURI(): " + req.getRequestURI());
		String[] parts = req.getRequestURI().split("/");
		String lastPart = parts[parts.length - 1];

		HttpSession session = req.getSession();
		if(session != null)
		{
			session.getAttribute("loggedInUserId");
			
		}
		
		try {
			if (lastPart.equals("getEntities")) {
				getEntities(req, resp);
			} else if (lastPart.equals("newEntities")) {
				newEntities(req, resp);
			} else if (lastPart.equals("allEntities")) {
				allEntities(req, resp);
			} else if (lastPart.equals("saveEntities")) {
				saveEntities(req, resp);
			} else if (lastPart.equals("login")) {
				login(req, resp);
			} else if (lastPart.equals("logout")) {
				logout(req, resp);
			} else {
				otherRequest(req, resp, lastPart);
			}
		} catch (Exception e) {
			resp.setContentType("text/json");
			System.err.println("Error while answering a POST-JSON-request. Error will be reportet to the client as short, JSON-Encoded answer. Full stack trace below:");
			e.printStackTrace();
			writeAnswer(resp.getOutputStream(), "ERROR", "\"An Exception occured while processing the request: " + Util.escapeForJson(e.toString())
					+ " A more detailed cause has been written to stderr on the server.\"");
		}
	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession(true).invalidate();
		resp.setContentType("text/json; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		writeAnswer(resp.getOutputStream(), "OK", "\"User logged out.\"");
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
		String requestString = convertStreamToString(req.getInputStream());
		JsonValue requestValue = getJsonSystem().parse(requestString);
		JsonObject requestObject = requestValue.asObject();

		String username = requestObject.getString("username");
		String password = requestObject.getString("password");
	
		Connection dbCon = SqlHelper.getConnection();
		Statement stmt = dbCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT id FROM User WHERE userName='"+username+"' AND password='"+password+"';");
		String content = "";
		String status = "ERROR";
		if(rs.next())
		{
			long userId = rs.getLong(1);
			content = userId + "";
			status = "OK";
			req.getSession(true).setAttribute("loggedInUserId", userId + "");
		}
		
		resp.setContentType("text/json; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		writeAnswer(resp.getOutputStream(), status, content);
	}

	private void otherRequest(HttpServletRequest req, HttpServletResponse resp, String lastPart) throws IOException {

		String input = convertStreamToString(req.getInputStream());
		System.out.println("Query: " + input);
		JsonObject request = getJsonSystem().parse(input).asObject();

		Object query = EntityFactory.createQueryObject(lastPart, request);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		if (query instanceof EntityQuery) {
			EntityQuery entityQuery = (EntityQuery)query;
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
			ValueQuery valueQuery = (ValueQuery)query;
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
		resp.setCharacterEncoding("utf-8");
		writeAnswer(resp.getOutputStream(), "OK", sb.toString());
	}

	/**
	 * 
	 * @param outputStream
	 * @param status
	 * @param content
	 *            Please note that you must wrap Strings in an extra pair of
	 *            (escaped) quotes if they are used as Json Strong values,
	 *            because this parameter is written "as is", so that you can
	 *            pass a Number or Array or Object.
	 */
	private void writeAnswer(ServletOutputStream outputStream, String status, String content) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
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
		String requestString = convertStreamToString(req.getInputStream());
		JsonValue requestValue = getJsonSystem().parse(requestString);
		JsonObject requestObject = requestValue.asObject();

		String typeName = requestObject.getString("typeName");
		TypeName type = TypeName.getEntityByString(typeName, true);
		JsonObject entitiesMap = requestObject.getObject("entities");

		Collection<Entity> entitiesToSave = new ArrayList<Entity>(entitiesMap.size());

		for (Entry<String, JsonValue> entry : entitiesMap.entrySet()) {
			long id = Long.parseLong(entry.getKey());
			Entity e = EntityFactory.getUnloadedEntityById(type, id);
			e.deserializeJson(entry.getValue().asObject());
			entitiesToSave.add(e);
		}
		LocalRequetsImpl.saveEntities(entitiesToSave);

		resp.setContentType("text/json; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		writeAnswer(resp.getOutputStream(), "OK", "\"All entities saved to the database.\"");
	}

	private void getEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String requestString = convertStreamToString(req.getInputStream());
		JsonValue requestValue = getJsonSystem().parse(requestString);
		JsonObject requestObject = requestValue.asObject();

		String typeName = requestObject.getString("typeName");
		TypeName type = TypeName.getEntityByString(typeName, true);
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

		String requestString = convertStreamToString(req.getInputStream());
		JsonValue requestValue = getJsonSystem().parse(requestString);
		JsonObject requestObject = requestValue.asObject();

		String typeName = requestObject.getString("typeName");
		TypeName type = TypeName.getEntityByString(typeName, true);
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
		resp.setContentType("text/json; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		writeAnswer(resp.getOutputStream(), "OK", sb.toString());
	}


	private void allEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String requestString = convertStreamToString(req.getInputStream());
		JsonValue requestValue = getJsonSystem().parse(requestString);
		JsonObject requestObject = requestValue.asObject();

		String typeName = requestObject.getString("typeName");
		TypeName type = TypeName.getEntityByString(typeName, true);
		
		Collection<Long> ids = LocalRequetsImpl.getIdsByType(type);

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
		resp.setContentType("text/json; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
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
	
	private JsonSystem getJsonSystem()
	{
		if(jsonSystem == null)
			jsonSystem = StandardConfig.createSystem();
		return jsonSystem;
	}
}
