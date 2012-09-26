// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.android.request;

// gmino stuff
import de.gmino.meva.shared.Value;
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
import de.gmino.checkin.android.domain.Coupon;
import de.gmino.checkin.android.domain.CouponOwenership;
import de.gmino.checkin.android.domain.Shop;


import de.gmino.checkin.android.request.gen.ReplyPerformCheckinGen;
public class ReplyPerformCheckin extends ReplyPerformCheckinGen {
	// Constructors
	public ReplyPerformCheckin(DataInputStream dis) throws IOException
	{
		super(dis);
	}
	public ReplyPerformCheckin(JsonObject json) throws IOException
	{
		super(json);
	}
	public ReplyPerformCheckin(
			boolean success,
			String message,
			Shop shop,
			Coupon coupon,
			CouponOwenership ownership)
	{
		super(
			success,
			message,
			(de.gmino.checkin.android.domain.Shop)shop,
			(de.gmino.checkin.android.domain.Coupon)coupon,
			(de.gmino.checkin.android.domain.CouponOwenership)ownership
		);
	}
	

}
