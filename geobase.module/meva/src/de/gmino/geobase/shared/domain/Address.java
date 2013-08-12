// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.AddressGen;
// default imports
// imports for JSON
public class Address extends AddressGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Address()
	{
	}

	public Address(JsonObject json) throws IOException
	{
		super(json);
	}
	public Address(
			String recipientName,
			String street,
			String houseNumber,
			String zip,
			String city,
			String additionalAddressLine)
	{
		super(
			recipientName,
			street,
			houseNumber,
			zip,
			city,
			additionalAddressLine
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	public String toReadableString(boolean multiline)
	{
		String divider = multiline ? "\n" : ",";
		StringBuilder ret = new StringBuilder();
		addComponentIfNotEmpty(ret, recipientName, divider);
		addComponentIfNotEmpty(ret, street + " " + houseNumber, divider);
		addComponentIfNotEmpty(ret, additionalAddressLine, divider);
		addComponentIfNotEmpty(ret, zip + " " +  city, divider);
		return ret.toString();
	}

	private void addComponentIfNotEmpty(StringBuilder sb,
			String componemt, String divider) {
		if(componemt == null || componemt.length() == 0)
			return;
		if(sb.length() > 0)
			sb.append(divider);
		sb.append(componemt);
	}
}
