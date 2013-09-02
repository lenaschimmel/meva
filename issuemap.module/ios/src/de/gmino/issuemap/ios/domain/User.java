// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.ios.domain;

// gmino stuff
import de.gmino.geobase.ios.domain.Address;
import de.gmino.issuemap.ios.domain.gen.UserGen;
// default imports
// imports for JSON
// imports for field types
@SuppressWarnings("unused")
public class User extends UserGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public User(long id)
	{
		super(id);
	}
	
	public User(
			long id,
			boolean ready,
			String userName,
			String password,
			Address postal_address,
			String email)
	{
		super(
			id,
			ready,
			userName,
			password,
			(de.gmino.geobase.ios.domain.Address)postal_address,
			email
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
