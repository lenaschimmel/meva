// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.meva.ios.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
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

// imports for field types
import de.gmino.meva.ios.domain.KeyValueSet;


import de.gmino.meva.ios.domain.gen.KeyValueDefGen;
@SuppressWarnings("unused")
public class KeyValueDef extends KeyValueDefGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public KeyValueDef(long id)
	{
		super(id);
	}
	
	public KeyValueDef(
			long id,
			boolean ready,
			String name,
			String valueType,
			String description,
			KeyValueSet set)
	{
		super(
			id,
			ready,
			name,
			valueType,
			description,
			(de.gmino.meva.ios.domain.KeyValueSet)set
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
