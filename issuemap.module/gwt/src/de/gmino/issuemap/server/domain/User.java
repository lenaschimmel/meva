// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.issuemap.server.domain;

// gmino stuff
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


// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TreeSet;


// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;
import de.gmino.meva.server.EntitySql;

import de.gmino.meva.server.domain.DateTime;
// imports for field types
import de.gmino.geobase.server.domain.Address;


import de.gmino.issuemap.server.domain.gen.UserGen;
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
			boolean activated)
	{
		super(
			id,
			ready,
			userName,
			password,
			(de.gmino.geobase.server.domain.Address)postal_address,
			email,
			activated
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
