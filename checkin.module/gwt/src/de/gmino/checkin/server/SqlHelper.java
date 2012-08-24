package de.gmino.checkin.server;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlHelper {
	static Connection con;
	public static Connection getConnection()
	{
		if(con == null)
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				final String db_string = "jdbc:mysql://85.214.33.79:3306/checkin";
				con = DriverManager.getConnection(db_string, "checkin", "kw9fn39dfn");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
}
