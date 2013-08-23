// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.meva.shared.domain;

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
import de.gmino.meva.shared.domain.KeyValueDef;


import de.gmino.meva.shared.domain.gen.KeyValueSetGen;
@SuppressWarnings("unused")
public class KeyValueSet extends KeyValueSetGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public KeyValueSet(long id)
	{
		super(id);
	}
	
	public KeyValueSet(
			long id,
			boolean ready,
			String name)
	{
		super(
			id,
			ready,
			name
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
