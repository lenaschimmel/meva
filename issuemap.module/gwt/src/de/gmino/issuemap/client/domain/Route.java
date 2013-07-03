// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
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

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

import de.gmino.geobase.client.domain.Distance;
import de.gmino.geobase.client.domain.Duration;
// imports for field types
import de.gmino.geobase.client.domain.LatLon;


import de.gmino.issuemap.client.domain.gen.RouteGen;
public class Route extends RouteGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Route(long id)
	{
		super(id);
	}
	
	public Route(
			long id,
			boolean ready,
			LatLon location,
			String title,
			String description,
			String gpxUrl,
			String color,
			String characteristics,
			Distance length,
			Duration rideTime,
			int rating,
			int number_of_rating)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.client.domain.LatLon)location,
			title,
			description,
			gpxUrl,
			color,
			characteristics,
			(de.gmino.geobase.client.domain.Distance)length,
			(de.gmino.geobase.client.domain.Duration)rideTime,
			rating,
			number_of_rating
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
	

	public int vote;
	
	public void changeRating(int newVote)
	{
		rating += newVote - vote;
		if(vote == 0 && newVote != 0)
			number_of_rating++;
		if(vote != 0 && newVote == 0)
			number_of_rating--;
		vote = newVote;
	}
}
