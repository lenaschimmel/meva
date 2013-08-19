// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

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
import de.gmino.meva.server.EntitySql;

// imports for field types
import de.gmino.geobase.server.domain.Address;
import de.gmino.geobase.server.domain.LatLon;
import de.gmino.issuemap.server.domain.Map;


import de.gmino.issuemap.server.domain.gen.ElectricalSubstationGen;
@SuppressWarnings("unused")
public class ElectricalSubstation extends ElectricalSubstationGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public ElectricalSubstation(long id)
	{
		super(id);
	}
	
	public ElectricalSubstation(
			long id,
			boolean ready,
			LatLon location,
			String title,
			Address address,
			String unitType,
			String operator,
			int frequence,
			float high_voltage,
			float low_voltage,
			float consumption,
			Map map_instance)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.server.domain.LatLon)location,
			title,
			(de.gmino.geobase.server.domain.Address)address,
			unitType,
			operator,
			frequence,
			high_voltage,
			low_voltage,
			consumption,
			(de.gmino.issuemap.server.domain.Map)map_instance
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
