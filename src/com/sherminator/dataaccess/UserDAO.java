/*
 * 
 * @author	Ang Weeliang
 * @author	Nasruddin
 * @author	Salihan
 * @version	2.4
 * 
 */

package com.sherminator.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sherminator.model.User;
import com.sherminator.util.CWS_DBConnection;

public class UserDAO implements UserDataAccess {

	// will return true if username and password matches
	public boolean authenticate(User user) {
		boolean isAuthenticated = false;
		
		Connection connection = CWS_DBConnection.getConnection();
		
		try {
			
			PreparedStatement statement = connection.prepareStatement("SELECT [passowrd] FROM [dbo].[User] WHERE username=?");
			statement.setString(1, user.getUserName());
			
			ResultSet resultset = statement.executeQuery();
			
			String password = resultset.getString("[password]");
			
			// Is the password match?
			if(user.getPassword().equals(password)) {
				// YES!!!
				isAuthenticated = true;
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isAuthenticated;
	}

}
