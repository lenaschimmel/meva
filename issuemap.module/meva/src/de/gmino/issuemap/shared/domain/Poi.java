// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.shared.domain;

// gmino stuff
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.geobase.shared.domain.Timestamp;
import de.gmino.issuemap.shared.domain.gen.PoiGen;
import de.gmino.meva.shared.domain.KeyValueSet;
// default imports
// imports for JSON
// imports for field types
public class Poi extends PoiGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Poi(long id)
	{
		super(id);
	}
	
	public Poi(
			long id,
			boolean ready,
			LatLon location,
			String title,
			Markertype markertype,
			KeyValueSet keyvalueset,
			Map map_instance,
			Timestamp creationTimestamp,
			int rating,
			int number_of_rating,
			boolean marked,
			boolean deleted)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.shared.domain.LatLon)location,
			title,
			(de.gmino.issuemap.shared.domain.Markertype)markertype,
			(de.gmino.meva.shared.domain.KeyValueSet)keyvalueset,
			(de.gmino.issuemap.shared.domain.Map)map_instance,
			(de.gmino.geobase.shared.domain.Timestamp)creationTimestamp,
			rating,
			number_of_rating,
			marked,
			deleted
		);
		loadValueSet();
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
