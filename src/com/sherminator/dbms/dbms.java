package com.sherminator.dbms;

import java.sql.*;
class dbms{
	public static void main(String[] args) throws SQLException{
		dbconnect();
	}
	
	public static void dbconnect() throws SQLException{
		String USER = "sa";
		String PASS = "root";
		String DB_URL = "jdbc:sqlserver://172.22.128.62:1433;databaseName=NasCWS;";
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		selectStatement(conn);
		addStatement(conn);
	}
	public static void selectStatement(Connection con) throws SQLException {
		String SQL = "SELECT * FROM [User] ORDER BY userName;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()){
			System.out.println(rs.getString("userName") + ", " + rs.getString("Password"));
		}
		rs.close();
		stmt.close();
	}
	
	public static void addStatement(Connection con) throws SQLException{
		String USER = "asd";
		String PASS = "123";
		String SQL = "INSERT INTO [USER] (userName, Password) VALUES ('"+USER+"','"+PASS+"');";
		Statement stmt = con.createStatement();
		stmt.execute(SQL);
		selectStatement(con);
	}
}

