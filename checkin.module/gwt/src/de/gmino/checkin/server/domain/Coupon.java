// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.domain;

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
import de.gmino.meva.shared.EntitySql;

// imports for field types
import de.gmino.checkin.server.domain.Shop;
import de.gmino.geobase.server.domain.Duration;
import de.gmino.geobase.server.domain.ImageUrl;


import de.gmino.checkin.server.domain.gen.CouponGen;
public class Coupon extends CouponGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Coupon(long id)
	{
		super(id);
	}
	
	public Coupon(
			long id,
			boolean ready,
			Shop shop,
			String title,
			String description,
			ImageUrl image,
			Duration duration)
	{
		super(
			id,
			ready,
			(de.gmino.checkin.server.domain.Shop)shop,
			title,
			description,
			(de.gmino.geobase.server.domain.ImageUrl)image,
			(de.gmino.geobase.server.domain.Duration)duration
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
