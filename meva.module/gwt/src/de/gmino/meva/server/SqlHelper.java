package de.gmino.meva.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.google.appengine.api.rdbms.AppEngineDriver;
import com.mysql.jdbc.Driver;

import de.gmino.meva.shared.Log;

public class SqlHelper {
	private static String connectionUrl;

	static
	{
		try {
			DriverManager.registerDriver(new Driver());
		} catch (SQLException e) {
			Log.exception("Error registering DB driver.",e);
		}
	}

	public static Connection getConnection() {
			Connection con = null;
			try {
				con = DriverManager.getConnection(connectionUrl);
			    con.createStatement().execute("set names 'utf8';");
			} catch (Exception e) {
				Log.exception("Error creating DB connection.",e);
			}
		return con;
	}

	public static void setConnectionUrl(String connectionUrl) {
		SqlHelper.connectionUrl = connectionUrl;
	}
}
