// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.domain;

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

// imports for field types
import de.gmino.checkin.server.domain.Shop;
import de.gmino.geobase.server.domain.DateSpan;


import de.gmino.checkin.server.domain.gen.CouponGen;
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
			(de.gmino.checkin.server.domain.Shop)shopWhichIssues,
			(de.gmino.checkin.server.domain.Shop)shopWhichAccepts,
			img,
			title,
			text,
			neededVisits,
			(de.gmino.geobase.server.domain.DateSpan)validty
		);
	}
	

}
