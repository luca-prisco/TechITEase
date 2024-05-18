package model;

import java.sql.SQLException;

import controller.DriverManagerConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PhotoControl {
	
	private static final String TABLE_NAME = "Specifiche";
	private DriverManagerConnectionPool dmcp = null;	

	public PhotoControl(DriverManagerConnectionPool dmcp) {
		this.dmcp = dmcp;
		
		System.out.println("DriverManager PhotoControl Model creation....");
	}
	
	public synchronized byte[] load(int id1, int id2) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		byte[] bt = null;


		String sql = "SELECT photo FROM " + PhotoControl.TABLE_NAME + " WHERE IDProdotto = ? AND IDSpecifiche = ?";
		
		try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, id1);
			ps.setInt(2, id2);
			rs = ps.executeQuery();

			if (rs.next()) {
				bt = rs.getBytes("photo");
			}

		} catch (SQLException sqlException) {
			System.out.println(sqlException);
		} 
			finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException sqlException) {
				System.out.println(sqlException);
			} 
		}
		return bt;
	}
	
	public synchronized void updatePhoto(int id1, int id2, InputStream photo) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		
		String sql = "UPDATE " + PhotoControl.TABLE_NAME + " SET photo = ? WHERE IDProdotto = ? AND IDSpecifiche = ?";
		
		try {
			connection = dmcp.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);
			try {
				ps.setBinaryStream(1, photo, photo.available());
				ps.setInt(2, id1);	
				ps.setInt(3, id2);
				ps.executeUpdate();
				connection.commit();
			} catch (IOException e) {
				System.out.println(e);
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException sqlException) {
				System.out.println(sqlException);
			}
		}
	}
}
