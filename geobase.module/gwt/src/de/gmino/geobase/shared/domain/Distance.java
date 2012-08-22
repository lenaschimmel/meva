

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/geobase.module/meva/src/de/gmino/geobase/shared/domain/Distance.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.geobase.shared.domain;

// gmino stuff
import java.io.IOException;

import org.itemscript.core.values.JsonObject;

import de.gmino.geobase.shared.domain.gen.DistanceGen;

public class Distance extends DistanceGen {
	private static final double METER_PER_MILE = 1609.344;
	private static final double METER_PER_FOOT = 0.3048;

	// Constructors
	public Distance(JsonObject json) throws IOException {
		super(json);
	}

	public Distance(double meters) {
		super(meters);
	}

	public double getInMeter() {
		return meters;
	}

	public double getInKilometer() {
		return meters / 1000.0;
	}

	public double getInMiles() {
		return meters / METER_PER_MILE;
	}

	public double getInFoot() {
		return meters / METER_PER_FOOT;
	}

	// TODO Factory methods for different units, but this would need a way to
	// instantiate the right class. Maybe a class "ValueFactory" per project?

	/**
	 * Formats the distance into a metric dimension. @see #toMetricString()
	 */
	@Override
	public String toString() {
		return toMetricString();
	}

	/**
	 * Formats the distance into a metric dimension. The unit is either m or km
	 * and the number may have 0, 1 or 2 decimals, depending on the magnitude.
	 * The smaller the value, the more higher the precision used.
	 */
	public String toMetricString() {
		if (meters < 20)
			return String.format("%.1fm", meters);
		else if (meters < 1000)
			return String.format("%dm", (int)meters);
		else if (meters < 5000)
			return String.format("%.2fkm", meters / 1000.0);
		else if (meters < 20000)
			return String.format("%.1fkm", meters / 1000.0);
		else
			return String.format("%dkm", (int)(meters / 1000.0));
	}

	/**
	 * Formats the distance into an imperial dimension. The unit is either foot
	 * or mile and the number may have 0, 1 or 2 decimals, depending on the
	 * magnitude. The smaller the value, the more higher the precision used.
	 */
	public String toImperialString() {
		final double foot = getInFoot();
		if (foot < 20)
			return String.format("%.1f′", foot);
		else if (foot < 1000)
			return String.format("%d′", (int)foot);
		else {
			final double miles = getInMiles();
			if (miles < 5)
				return String.format("%.2fmi", miles);
			else if (miles < 20)
				return String.format("%.1fmi", miles);
			else
				return String.format("%dmi", (int)miles);
		}
	}
}
