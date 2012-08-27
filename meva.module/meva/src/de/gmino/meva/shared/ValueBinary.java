package de.gmino.meva.shared;

import java.io.DataOutputStream;
import java.io.IOException;

public interface ValueBinary {

	public void serializeBinary(DataOutputStream dos) throws IOException;

}