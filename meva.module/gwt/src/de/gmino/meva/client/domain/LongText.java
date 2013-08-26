// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.meva.client.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.TreeMap;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;


import de.gmino.meva.client.domain.gen.LongTextGen;
@SuppressWarnings("unused")
public class LongText extends LongTextGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public LongText()
	{
	}

	public LongText(JsonObject json) throws IOException
	{
		super(json);
	}
	public LongText(
			String text)
	{
		super(
			text
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
