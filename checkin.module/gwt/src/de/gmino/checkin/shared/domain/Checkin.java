

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/checkin.module/meva/src/de/gmino/checkin/shared/domain/Checkin.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.checkin.shared.domain.gen.CheckinGen;
import de.gmino.geobase.shared.domain.Timestamp;
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
			(de.gmino.geobase.shared.domain.Timestamp)timestamp,
			(de.gmino.checkin.shared.domain.Consumer)consumer,
			(de.gmino.checkin.shared.domain.Shop)shop
		);
	}
	

}
