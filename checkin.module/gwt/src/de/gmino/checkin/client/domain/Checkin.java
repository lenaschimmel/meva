// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.client.domain;

// gmino stuff
import de.gmino.checkin.client.domain.gen.CheckinGen;
import de.gmino.geobase.client.domain.Timestamp;
public class Checkin extends CheckinGen {
	// Constructors
	public Checkin(long id)
	{
		super(id);
	}
	
	public Checkin(
			long id,
			boolean ready,
			Timestamp timestamp,
			Consumer consumer,
			Shop shop)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.client.domain.Timestamp)timestamp,
			(de.gmino.checkin.client.domain.Consumer)consumer,
			(de.gmino.checkin.client.domain.Shop)shop
		);
	}
	

}
