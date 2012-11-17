// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.server.domain;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.server.domain.gen.AddressGen;

public class Address extends AddressGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public Address(String prefix, ResultSet rs) throws SQLException {
		super(prefix, rs);
	}

	public Address(DataInputStream dis) throws IOException {
		super(dis);
	}

	public Address(JsonObject json) throws IOException {
		super(json);
	}

	public Address(String recipientName, String street, String houseNumber, String zip, String city, String additionalAddressLine) {
		super(recipientName, street, houseNumber, zip, city, additionalAddressLine);
	}

}
