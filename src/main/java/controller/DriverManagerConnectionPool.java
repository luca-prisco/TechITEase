package controller; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import java.util.List;
import java.util.TimeZone;


public class DriverManagerConnectionPool {

	private static List<Connection> freeDbConnections;

	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("DB driver not found:"+ e.getMessage());
		} 
	}
	
	private static synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;

		String dbName = "TechITEase";
		String dbUsername = "root";
		String dbPassword = "lucaprisco";
		String url = "jdbc:mysql://localhost:3306/"+ dbName + "?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true"
				+ "&useLegacyDatetimeCode=false&serverTimezone=UTC";
		
		try {
		newConnection = DriverManager.getConnection(url, dbUsername, dbPassword);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		newConnection.setAutoCommit(true);
		return newConnection;
	}


	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeDbConnections.isEmpty()) {
			connection = freeDbConnections.get(0);
			freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnection();
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} else {
			connection = createDBConnection();		
		}

		return connection;
	}

	public static synchronized void releaseConnection(Connection connection) {
		if(connection != null) freeDbConnections.add(connection);
	}
}