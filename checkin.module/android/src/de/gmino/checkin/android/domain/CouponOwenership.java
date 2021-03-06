// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.domain;

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

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// imports for serialization interfaces
import de.gmino.meva.shared.EntityBinary;
import de.gmino.meva.shared.ValueBinary;

// imports for field types
import de.gmino.checkin.android.domain.Consumer;
import de.gmino.checkin.android.domain.Coupon;
import de.gmino.geobase.android.domain.Timestamp;


import de.gmino.checkin.android.domain.gen.CouponOwenershipGen;
public class CouponOwenership extends CouponOwenershipGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public CouponOwenership(long id)
	{
		super(id);
	}
	
	public CouponOwenership(
			long id,
			boolean ready,
			Timestamp acquired,
			Timestamp invalidated,
			Consumer consumer,
			Coupon coupon)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.android.domain.Timestamp)acquired,
			(de.gmino.geobase.android.domain.Timestamp)invalidated,
			(de.gmino.checkin.android.domain.Consumer)consumer,
			(de.gmino.checkin.android.domain.Coupon)coupon
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
