// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.ios.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.ios.domain.gen.TimestampGen;
// default imports
// imports for JSON
public class Timestamp extends TimestampGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Timestamp()
	{
	}

	public Timestamp(JsonObject json) throws IOException
	{
		super(json);
	}
	public Timestamp(
			long millisSinceEpoch)
	{
		super(
			millisSinceEpoch
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
