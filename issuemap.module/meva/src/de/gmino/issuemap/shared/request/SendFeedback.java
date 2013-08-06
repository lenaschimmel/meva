// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.request;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.issuemap.shared.domain.Map;
import de.gmino.issuemap.shared.request.gen.SendFeedbackGen;
import de.gmino.meva.shared.Value;
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
			(de.gmino.issuemap.shared.domain.Map)map
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	@Override
	public Value valueFromJson(JsonObject json) throws IOException {
		return new de.gmino.meva.shared.domain.Void();
	}
}
