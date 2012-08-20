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
import de.gmino.checkin.server.domain.Coupon;
import de.gmino.checkin.server.domain.ShopAdmin;
import de.gmino.geobase.server.domain.Address;
import de.gmino.geobase.server.domain.ImageUrl;
import de.gmino.geobase.server.domain.LatLon;


import de.gmino.checkin.server.domain.gen.ShopGen;
public class Shop extends ShopGen {
	// Constructors
	public Shop(long id)
	{
		super(id);
	}
	
	public Shop(
			long id,
			boolean ready,
			LatLon location,
			String facebookId,
			String title,
			String description,
			ImageUrl logo,
			Address shopAddress,
			Address billingAddress,
			ShopAdmin admin)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.server.domain.LatLon)location,
			facebookId,
			title,
			description,
			(de.gmino.geobase.server.domain.ImageUrl)logo,
			(de.gmino.geobase.server.domain.Address)shopAddress,
			(de.gmino.geobase.server.domain.Address)billingAddress,
			(de.gmino.checkin.server.domain.ShopAdmin)admin
		);
	}
	

}
