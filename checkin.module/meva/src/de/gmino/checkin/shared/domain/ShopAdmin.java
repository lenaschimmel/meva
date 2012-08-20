// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;
import de.gmino.meva.shared.RelationCollection;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for field types
import de.gmino.checkin.shared.domain.Shop;


import de.gmino.checkin.shared.domain.gen.ShopAdminGen;
public class ShopAdmin extends ShopAdminGen {
	// Constructors
	public ShopAdmin(long id)
	{
		super(id);
	}
	
	public ShopAdmin(
			long id,
			boolean ready,
			String username,
			String password)
	{
		super(
			id,
			ready,
			username,
			password
		);
	}
	

}
