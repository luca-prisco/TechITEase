package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DriverManagerConnectionPool;
import model.bean.UtenteBean;

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
}
