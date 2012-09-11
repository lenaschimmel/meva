// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.ImageUrlGen;
public class ImageUrl extends ImageUrlGen {
	// Constructors
	public ImageUrl(JsonObject json) throws IOException
	{
		super(json);
	}
	public ImageUrl(
			String url)
	{
		super(
			url
		);
	}
	
	public void fillWithRandomData()
	{
		url = "http://gmino.de/static/img/logotest/" + (int)(Math.random() * 467 + 474) + ".jpg";
	}
}
