// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.client.domain;

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
import de.gmino.checkin.client.domain.Consumer;
import de.gmino.checkin.client.domain.Coupon;
import de.gmino.geobase.client.domain.Timestamp;


import de.gmino.checkin.client.domain.gen.CouponOwenershipGen;
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
			(de.gmino.geobase.client.domain.Timestamp)acquired,
			(de.gmino.geobase.client.domain.Timestamp)invalidated,
			(de.gmino.checkin.client.domain.Consumer)consumer,
			(de.gmino.checkin.client.domain.Coupon)coupon
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
