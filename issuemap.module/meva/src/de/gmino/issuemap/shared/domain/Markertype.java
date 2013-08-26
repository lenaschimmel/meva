// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

// gmino stuff
import de.gmino.issuemap.shared.domain.gen.MarkertypeGen;
import de.gmino.meva.shared.domain.KeyValueSet;
// default imports
// imports for JSON
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
			int imageHeight,
			KeyValueSet markerClass)
	{
		super(
			id,
			ready,
			markerName,
			imageName,
			imageWidth,
			imageHeight,
			(de.gmino.meva.shared.domain.KeyValueSet)markerClass
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
