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
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.shared.domain.Issue;


import de.gmino.issuemap.shared.domain.gen.CommentGen;
public class Comment extends CommentGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Comment(long id)
	{
		super(id);
	}
	
	public Comment(
			long id,
			boolean ready,
			Issue issue,
			String text,
			String user,
			Timestamp timestamp,
			boolean deleted)
	{
		super(
			id,
			ready,
			(de.gmino.issuemap.shared.domain.Issue)issue,
			text,
			user,
			(de.gmino.geobase.shared.domain.Timestamp)timestamp,
			deleted
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
