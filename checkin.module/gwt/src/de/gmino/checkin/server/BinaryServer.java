package de.gmino.checkin.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.gmino.checkin.server.request.LocalRequetsImpl;
import de.gmino.checkin.server.request.NetworkRequestsImplAsyncLocalSql;
import de.gmino.checkin.server.request.QueryConsumerByFid;
import de.gmino.checkin.server.request.QueryNearbyShops;
import de.gmino.checkin.server.request.QueryPerformCheckin;
import de.gmino.checkin.server.request.QueryShopByCode;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntityQuery;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.shared.ValueQuery;
import de.gmino.meva.shared.request.Requests;

public class BinaryServer extends HttpServlet {

	private static final long serialVersionUID = -3067797075737374489L;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			EntityFactory.setImplementations(new EntityFactoryImpl());
			Requests.setImplementation(new NetworkRequestsImplAsyncLocalSql());
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
		} else if (lastPart.equals("saveEntities")) {
			saveEntities(req, resp);
		} else {
			otherRequest(req, resp, lastPart);
		}
	}

	private void otherRequest(HttpServletRequest req, HttpServletResponse resp, String lastPart) throws IOException {
		EntityQuery entityQuery = null;
		ValueQuery valueQuery = null;
		if (lastPart.equals("QueryNearbyShops"))
			entityQuery = new QueryNearbyShops(new DataInputStream(req.getInputStream()));
		else if (lastPart.equals("QueryShopByCode"))
			entityQuery = new QueryShopByCode(new DataInputStream(req.getInputStream()));
		else if (lastPart.equals("QueryConsumerByFid"))
			entityQuery = new QueryConsumerByFid(new DataInputStream(req.getInputStream()));
		else if (lastPart.equals("QueryPerformCheckin"))
			valueQuery = new QueryPerformCheckin(new DataInputStream(req.getInputStream()));
		else
			throw new RuntimeException("Unrecognized query type: " + lastPart);

		DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
		if (entityQuery != null) {
			System.out.println("Evaluating " + entityQuery);
			Collection<Long> ids = entityQuery.evaluate();
			for (long id : ids)
				dos.writeLong(id);
			dos.writeLong(0);
		}
		else
		{
			System.out.println("Evaluating " + valueQuery);
			Collection<Value> values = valueQuery.evaluate();
			dos.writeInt(values.size());
			for (Value val : values)
				((ValueBinary)val).serializeBinary(dos);
		}
		dos.flush();
		dos.close();
	}

	private void saveEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Connection dbCon = SqlHelper.getConnection();
		Query query = null;
		DataInputStream dis = new DataInputStream(req.getInputStream());
		String typeName = dis.readUTF();
		EntityTypeName type = EntityTypeName.getByString(typeName);
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
		EntityTypeName type = EntityTypeName.getByString(typeName);
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
		EntityTypeName type = EntityTypeName.getByString(typeName);
		int count = dis.readInt();

		Collection<Entity> entities = LocalRequetsImpl.getNewEntities(type, count);

		DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
		for (Entity e : entities) {
			dos.writeLong(e.getId());
		}
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
