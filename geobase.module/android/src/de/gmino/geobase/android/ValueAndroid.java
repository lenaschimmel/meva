package de.gmino.geobase.android;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ValueAndroid {

	public void deserializeBinary(DataInputStream dis) throws IOException;

	public void serializeBinary(DataOutputStream dos) throws IOException;

}