// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

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


import de.gmino.geobase.shared.domain.gen.AddressGen;
public class Address extends AddressGen {
	// Constructors
	public Address(JsonObject json) throws IOException
	{
		this(
			json.get("recipientName").asString().stringValue(),
			json.get("street").asString().stringValue(),
			json.get("houseNumber").asString().stringValue(),
			json.get("zip").asString().stringValue(),
			json.get("city").asString().stringValue(),
			json.get("additionalAddressLine").asString().stringValue());
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
	

}
