// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

// gmino stuff
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.shared.domain.gen.PhotoGen;
// default imports
// imports for JSON
// imports for field types
public class Photo extends PhotoGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Photo(long id)
	{
		super(id);
	}
	
	public Photo(
			long id,
			boolean ready,
			Poi poi,
			ImageUrl image,
			String user,
			Timestamp timestamp,
			boolean deleted)
	{
		super(
			id,
			ready,
			(de.gmino.issuemap.shared.domain.Poi)poi,
			(de.gmino.geobase.shared.domain.ImageUrl)image,
			user,
			(de.gmino.geobase.shared.domain.Timestamp)timestamp,
			deleted
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
