// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

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
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;


import de.gmino.issuemap.shared.domain.gen.MapGen;
public class Map extends MapGen {
	// Constructors
	public Map(long id)
	{
		super(id);
	}
	
	public Map(
			long id,
			boolean ready,
			String title,
			String description,
			String subdomain,
			String city,
			LatLon initLocation,
			int initZoomlevel,
			String layer,
			String headerText,
			ImageUrl logo,
			String infoText)
	{
		super(
			id,
			ready,
			title,
			description,
			subdomain,
			city,
			(de.gmino.geobase.shared.domain.LatLon)initLocation,
			initZoomlevel,
			layer,
			headerText,
			(de.gmino.geobase.shared.domain.ImageUrl)logo,
			infoText
		);
		this.ready = true;
	}
	

}
