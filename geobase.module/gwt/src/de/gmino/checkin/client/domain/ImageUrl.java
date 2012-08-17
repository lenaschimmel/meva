// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.client.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;
import de.gmino.meva.shared.RelationCollection;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;


import de.gmino.checkin.client.domain.gen.ImageUrlGen;
public class ImageUrl extends ImageUrlGen {
	// Constructors
	public ImageUrl(JsonObject json) throws IOException
	{
		this(
			json.get("url").asString().stringValue());
	}

	public ImageUrl(
			String url)
	{
		super(
			url
		);
	}
	

}
