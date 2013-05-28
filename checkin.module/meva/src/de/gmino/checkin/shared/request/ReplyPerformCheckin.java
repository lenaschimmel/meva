// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.request;

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

// imports for field types
import de.gmino.checkin.shared.domain.Coupon;
import de.gmino.checkin.shared.domain.CouponOwenership;
import de.gmino.checkin.shared.domain.Shop;


import de.gmino.checkin.shared.request.gen.ReplyPerformCheckinGen;
public class ReplyPerformCheckin extends ReplyPerformCheckinGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public ReplyPerformCheckin()
	{
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
			(de.gmino.checkin.shared.domain.Shop)shop,
			(de.gmino.checkin.shared.domain.Coupon)coupon,
			(de.gmino.checkin.shared.domain.CouponOwenership)ownership
		);
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
