package de.gmino.checkin.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

public class ServletGetEntities extends HttpServlet {

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String typeName = req.getParameter("typename");
		String idsString = req.getParameter("ids");
		String[] idStrings = idsString.split(",");

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		Collection<Long> ids = new LinkedList<Long>();
		
		for (String idString : idStrings) 
			ids.add(Long.parseLong(idString));
		
		System.out.println("Got request for entities of type " + typeName + " with ids: " + idsString);
		
		Collection<Entity> entities = EntityFactory.getEntitiesById(typeName, ids, ReturnEntityPolicy.BLOCK_IF_NEEDED);
			
		boolean first = true;
		for(Entity ret : entities)
		{
			sb.append(first ? "\n" : ",\n");
			if (ret != null)
			{
				ret.serializeJson(sb);
				System.out.println("Output entity: " + ret.toString());
			}
			else
				sb.append("null");
			first = false;
		}
		sb.append("]\n");

		PrintWriter out = resp.getWriter();
		out.write(sb.toString());
		out.flush();
	}
}
