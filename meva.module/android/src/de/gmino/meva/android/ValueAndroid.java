package de.gmino.meva.android;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ValueAndroid {

	public void serializeBinary(DataOutputStream dos) throws IOException;

}