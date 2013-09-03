// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.client.domain;

// gmino stuff
import de.gmino.meva.client.domain.DateTime;
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.RelationCollection;
import de.gmino.meva.shared.TypeName;
import de.gmino.meva.shared.Util;


// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.TreeMap;


// imports for JSON
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;


// imports for Key-Value-Set
import de.gmino.meva.shared.ValueWrapper;
import de.gmino.meva.shared.domain.KeyValueDef;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

import org.itemscript.core.JsonSystem;
import org.itemscript.core.values.JsonObject;
import org.itemscript.core.values.JsonValue;
import org.itemscript.standard.StandardConfig;


// imports for field types
import de.gmino.geobase.client.domain.Address;


import de.gmino.issuemap.client.domain.gen.UserGen;
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
			String email,
			boolean activated,
			String sessionId,
			DateTime expirationTime)
	{
		super(
			id,
			ready,
			userName,
			password,
			(de.gmino.geobase.client.domain.Address)postal_address,
			email,
			activated,
			sessionId,
			(de.gmino.meva.client.domain.DateTime)expirationTime
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
