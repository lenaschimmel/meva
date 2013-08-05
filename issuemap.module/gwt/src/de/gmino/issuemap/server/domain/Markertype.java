// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

// gmino stuff
import de.gmino.issuemap.server.domain.gen.MarkertypeGen;
// default imports
// imports for JSON
// imports for SQL stuff
// imports for serialization interfaces
public class Markertype extends MarkertypeGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Markertype(long id)
	{
		super(id);
	}
	
	public Markertype(
			long id,
			boolean ready,
			String markerName,
			String imageName,
			int imageWidth,
			int imageHeight)
	{
		super(
			id,
			ready,
			markerName,
			imageName,
			imageWidth,
			imageHeight
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
