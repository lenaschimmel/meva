package de.gmino.issuemap.server;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.cloud.sql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
	static Connection con;

	public static Connection getConnection() {
		if (con != null) {
			try {
				java.sql.Statement stmt = con.createStatement();
				ResultSet rset = stmt.executeQuery("SELECT NOW() FROM DUAL");
			} catch (SQLException e) {
				System.err.println("Detected db error, trying to reconnect.");
				con = null;
			}
		}
		if (con == null) {
			try {
				//Class.forName("com.mysql.jdbc.Driver");
				//final String db_string = "jdbc:mysql://85.214.33.79:3306/issuemap";
				//con = DriverManager.getConnection(db_string, "checkin", "kw9fn39dfn");
				
				DriverManager.registerDriver(new AppEngineDriver());
				//DriverManager.registerDriver(new com.google.cloud.sql.Driver());
			    con = DriverManager.getConnection("jdbc:google:rdbms://gmino-de-cloud-sql:gmino-socialgis-sql/gminosocialmap");
			    con.createStatement().execute("set names 'utf8';");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
}
