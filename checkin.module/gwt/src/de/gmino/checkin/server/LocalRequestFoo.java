package de.gmino.checkin.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityRequestInterface;
import de.gmino.meva.shared.EntitySql;

public class LocalRequestFoo implements EntityRequestInterface {

	@Override
	public void loadEntities(Collection<Entity> c) {
		Connection dbCon = SqlHelper.getConnection();
		for(Entity e : c)
		{
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
		// TODO Auto-generated method stub
		return null;
	}

}
