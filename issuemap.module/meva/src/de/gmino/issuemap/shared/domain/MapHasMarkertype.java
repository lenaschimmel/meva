// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

// gmino stuff
import de.gmino.issuemap.shared.domain.gen.MapHasMarkertypeGen;
// default imports
// imports for JSON
// imports for field types
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
			(de.gmino.issuemap.shared.domain.Markertype)markertype,
			(de.gmino.issuemap.shared.domain.Map)map
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
