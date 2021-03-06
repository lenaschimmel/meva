package de.gmino.meva.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.gmino.meva.server.request.LocalRequetsImpl;
import de.gmino.meva.server.request.NetworkRequestsImplAsyncLocalSql;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityFactoryInterface;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.shared.ValueQuery;
import de.gmino.meva.shared.request.Requests;

public class BinaryServer extends HttpServlet {

	private static final long serialVersionUID = -3067797075737374489L;
	
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

		if (lastPart.equals("getEntities")) {
			getEntities(req, resp);
		} else if (lastPart.equals("newEntities")) {
			newEntities(req, resp);
		} else if (lastPart.equals("allEntities")) {
			allEntities(req, resp);
		} else if (lastPart.equals("saveEntities")) {
			saveEntities(req, resp);
		} else {
			otherRequest(req, resp, lastPart);
		}
	}

	private void otherRequest(HttpServletRequest req, HttpServletResponse resp, String lastPart) throws IOException {

		Object query = EntityFactory.createQueryObject(lastPart, new DataInputStream(req.getInputStream()));
		
		DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
		if (query instanceof EntityQuery) {
			EntityQuery entityQuery = (EntityQuery)query;
			System.out.println("Evaluating " + entityQuery);
			Collection<Long> ids = entityQuery.evaluate();
			for (long id : ids)
				dos.writeLong(id);
			dos.writeLong(0);
		} else {
			ValueQuery valueQuery = (ValueQuery)query;
			System.out.println("Evaluating " + valueQuery);
			Collection<Value> values = valueQuery.evaluate();
			dos.writeInt(values.size());
			for (Value val : values)
				((ValueBinary) val).serializeBinary(dos);
		}
		dos.flush();
		dos.close();
	}

	private void saveEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Connection dbCon = SqlHelper.getConnection();
		Query query = null;
		DataInputStream dis = new DataInputStream(req.getInputStream());
		String typeName = dis.readUTF();
		TypeName type = TypeName.getEntityByString(typeName, false);
		long id = dis.readLong();
		Collection<Entity> entities = new LinkedList<Entity>();
		while (id != 0) {
			Entity e = EntityFactory.getUnloadedEntityById(type, id);
			try {
				((EntityBinary) e).deserializeBinary(dis);
				entities.add(e);
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
			id = dis.readLong();
		}
		LocalRequetsImpl.saveEntities(entities);

		DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
		dos.writeLong(0);
		dos.flush();
		dos.close();
	}

	private void getEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DataInputStream dis = new DataInputStream(req.getInputStream());
		String typeName = dis.readUTF();
		TypeName type = TypeName.getEntityByString(typeName, false);
		long id = dis.readLong();

		Collection<Long> ids = new LinkedList<Long>();
		while (id != 0) {
			ids.add(id);
			id = dis.readLong();
		}

		System.out.println("Got a binary query for ids of type " + typeName + ": " + Arrays.toString(ids.toArray()));

		Collection<Entity> entities = LocalRequetsImpl.getLoadedEntitiesById(type, ids);

		DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
		for (Entity e : entities) {
			((EntityBinary) e).serializeBinary(dos);
			System.out.println("Written " + e.toShortString());
		}
		dos.flush();
		dos.close();
	}

	private void newEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DataInputStream dis = new DataInputStream(req.getInputStream());
		String typeName = dis.readUTF();
		TypeName type = TypeName.getEntityByString(typeName, false);
		int count = dis.readInt();

		Collection<Entity> entities = LocalRequetsImpl.getNewEntities(type, count);

		DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
		for (Entity e : entities) {
			dos.writeLong(e.getId());
		}
		dos.flush();
		dos.close();
	}


	private void allEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DataInputStream dis = new DataInputStream(req.getInputStream());
		String typeName = dis.readUTF();
		TypeName type = TypeName.getEntityByString(typeName, false);
		
		Collection<Long> ids = LocalRequetsImpl.getIdsByType(type);

		DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
		for (Long id : ids) {
			dos.writeLong(id);
		}
		dos.writeLong(0);
		dos.flush();
		dos.close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Huhu!");
		resp.getWriter()
				.write("Yeah, du lustige Tante, ich bin hier! Du solltest aber POST-Request machen, nicht GET. Versuche doch mal <a href=\"http://gmino.de/legacy/php/post.php\">http://gmino.de/legacy/php/post2.php</a>.");
		/*
		 * String fid = req.getParameter("facebookId").toString();
		 * 
		 * Shop ret = (Shop) Shop.getById(Long.parseLong(fid)); StringBuilder sb
		 * = new StringBuilder(); if(ret != null) ret.serializeJson(sb); else
		 * sb.append("{ \"error\":\"Shop is null.\"}");
		 * 
		 * PrintWriter out = resp.getWriter(); out.write(sb.toString());
		 * out.flush();
		 */
	}

	String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

}
