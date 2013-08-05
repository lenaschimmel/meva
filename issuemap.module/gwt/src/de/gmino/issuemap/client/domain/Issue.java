// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;


import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.domain.Timestamp;
import de.gmino.geobase.shared.domain.Poi;
import de.gmino.issuemap.client.domain.gen.IssueGen;
// default imports
// imports for JSON
// imports for field types
public class Issue extends IssueGen implements Poi {
	
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
	
	public String getDescriptionWithHtmlLineBreaks()
	{
		return description.replace("\n", "<br/>");
	}
	
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Issue(long id)
	{
		super(id);
	}
	
	public Issue(
			long id,
			boolean ready,
			LatLon location,
			String title,
			String description,
			Markertype markertype,
			Map map_instance,
			Timestamp creationTimestamp,
			int rating,
			int number_of_rating,
			boolean resolved,
			boolean deleted,
			ImageUrl primary_picture,
			Timestamp eventTimestamp,
			double price,
			String organizer,
			String email,
			String phone)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.client.domain.LatLon)location,
			title,
			description,
			(de.gmino.issuemap.client.domain.Markertype)markertype,
			(de.gmino.issuemap.client.domain.Map)map_instance,
			(de.gmino.geobase.client.domain.Timestamp)creationTimestamp,
			rating,
			number_of_rating,
			resolved,
			deleted,
			(de.gmino.geobase.client.domain.ImageUrl)primary_picture,
			(de.gmino.geobase.client.domain.Timestamp)eventTimestamp,
			price,
			organizer,
			email,
			phone
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
