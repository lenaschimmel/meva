// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import de.gmino.meva.shared.Value;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedList;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;


import de.gmino.geobase.shared.domain.gen.DurationGen;
public class Duration extends DurationGen {
	public static long SECOND = 1000;
	public static long MINUTE = SECOND * 60;
	public static long HOUR = MINUTE * 60;
	public static long DAY = HOUR * 24;
	public static long WEEK = DAY * 7;
	public static long MONTH = DAY * 30;
	public static long YEAR = DAY * 365;
	
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Duration()
	{
	}

	public Duration(JsonObject json) throws IOException
	{
		super(json);
	}
	public Duration(
			long milliseconds)
	{
		super(
			milliseconds
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	
	public String toRelativeString() {
		long now = System.currentTimeMillis();
		long diff = milliseconds - now;
		if (diff == 0)
			return "jetzt";
		String ret = (diff > 0) ? "in " : "vor ";
		LinkedList<String> parts = new LinkedList<String>();

		if (diff < 0)
			diff *= -1;

		long years = diff / YEAR;
		diff -= years * YEAR;
		if (years > 0)
			parts.add(years + ((years == 1) ? " Jahr" : " Jahren"));

		long months = diff / MONTH;
		diff -= months * MONTH;
		if (months > 0)
			parts.add(months + ((months == 1) ? " Monat" : " Monaten"));

		if (parts.size() > 1)
			return ret + concatParts(parts);

		long weeks = diff / WEEK;
		diff -= weeks * WEEK;
		if (weeks > 0)
			parts.add(weeks + ((weeks == 1) ? " Woche" : " Wochen"));

		if (parts.size() > 1)
			return ret + concatParts(parts);

		long days = diff / DAY;
		diff -= days * DAY;
		if (days > 0)
			parts.add(days + ((days == 1) ? " Tag" : " Tagen"));

		if (parts.size() > 1)
			return ret + concatParts(parts);

		long hours = diff / HOUR;
		diff -= hours * HOUR;
		if (hours > 0)
			parts.add(hours + ((hours == 1) ? " Stunde" : " Stunden"));

		if (parts.size() > 1)
			return ret + concatParts(parts);

		long minutes = diff / MINUTE;
		diff -= minutes * MINUTE;
		if (minutes > 0)
			parts.add(minutes + ((minutes == 1) ? " Minute" : " Minuten"));

		if (parts.size() > 1)
			return ret + concatParts(parts);

		long seconds = diff / SECOND;
		diff -= seconds * SECOND;
		if (seconds > 0)
			parts.add(seconds + ((seconds == 1) ? " Sekunde" : " Sekunden"));

		return ret + concatParts(parts);
	}
	
	private String concatParts(LinkedList<String> parts) {
		if (parts.size() == 1)
			return parts.getFirst();
		else
			return parts.getFirst() + " und " + parts.getLast();

	}
}
