// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.domain;

// gmino stuff
import de.gmino.checkin.server.domain.gen.CheckinGen;
import de.gmino.geobase.server.domain.Timestamp;
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
			(de.gmino.geobase.server.domain.Timestamp)timestamp,
			(de.gmino.checkin.server.domain.Consumer)consumer,
			(de.gmino.checkin.server.domain.Shop)shop
		);
	}
	

}
