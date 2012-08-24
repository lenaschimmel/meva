package de.gmino.meva.shared;

import java.io.IOException;

public interface Entity<T> extends Comparable<T> {

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
	
	public void reassignRelation(String relname, Entity e);
 
	public String toShortString();
	
	public EntityTypeName getType();
}