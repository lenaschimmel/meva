// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.domain;

// gmino stuff
import de.gmino.checkin.android.domain.gen.CheckinGen;
import de.gmino.geobase.android.domain.Timestamp;
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
			(de.gmino.geobase.android.domain.Timestamp)timestamp,
			(de.gmino.checkin.android.domain.Consumer)consumer,
			(de.gmino.checkin.android.domain.Shop)shop
		);
	}
	

}
