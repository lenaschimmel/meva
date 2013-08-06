// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

// gmino stuff
import de.gmino.geobase.server.domain.Timestamp;
import de.gmino.issuemap.server.domain.gen.CommentGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
// imports for field types
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
			(de.gmino.issuemap.server.domain.Issue)issue,
			text,
			user,
			(de.gmino.geobase.server.domain.Timestamp)timestamp,
			deleted
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
