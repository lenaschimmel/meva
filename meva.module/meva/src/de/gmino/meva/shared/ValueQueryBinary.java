package de.gmino.meva.shared;

import java.io.DataInputStream;
import java.io.IOException;

public interface ValueQueryBinary extends ValueQuery {
	public Value valueFromBinary(DataInputStream dis) throws IOException;
}
