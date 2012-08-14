

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/gwt/src/de/gmino/meva/shared/Value.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.



// DONTEDIT This file has been copied from /home/lena/workspaceNeu/meva.module/meva/src/de/gmino/meva/shared/Value.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

package de.gmino.meva.shared;
 
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public interface Value {

	// Binary
	//public void serializeBinary(DataOutputStream dos) throws IOException;

	// Json
	public void serializeJson(StringBuilder sb) throws IOException;

	public void serializeJson(StringBuilder sb, String indentation)
			throws IOException;

	// Stuff
	public String toString();

}