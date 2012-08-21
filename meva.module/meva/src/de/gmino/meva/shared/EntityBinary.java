package de.gmino.meva.shared;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface EntityBinary {

	public void deserializeBinary(DataInputStream dis) throws IOException;

	public void serializeBinary(DataOutputStream dos) throws IOException;

}