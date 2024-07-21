package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import controller.DriverManagerConnectionPool;
import model.bean.*;

public class UtenteDAO {
	
	private static final String TABLE_NAME = "Utente";
	private DriverManagerConnectionPool dmcp = null;	
	
	public UtenteDAO(DriverManagerConnectionPool dmcp) {
		this.dmcp = dmcp;
		
		System.out.println("DriverManager User Model creation....");
	}

	public synchronized UtenteBean loginUser(String emailUtente, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		
		UtenteBean utente = null;

		String sql = "SELECT * FROM " + UtenteDAO.TABLE_NAME + " WHERE emailUtente = ? AND password = ?";

		try {
			connection = dmcp.getConnection(); //Ottengo la connection dal connection pool
			ps = connection.prepareStatement(sql);
			ps.setString(1, emailUtente);
			ps.setString(2, password);
	
			ResultSet rs = ps.executeQuery();
	
			if (rs.next()) {
				utente = new UtenteBean();
				utente.setEmailUtente(rs.getString("emailUtente"));
				utente.setNome(rs.getString("nome"));
				utente.setCognome(rs.getString("cognome"));
				utente.setTelefono(rs.getString("telefono"));
				utente.setPassword(rs.getString("password"));
				utente.setAdmin(rs.getBoolean("isAdmin"));
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
		
		return utente;

	}
	
	public synchronized boolean registerUser(String emailUtente, String nome, String cognome, String telefono, String password, boolean isAdmin) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		boolean success = false;
		
		String sql = "INSERT INTO " + UtenteDAO.TABLE_NAME + " (emailUtente, nome, cognome, telefono, password, isAdmin) VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			connection = dmcp.getConnection(); //Ottengo la connection dalla connection pool
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, emailUtente);
			ps.setString(2, nome);
			ps.setString(3, cognome);
			ps.setString(4, telefono);
			ps.setString(5, password);
			ps.setBoolean(6, isAdmin);
			
			int result = ps.executeUpdate();
			
			if(result > 0)
				success = true;
			
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
		
		return success;
	}
	
	public boolean checkByEmail(String emailUtente) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM " + UtenteDAO.TABLE_NAME + " WHERE emailUtente = ?";

		try {
			connection = dmcp.getConnection(); //Ottengo la connection dalla connection pool
			ps = connection.prepareStatement(sql);
			ps.setString(1, emailUtente);
			rs = ps.executeQuery();
			
			return rs.next();
			
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} finally {
				if(connection != null)
					dmcp.releaseConnection(connection);
			}
		}
	}
	
	public synchronized Collection<UtenteBean> doRetrieveAdmins() throws SQLException {
		Collection<UtenteBean> admins = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + UtenteDAO.TABLE_NAME + " WHERE isAdmin = ?";
		
		try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setBoolean(1, true);
			rs = ps.executeQuery();
	
			while (rs.next()) {
				UtenteBean admin = new UtenteBean();
				admin.setEmailUtente(rs.getString("emailUtente"));
				admin.setNome(rs.getString("nome"));
				admin.setCognome(rs.getString("cognome"));
				admin.setTelefono(rs.getString("telefono"));
				admin.setAdmin(rs.getBoolean("isAdmin"));
				admins.add(admin);
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}

		return admins;
	}
	
	public synchronized void updateUser(UtenteBean utente, String email) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		
	    String updateUtenteSql = "UPDATE " + UtenteDAO.TABLE_NAME + " SET emailUtente = ?, nome = ?, cognome = ?, telefono = ? WHERE emailUtente = ?";

	    try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(updateUtenteSql);
			
			ps.setString(1, utente.getEmailUtente());
			ps.setString(2, utente.getNome());
			ps.setString(3, utente.getCognome());
			ps.setString(4, utente.getTelefono());
			ps.setString(6, email);
			
			ps.executeUpdate();
	    }finally {
			try {
				if (ps != null) ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
	}
	
	public synchronized void updatePassword(String password, String email) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		
	    String updateUtenteSql = "UPDATE " + UtenteDAO.TABLE_NAME + " SET password = ? WHERE emailUtente = ?";

	    try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(updateUtenteSql);
			
			ps.setString(1, password);
			ps.setString(2, email);
			
			ps.executeUpdate();
	    }finally {
			try {
				if (ps != null) ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
	}
	
	public synchronized void insertAdmin(UtenteBean admin) throws SQLException {
	    Connection connection = null;
	    PreparedStatement ps = null;
	    
	    String sql = "INSERT INTO " + UtenteDAO.TABLE_NAME + " (emailUtente, nome, cognome, telefono, password, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try {
	        connection = dmcp.getConnection(); 
	        ps = connection.prepareStatement(sql);
	        
	        ps.setString(1, admin.getEmailUtente());
	        ps.setString(2, admin.getNome());
	        ps.setString(3, admin.getCognome());
	        ps.setString(4, admin.getTelefono());
	        ps.setString(5, admin.getPassword());
	        ps.setBoolean(6, admin.isAdmin());
	        
	        ps.executeUpdate(); // Execute the insert statement
	    } finally {
	        if (ps != null) ps.close();
	        if (connection != null) dmcp.releaseConnection(connection);
	    }
	}

	public synchronized void deleteAdmin(String email) throws SQLException {
	    Connection connection = null;
	    PreparedStatement ps = null;
	    
	    String sql = "DELETE FROM " + UtenteDAO.TABLE_NAME + " WHERE emailUtente = ?";
	    
	    try {
	        connection = dmcp.getConnection(); 
	        ps = connection.prepareStatement(sql);
	        
	        ps.setString(1, email);
	        
	        ps.executeUpdate(); 
	    } finally {
	        if (ps != null) ps.close();
	        if (connection != null) dmcp.releaseConnection(connection);
	    }
	}

}
