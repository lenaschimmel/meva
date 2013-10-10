// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.domain;

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

// imports for field types
import de.gmino.geobase.ios.domain.LatLon;
import de.gmino.geobase.ios.domain.Timestamp;
import de.gmino.issuemap.ios.domain.Comment;
import de.gmino.issuemap.ios.domain.Map;
import de.gmino.issuemap.ios.domain.Markertype;
import de.gmino.issuemap.ios.domain.Photo;


import de.gmino.issuemap.ios.domain.gen.PoiGen;
@SuppressWarnings("unused")
public class Poi extends PoiGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Poi(long id)
	{
		super(id);
	}
	
	public Poi(
			long id,
			boolean ready,
			LatLon location,
			String title,
			Markertype markertype,
			KeyValueSet keyvalueset,
			Map map_instance,
			Timestamp creationTimestamp,
			Timestamp resolvedTimestamp,
			int rating,
			int number_of_rating,
			boolean marked,
			boolean deleted)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.ios.domain.LatLon)location,
			title,
			(de.gmino.issuemap.ios.domain.Markertype)markertype,
			(de.gmino.meva.ios.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.ios.domain.Map)map_instance,
			(de.gmino.geobase.ios.domain.Timestamp)creationTimestamp,
			(de.gmino.geobase.ios.domain.Timestamp)resolvedTimestamp,
			rating,
			number_of_rating,
			marked,
			deleted
		);
		loadValueSet();
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
