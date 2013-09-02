// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.domain;

// gmino stuff
import de.gmino.geobase.ios.domain.ImageUrl;
import de.gmino.geobase.ios.domain.Timestamp;
import de.gmino.issuemap.ios.domain.gen.PhotoGen;
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
			(de.gmino.issuemap.ios.domain.Poi)poi,
			(de.gmino.geobase.ios.domain.ImageUrl)image,
			user,
			(de.gmino.geobase.ios.domain.Timestamp)timestamp,
			deleted
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
