// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.meva.android.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TreeSet;

// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;


import de.gmino.meva.android.domain.gen.VoidGen;
public class Void extends VoidGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Void()
	{
	}

	public Void(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public Void(JsonObject json) throws IOException
	{
		super(json);
	}
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
