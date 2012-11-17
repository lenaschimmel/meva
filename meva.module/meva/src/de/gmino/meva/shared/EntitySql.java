package de.gmino.meva.shared;

import java.sql.Connection;
import java.sql.SQLException;

public interface EntitySql {

	public void deserializeSql(Connection dbCon) throws SQLException;

	public void serializeSql(Connection dbCon) throws SQLException;

}