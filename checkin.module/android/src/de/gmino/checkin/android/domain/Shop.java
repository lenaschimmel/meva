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
import de.gmino.checkin.android.domain.Coupon;
import de.gmino.checkin.android.domain.ShopAdmin;
import de.gmino.geobase.android.domain.Address;
import de.gmino.geobase.android.domain.ImageUrl;
import de.gmino.geobase.android.domain.LatLon;


import de.gmino.checkin.android.domain.gen.ShopGen;
public class Shop extends ShopGen {
	// BEGINNING OF CONSTRUCTOR BLOCK - DO NOT EDIT
	public Shop(long id)
	{
		super(id);
	}
	
	public Shop(
			long id,
			boolean ready,
			LatLon location,
			String facebookId,
			String scanCode,
			String title,
			String description,
			ImageUrl logo,
			Address shopAddress,
			Address billingAddress,
			ShopAdmin admin,
			Coupon currentCoupon)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.android.domain.LatLon)location,
			facebookId,
			scanCode,
			title,
			description,
			(de.gmino.geobase.android.domain.ImageUrl)logo,
			(de.gmino.geobase.android.domain.Address)shopAddress,
			(de.gmino.geobase.android.domain.Address)billingAddress,
			(de.gmino.checkin.android.domain.ShopAdmin)admin,
			(de.gmino.checkin.android.domain.Coupon)currentCoupon
		);
		this.ready = true;
	}
	
	// END OF CONSTRUCTOR BLOCK - DO NOT EDIT
}
