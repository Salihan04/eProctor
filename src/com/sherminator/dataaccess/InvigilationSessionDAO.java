package com.sherminator.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sherminator.model.InvigilationSession;
import com.sherminator.model.Proctor;
import com.sherminator.util.System_DBConnection;

public class InvigilationSessionDAO implements InvigilationSessionDataAccess {

	@Override
	public InvigilationSession getAvailableInvigilator() {
		InvigilationSession session = null;
		
		Connection connection = System_DBConnection.getConnection();

		try {
			String sql = "SELECT TOP 1 [username], [ip_address], [port] FROM [dbo].[Proctor_Invigilation_Session] "
					+ "WHERE is_available=1";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultset = statement.executeQuery();

			if(resultset.next()) {
				String username = resultset.getString("username");
				String ip_address = resultset.getString("ip_address");
				int port = resultset.getInt("port");
				
				session = new InvigilationSession(username, ip_address, port);
			}

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return session;
	}
	
	@Override
	public boolean makeAvailable(Proctor proctor, String ip_address) {
		boolean isCreated = false;

		Connection connection = System_DBConnection.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE [Proctor_Invigilation_Session] SET is_available = 1, ip_address = ? WHERE username = ?;");
			statement.setString(1, ip_address);
			statement.setString(2, proctor.getUserName());
			statement.execute();
			
			connection.close();
			isCreated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isCreated;
	}

	@Override
	public boolean makeUnavailable(Proctor proctor) {
		boolean isCreated = false;

		Connection connection = System_DBConnection.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE [Proctor_Invigilation_Session] SET is_available = 0, ip_address = NULL WHERE username = ?;");
			statement.setString(1, proctor.getUserName());
			statement.execute();
			
			connection.close();
			isCreated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isCreated;
	}

	@Override
	public boolean disablePort(Proctor proctor, int port) {
		boolean isCreated = false;

		Connection connection = System_DBConnection.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE [Proctor_Invigilation_Session] SET is_available = 0 WHERE username = ? AND port = ?;");
			statement.setString(1, proctor.getUserName());
			statement.setInt(2, port);
			statement.execute();
			
			connection.close();
			isCreated = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isCreated;
	}

}
