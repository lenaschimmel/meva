// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.client.domain;

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
import de.gmino.checkin.client.domain.Shop;
import de.gmino.geobase.client.domain.DateSpan;


import de.gmino.checkin.client.domain.gen.CouponGen;
public class Coupon extends CouponGen {
	// Constructors
	public Coupon(long id)
	{
		super(id);
	}
	
	public Coupon(
			long id,
			boolean ready,
			Shop shopWhichIssues,
			Shop shopWhichAccepts,
			String img,
			String title,
			String text,
			short neededVisits,
			DateSpan validty)
	{
		super(
			id,
			ready,
			(de.gmino.checkin.client.domain.Shop)shopWhichIssues,
			(de.gmino.checkin.client.domain.Shop)shopWhichAccepts,
			img,
			title,
			text,
			neededVisits,
			(de.gmino.geobase.client.domain.DateSpan)validty
		);
	}
	

}
