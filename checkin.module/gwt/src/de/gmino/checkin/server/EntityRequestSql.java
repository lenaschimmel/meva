package de.gmino.checkin.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityRequestInterface;
import de.gmino.meva.shared.EntitySql;

public class EntityRequestSql implements EntityRequestInterface {

	@Override
	public void loadEntities(Collection<? extends Entity> c) {
		Connection dbCon = SqlHelper.getConnection();
		for(Entity e : c)
		{
			System.out.println("Reading " + e.toShortString() + " from SQL.");
			try {
				((EntitySql)e).deserializeSql(dbCon);
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	@Override
	public void loadEntity(Entity e) {
		LinkedList<Entity> entities = new LinkedList<Entity>();
		entities.add(e);
		loadEntities(entities);
	}

	@Override
	public Collection<Long> getNewEntities(String typeName, int count) {
		Collection<Long> ret = new ArrayList<Long>(count);
		Connection dbCon = SqlHelper.getConnection();
		try {
			Statement stat = dbCon.createStatement();
			ResultSet rs = stat.executeQuery("SELECT maxId FROM MaxId WHERE typeName = '"+typeName+"';");
			rs.next();
			long maxId = rs.getLong(1);
			for(int i = 0; i < count; i++)
				ret.add(++maxId);
			stat.executeUpdate("UPDATE MaxId SET maxId = '"+maxId+"' WHERE typeName = '"+typeName+"';");
			System.out.println("Created new Entites of type " + typeName + " with ids from " + (maxId - count + 1) + " to " + maxId);
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void saveEntity(Entity e) {
		System.out.println("Saving " + e.toShortString() + " to SQL.");
		Connection dbCon = SqlHelper.getConnection();
		try {
			((EntitySql)e).serializeSql(dbCon);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
	}

	@Override
	public void saveEntities(Collection<? extends Entity> c) {
		for(Entity e : c)
			saveEntity(e);
	}

}
