// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.client.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.client.domain.gen.AddressGen;
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
}
