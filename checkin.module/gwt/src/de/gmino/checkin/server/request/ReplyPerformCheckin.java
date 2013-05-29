// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.request;

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
import de.gmino.meva.shared.EntitySql;

// imports for field types
import de.gmino.checkin.server.domain.Coupon;
import de.gmino.checkin.server.domain.CouponOwenership;
import de.gmino.checkin.server.domain.Shop;


import de.gmino.checkin.server.request.gen.ReplyPerformCheckinGen;
public class ReplyPerformCheckin extends ReplyPerformCheckinGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public ReplyPerformCheckin()
	{
	}

	// Constructor for SQL deseralizaiton
	public ReplyPerformCheckin(String prefix, ResultSet rs) throws SQLException
	{
		super(prefix, rs);
	}
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
			(de.gmino.checkin.server.domain.Shop)shop,
			(de.gmino.checkin.server.domain.Coupon)coupon,
			(de.gmino.checkin.server.domain.CouponOwenership)ownership
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
