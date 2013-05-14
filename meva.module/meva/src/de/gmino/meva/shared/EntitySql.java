package de.gmino.meva.shared;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public interface EntitySql {

	public void deserializeSql(Connection dbCon) throws SQLException;

	public void deserializeSql(Connection dbCon, Collection<Entity> toLoad) throws SQLException;

	public void deserializeSql(ResultSet rs, Connection dbCon) throws SQLException;
	
	public void serializeSql(Connection dbCon) throws SQLException;

}