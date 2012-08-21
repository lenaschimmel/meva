package de.gmino.checkin.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.entity.EntitySerializer;

import de.gmino.checkin.server.request.QueryNearbyShops;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Query;
import de.gmino.meva.shared.ReturnEntityPolicy;

public class BinaryServer extends HttpServlet {

	private static final long serialVersionUID = -3067797075737374489L;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			EntityFactory.setImplementations(new EntityFactoryImpl(),
					new LocalRequestFoo());
			EntityFactory.registerType("Shop");
			EntityFactory.registerType("Coupon");
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
			DataInputStream dis = new DataInputStream(req.getInputStream());
			String typeName = dis.readUTF();
			long id = dis.readLong();
			if(id == 0)
				return;
			
			Collection<Long> ids = new LinkedList<Long>();
			while(id != 0)
			{
				ids.add(id);
				id = dis.readLong();
			}
			
			System.out.println("Got a query for ids of type " + typeName + ": " + Arrays.toString(ids.toArray()));
			
			Collection<Entity> entities = EntityFactory.getEntitiesById(typeName, ids, ReturnEntityPolicy.BLOCK_ALWAYS);
			
			DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
			for (Entity e : entities)
			{
				((EntityBinary)e).serializeBinary(dos);
				System.out.println("Written " + e.toString());
			}
			
		} else {
			Query query = null;
			if (lastPart.equals("QueryNearbyShops"))
				query = new QueryNearbyShops(new DataInputStream(
						req.getInputStream()));
			if (query == null)
				throw new RuntimeException("Unrecognized query type: "
						+ lastPart);
			System.out.println("Got a query of type " + lastPart);
			System.out.println(query.toString());
			Collection<Long> ids = query.evaluate();
			DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
			for (long id : ids)
				dos.writeLong(id);
			dos.writeLong(0);
			dos.flush();
			dos.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Huhu!");
		resp.getWriter()
				.write("Yeah, du lustige Tante, ich bin hier! Du solltest aber POST-Request machen, nicht GET. Versuche doch mal <a href=\"http://gmino.de/legacy/php/post.php\">http://gmino.de/legacy/php/post.php</a>.");
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
