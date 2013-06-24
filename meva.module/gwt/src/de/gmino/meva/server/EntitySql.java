package de.gmino.meva.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import de.gmino.meva.shared.Entity;

public interface EntitySql {

	public void deserializeSql(Connection dbCon) throws SQLException;

	public void deserializeSql(Connection dbCon, Collection<Entity> toLoad) throws SQLException;

	public void deserializeSql(ResultSet rs, Connection dbCon) throws SQLException;
	
	public void serializeSql(Connection dbCon) throws SQLException;

}