/*
 * This class centralize the DB connection.
 * 
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class System_DBConnection {
	
	private static String db_url = "jdbc:sqlserver://localhost:1433;databaseName=NasSystem;";
	private static String user_name = "sa";
	private static String password = "root";
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			 connection = DriverManager.getConnection(db_url, user_name, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
}
