

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/checkin.module/meva/src/de/gmino/checkin/shared/request/QueryPerformCheckin.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.request;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.ValueQuery;
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


import de.gmino.checkin.shared.request.gen.QueryPerformCheckinGen;
public class QueryPerformCheckin extends QueryPerformCheckinGen {
	// Constructors
	public QueryPerformCheckin(JsonObject json) throws IOException
	{
		super(json);
	}
	public QueryPerformCheckin(
			String scanCode)
	{
		super(
			scanCode
		);
	}
	
	@Override
	public Value valueFromJson(JsonObject json) throws IOException {
		// TODO Auto-generated method stub
		return new ReplyPerformCheckin(json);
	}
	

}
