// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.request;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.issuemap.ios.domain.Map;
import de.gmino.issuemap.ios.request.gen.SendFeedbackGen;
// default imports
// imports for JSON
public class SendFeedback extends SendFeedbackGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public SendFeedback()
	{
	}

	public SendFeedback(JsonObject json) throws IOException
	{
		super(json);
	}
	public SendFeedback(
			boolean toDevelopers,
			String message,
			String emailAddress,
			Map map)
	{
		super(
			toDevelopers,
			message,
			emailAddress,
			(de.gmino.issuemap.ios.domain.Map)map
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
