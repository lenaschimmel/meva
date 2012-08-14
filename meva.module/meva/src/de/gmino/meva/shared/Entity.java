package de.gmino.meva.shared;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public interface Entity {

	// Binary
	//public void serializeBinary(DataOutputStream dos) throws IOException;

	// Json
	public void serializeJson(StringBuilder sb) throws IOException;

	public void serializeJson(StringBuilder sb, String indentation)
			throws IOException;

	// public void serializeSql(Connection dbCon) throws SQLException;

	// Getters
	public long getId();

	// Setters
	public void setId(long id);

	// Stuff
	public String toString();

	public String getTypeName();

	public boolean isReady();
 
}