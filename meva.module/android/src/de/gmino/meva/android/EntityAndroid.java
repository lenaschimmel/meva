package de.gmino.meva.android;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface EntityAndroid {

	public void deserializeBinary(DataInputStream dis) throws IOException;

	public void serializeBinary(DataOutputStream dos) throws IOException;

}