package de.gmino.checkin.server.request;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import de.gmino.checkin.server.SqlHelper;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.EntitySql;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Query;

/**
 * This server-specific class implements similar, but synchronous, methods as
 * the asyncrhonous all-platform-class Requests.
 * 
 * @author lena
 * 
 */
public class LocalRequetsImpl {

	/**
	 * Just call query.evaluate.
	 * @param query
	 * @return
	 */
	@Deprecated
	public static Collection<Long> getIdsByQuery(Query query) {
		return query.evaluate();
	}

	public static Collection<Long> getNewIds(EntityTypeName type, int count) {
		Collection<Long> ret = new ArrayList<Long>(count);
		Connection dbCon = SqlHelper.getConnection();
		try {
			Statement stat = dbCon.createStatement();
			ResultSet rs = stat.executeQuery("SELECT maxId FROM MaxId WHERE typeName = '"+type.toString()+"';");
			rs.next();
			long maxId = rs.getLong(1);
			for(int i = 0; i < count; i++)
				ret.add(++maxId);
			stat.executeUpdate("UPDATE MaxId SET maxId = '"+maxId+"' WHERE typeName = '"+type.toString()+"';");
			System.out.println("Created new Entites of type " + type.toString() + " with ids from " + (maxId - count + 1) + " to " + maxId);
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void loadEntities(Collection<? extends Entity> entities) {
		Connection dbCon = SqlHelper.getConnection();
		for (Entity e : entities) {
			System.out.println("Reading " + e.toShortString() + " from SQL.");
			try {
				((EntitySql) e).deserializeSql(dbCon);
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	public static void saveEntities(Collection<? extends Entity> entities) {
		Connection dbCon = SqlHelper.getConnection();
		for (Entity e : entities) {
			System.out.println("Saving " + e.toShortString() + " to SQL.");
			try {
				((EntitySql) e).serializeSql(dbCon);
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	public static <EntityClass extends Entity> Collection<EntityClass> getLoadedEntitiesById(
			EntityTypeName type, Collection<Long> ids) {

		Collection<EntityClass> entities = EntityFactory
				.getUnloadedEntitiesById(type, ids);
		loadEntities(entities);
		return entities;
	}

	public static <EntityClass extends Entity> Collection<EntityClass> getLoadedEntitiesByQuery(
			final EntityTypeName type, Query q) {

		Collection<Long> ids = getIdsByQuery(q);
		Collection<EntityClass> entities = EntityFactory
				.getUnloadedEntitiesById(type, ids);
		loadEntities(entities);
		return entities;
	}

	public static <EntityClass extends Entity> Collection<EntityClass> getNewEntities(
			final EntityTypeName type, final int count) {

		Collection<Long> ids = getNewIds(type, count);
		Collection<EntityClass> entitites = EntityFactory
				.getUnloadedEntitiesById(type, ids);

		return entitites;
	}

	public static <EntityClass extends Entity> Collection<EntityClass> getUnloadedEntitiesByQuery(
			final EntityTypeName type, Query q) {

		Collection<Long> ids = getIdsByQuery(q);

		Collection<EntityClass> entities = EntityFactory
				.getUnloadedEntitiesById(type, ids);
		return entities;
	}

}
