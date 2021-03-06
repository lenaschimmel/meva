// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;

// gmino stuff
import com.google.gwt.user.client.Random;

// imports for field types
import de.gmino.geobase.client.domain.Address;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.issuemap.client.domain.gen.ElectricalSubstationGen;
// default imports
// imports for JSON
@SuppressWarnings("unused")
public class ElectricalSubstation extends ElectricalSubstationGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public ElectricalSubstation(long id)
	{
		super(id);
	}
	
	public ElectricalSubstation(
			long id,
			boolean ready,
			LatLon location,
			String title,
			Address address,
			String unitType,
			String operator,
			int frequence,
			float high_voltage,
			float low_voltage,
			float consumption,
			Map map_instance)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.client.domain.LatLon)location,
			title,
			(de.gmino.geobase.client.domain.Address)address,
			unitType,
			operator,
			frequence,
			high_voltage,
			low_voltage,
			consumption,
			(de.gmino.issuemap.client.domain.Map)map_instance
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	public void preventNulls() {
		title = "";
		operator = "";
		unitType = "";
	}
	
	public String color;
	public int numberOfSunUnits;
	public int numberOfWindUnits;
	public float sunPowerSum;
	public float windPowerSum;
	
	public void setRandomColor()
	{

	    String hex1 = getRandomMiddleHex();
	    String hex2 = getRandomMiddleHex();
	    String hex3 = getRandomMiddleHex();
	    String hex4 = getRandomMiddleHex();
	    String hex5 = getRandomMiddleHex();
	    String hex6 = getRandomMiddleHex();

	    color = "#" + hex1 + hex2 + hex3 + hex4 + hex5 + hex6;


	}
	
	private static String getRandomMiddleHex() {
		String[] hex = new String[] { "3", "4", "5", "6", "7", "8", "9", "A", "B", "C" };
		int randomNum = Random.nextInt(hex.length);
		String sHex = hex[randomNum];
		return sHex;
	}

}
