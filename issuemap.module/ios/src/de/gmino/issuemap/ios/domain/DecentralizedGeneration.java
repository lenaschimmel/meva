// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
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

// imports for field types
import de.gmino.geobase.ios.domain.Address;
import de.gmino.geobase.ios.domain.LatLon;


import de.gmino.issuemap.ios.domain.gen.DecentralizederationGen;
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
			String voltage)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.ios.domain.LatLon)location,
			(de.gmino.geobase.ios.domain.Address)address,
			unitType,
			power,
			voltage
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
