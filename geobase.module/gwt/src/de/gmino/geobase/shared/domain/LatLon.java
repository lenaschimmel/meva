

// DONTEDIT This file has been copied from C:\Users\greenmobile\meva\geobase.module\meva\src\de\gmino\geobase\shared\domain\LatLon.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;


import de.gmino.geobase.shared.domain.gen.LatLonGen;
public class LatLon extends LatLonGen {
	public LatLon(JsonObject json) throws IOException {
		super(json);
		// TODO Auto-generated constructor stub
	}

	// Constructors
	public LatLon(
			double latitude,
			double longitude)
	{
		super(
			latitude,
			longitude
		);
	}
	

}
