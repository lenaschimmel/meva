// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.server.request;

// gmino stuff
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import org.itemscript.core.values.JsonObject;

import de.gmino.checkin.client.domain.Consumer;
import de.gmino.checkin.server.SqlHelper;
import de.gmino.checkin.server.request.gen.QueryConsumerByFidGen;
import de.gmino.meva.shared.Entity;

public class QueryConsumerByFid extends QueryConsumerByFidGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public QueryConsumerByFid(String prefix, ResultSet rs) throws SQLException {
		super(prefix, rs);
	}

	public QueryConsumerByFid(DataInputStream dis) throws IOException {
		super(dis);
	}

	public QueryConsumerByFid(JsonObject json) throws IOException {
		super(json);
	}

	public QueryConsumerByFid(String fid) {
		super(fid);
	}

	@Override
	public Collection<Long> evaluate() {
		try {
			Connection con = SqlHelper.getConnection();
			Statement stat = con.createStatement();

			ResultSet result = stat.executeQuery("SELECT id FROM Shop "
					+ "WHERE scanCode = '" + fid + "';");
			Collection<Long> ids = new LinkedList<Long>();
			if (result.next())
				ids.add(result.getLong(1));
			else {
				Collection<Consumer> newEntities = LocalRequetsImpl
						.getNewEntities(Consumer.type, 1);
				Consumer c = newEntities.iterator().next();
				c.setFacebookId(fid);
				LocalRequetsImpl.saveEntities(newEntities);
				ids.add(c.getId());
				System.out.println("Created new Consumer " + c.getId()
						+ " with fid " + fid);
			}
			return ids;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
