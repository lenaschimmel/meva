package de.gmino.meva.shared;

import java.io.IOException;

public interface Value {

	// Binary
	// public void serializeBinary(DataOutputStream dos) throws IOException;

	// Json
	public void serializeJson(StringBuilder sb) throws IOException;

	public void serializeJson(StringBuilder sb, String indentation) throws IOException;

	// Stuff
	public String toString();
}