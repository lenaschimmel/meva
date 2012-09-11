// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.AddressGen;
import de.gmino.meva.shared.Util;
public class Address extends AddressGen {
	static String[] givenNames = {"Frauke", "Johanna", "Gerda", "Tom", "Hans", "Klaus", "Chrisitan", "Maria", "Herbert", "Hazul", "Mannfred", "Heike", "Matilda", "Zara", "Friedrich"};
	static String[] familyNames = {"Schmidt", "Meier", "Müller", "Karst", "Pökel", "Hanse", "Tidelkamp", "Hennermann", "Tscheikowski", "Rabe", "Nurte", "Keilkopf", "Kandinski", "Mayer", "Zytlec"};
	static String[] titles = {"Dr.", "Prof.", "Prof. Dr.", "Dr. med.", "Freiherr ", "Fürst"};
	static String[] streetNames = {"Bahnhof","Markt","Kölner ", "Freiheits", "Haupt", "Buchen", "Kampe", "Friedrich", "Jahn", "Stettiner ", "Hans-Christian-Tumpe-", "Pony"};
	static String[] streetTypes= {"straße","straße","straße","platz","allee","weg"};
	static String[] additionalLines = {"Inkubator","Hinterhaus","Personalabteilung","z.Hd. Frau Gesterpümp","(i.d.Z.p.)", "3. Obergeschoss", "Linker Eingang"};
	static String[] cities = {"Braunschweig","Hannover","Lehrte","Berlin","Wanne-Eickel","Groß-Hinterprüpfingen","Wolfsburg","Wolfenbüttel","Hacke"};
	
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

	public void fillWithRandomData()
	{
		String title = ((Math.random() > 0.2) ? "" : Util.randomElementFrom(titles) + " ");
		String givenName = Util.randomElementFrom(givenNames) + ((Math.random() > 0.2) ? "" : "-" + Util.randomElementFrom(givenNames));
		String familyName = Util.randomElementFrom(familyNames) + ((Math.random() > 0.2) ? "" : "-" + Util.randomElementFrom(familyNames));
		recipientName = title + givenName + ((Math.random() > 0.2) ? " " : " von und zu ") + familyName;
		houseNumber = (int)(Math.random() * 200 + 1) + "" + ((Math.random() > 0.2) ? ' ' : (char)(Math.random() * 10 + 'a'));
		String streetName = Util.randomElementFrom(streetNames);
		if(streetName.endsWith(" "))
			street = streetName + Util.capitalizeFirst(Util.randomElementFrom(streetTypes));
		else
			street = streetName + Util.randomElementFrom(streetTypes);
		zip = "" + (int)(Math.random() * 89999 + 10000);
		additionalAddressLine = ((Math.random() > 0.2) ? "" : Util.randomElementFrom(additionalLines));
		city = Util.randomElementFrom(cities);
	}
}
