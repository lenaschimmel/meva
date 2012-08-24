

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/checkin.module/meva/src/de/gmino/checkin/shared/domain/Consumer.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.checkin.shared.domain.gen.ConsumerGen;
public class Consumer extends ConsumerGen {
	// Constructors
	public Consumer(long id)
	{
		super(id);
	}
	
	public Consumer(
			long id,
			boolean ready,
			String facebookId)
	{
		super(
			id,
			ready,
			facebookId
		);
	}
	

}
