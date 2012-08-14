// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.meva.shared.Entity;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.ReturnEntityPolicy;

// default imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// imports for SQL stuff
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

// imports for field types
import de.gmino.checkin.shared.domain.Shop;
import de.gmino.geobase.shared.domain.LatLon;


import de.gmino.checkin.shared.domain.gen.ShopGen;
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
			String img,
			String title,
			String text,
			String owner,
			Shop neighbour,
			String facebookId)
	{
		super(
			id,
			ready,
			(de.gmino.geobase.shared.domain.LatLon)location,
			img,
			title,
			text,
			owner,
			(de.gmino.checkin.shared.domain.Shop)neighbour,
			facebookId
		);
	}
	

}
