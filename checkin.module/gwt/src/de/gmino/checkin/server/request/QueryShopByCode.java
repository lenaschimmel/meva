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

import de.gmino.checkin.server.SqlHelper;
import de.gmino.checkin.server.request.gen.QueryShopByCodeGen;

public class QueryShopByCode extends QueryShopByCodeGen {
	// Constructors
	// Constructor for SQL deseralizaiton
	public QueryShopByCode(String prefix, ResultSet rs) throws SQLException {
		super(prefix, rs);
	}

	public QueryShopByCode(DataInputStream dis) throws IOException {
		super(dis);
	}

	public QueryShopByCode(JsonObject json) throws IOException {
		super(json);
	}

	public QueryShopByCode(String scanCode) {
		super(scanCode);
	}

	@Override
	public Collection<Long> evaluate() {
		try {
			Connection con = SqlHelper.getConnection();
			Statement stat = con.createStatement();

			ResultSet result = stat.executeQuery("SELECT id FROM Shop "
					+ "WHERE scanCode = '" + scanCode + "';");
			Collection<Long> ids = new LinkedList<Long>();
			while (result.next())
				ids.add(result.getLong(1));
			return ids;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
