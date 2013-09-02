// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.android.domain;

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

// imports for field types
import de.gmino.geobase.android.domain.Address;
import de.gmino.geobase.android.domain.LatLon;
import de.gmino.issuemap.android.domain.Map;


import de.gmino.issuemap.android.domain.gen.DecentralizedGenerationGen;
@SuppressWarnings("unused")
public class DecentralizedGeneration extends DecentralizedGenerationGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public DecentralizedGeneration(long id)
	{
		super(id);
	}
	
	public DecentralizedGeneration(
			long id,
			boolean ready,
			LatLon location,
			Address address,
			String unitType,
			float power,
			String voltage,
			Map map_instance)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.android.domain.LatLon)location,
			(de.gmino.geobase.android.domain.Address)address,
			unitType,
			power,
			voltage,
			(de.gmino.issuemap.android.domain.Map)map_instance
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
