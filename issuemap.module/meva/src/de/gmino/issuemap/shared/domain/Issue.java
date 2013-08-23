// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

// gmino stuff
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.shared.domain.gen.IssueGen;
import de.gmino.meva.shared.domain.KeyValueSet;
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
			(de.gmino.geobase.shared.domain.LatLon)location,
			title,
			description,
			(de.gmino.issuemap.shared.domain.Markertype)markertype,
			(de.gmino.meva.shared.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.shared.domain.Map)map_instance,
			(de.gmino.geobase.shared.domain.Timestamp)creationTimestamp,
			rating,
			number_of_rating,
			resolved,
			deleted,
			(de.gmino.geobase.shared.domain.ImageUrl)primary_picture,
			(de.gmino.geobase.shared.domain.Timestamp)eventTimestamp,
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
