// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.EntityTypeName;
import de.gmino.meva.shared.Util;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;

// imports for field types
import de.gmino.checkin.shared.domain.Shop;


import de.gmino.checkin.shared.domain.gen.ShopAdminGen;
public class ShopAdmin extends ShopAdminGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
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
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
