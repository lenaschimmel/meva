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
import de.gmino.issuemap.ios.domain.Map;
import de.gmino.issuemap.ios.domain.Markertype;


import de.gmino.issuemap.ios.domain.gen.MapHasMarkertypeGen;
public class MapHasMarkertype extends MapHasMarkertypeGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public MapHasMarkertype(long id)
	{
		super(id);
	}
	
	public MapHasMarkertype(
			long id,
			boolean ready,
			Markertype markertype,
			Map map)
	{
		super(
			id,
			ready,
			(de.gmino.issuemap.ios.domain.Markertype)markertype,
			(de.gmino.issuemap.ios.domain.Map)map
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
