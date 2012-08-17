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
import de.gmino.checkin.client.domain.Coupon;
import de.gmino.checkin.client.domain.ShopAdmin;
import de.gmino.geobase.client.domain.Address;
import de.gmino.geobase.client.domain.ImageUrl;
import de.gmino.geobase.client.domain.LatLon;


import de.gmino.checkin.client.domain.gen.ShopGen;
public class Shop extends ShopGen {
	// Constructors
	public Shop(long id)
	{
		super(id);
		this.coupons = new RelationCollection();
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
			(de.gmino.geobase.client.domain.LatLon)location,
			facebookId,
			title,
			description,
			(de.gmino.geobase.client.domain.ImageUrl)logo,
			(de.gmino.geobase.client.domain.Address)shopAddress,
			(de.gmino.geobase.client.domain.Address)billingAddress,
			(de.gmino.checkin.client.domain.ShopAdmin)admin
		);
	}
	

}
