// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.domain;

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

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// android
import de.gmino.meva.android.EntityAndroid;
import de.gmino.meva.android.ValueAndroid;

// imports for field types
import de.gmino.geobase.android.domain.Timestamp;


import de.gmino.checkin.android.domain.gen.CouponOwenershipGen;
public class CouponOwenership extends CouponOwenershipGen {
	// Constructors
	public CouponOwenership(long id)
	{
		super(id);
	}
	
	public CouponOwenership(
			long id,
			boolean ready,
			Timestamp acquired,
			Timestamp invalidated)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.android.domain.Timestamp)acquired,
			(de.gmino.geobase.android.domain.Timestamp)invalidated
		);
	}
	

}
