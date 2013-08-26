// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;

// gmino stuff
// imports for field types
import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.client.domain.LatLon;
import de.gmino.geobase.client.domain.Timestamp;
import de.gmino.issuemap.client.domain.gen.IssueGen;
import de.gmino.meva.client.domain.KeyValueSet;
// default imports
// imports for JSON
// imports for Key-Value-Set
@SuppressWarnings("unused")
public class Issue extends IssueGen {
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
			KeyValueSet keyvalueset,
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
			(de.gmino.meva.client.domain.KeyValueSet)keyvalueset,
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
		loadValueSet();
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
