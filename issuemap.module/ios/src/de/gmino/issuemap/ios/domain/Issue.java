// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.domain;

// gmino stuff
import de.gmino.geobase.ios.domain.ImageUrl;
import de.gmino.geobase.ios.domain.LatLon;
import de.gmino.geobase.ios.domain.Timestamp;
import de.gmino.issuemap.ios.domain.gen.IssueGen;
import de.gmino.meva.ios.domain.KeyValueSet;

// default imports
// imports for JSON
// imports for field types
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
			(de.gmino.geobase.ios.domain.LatLon)location,
			title,
			description,
			(de.gmino.issuemap.ios.domain.Markertype)markertype,
			(de.gmino.meva.ios.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.ios.domain.Map)map_instance,
			(de.gmino.geobase.ios.domain.Timestamp)creationTimestamp,
			rating,
			number_of_rating,
			resolved,
			deleted,
			(de.gmino.geobase.ios.domain.ImageUrl)primary_picture,
			(de.gmino.geobase.ios.domain.Timestamp)eventTimestamp,
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
