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
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.shared.domain.Map;


import de.gmino.issuemap.shared.domain.gen.IssueGen;
public class Issue extends IssueGen {
	// Constructors
	public Issue(long id)
	{
		super(id);
	}
	
	public Issue(
			long id,
			boolean ready,
			LatLon location,
			String title,
			String description,
			String issueType,
			Map map_instance,
			Timestamp creationTimestamp,
			boolean resolved,
			boolean deleted,
			ImageUrl primary_picture)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.shared.domain.LatLon)location,
			title,
			description,
			issueType,
			(de.gmino.issuemap.shared.domain.Map)map_instance,
			(de.gmino.geobase.shared.domain.Timestamp)creationTimestamp,
			resolved,
			deleted,
			(de.gmino.geobase.shared.domain.ImageUrl)primary_picture
		);
		this.ready = true;
	}
	

}
