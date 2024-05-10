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
		
		UtenteBean utente = new UtenteBean();

		String sql = "SELECT * FROM " + UtenteDAO.TABLE_NAME + " WHERE emailUtente = ? AND password = ?";

		try {
			connection = dmcp.getConnection(); //Ottengo la connection dal connection pool
			ps = connection.prepareStatement(sql);
			ps.setString(1, emailUtente);
			ps.setString(2, password);
	
			ResultSet rs = ps.executeQuery();
	
			if (rs.next()) {
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
	
	public synchronized void registerUser(UtenteBean utente) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;	
		
		String sql = "INSERT INTO " + UtenteDAO.TABLE_NAME + " (emailUtente, nome, cognome, telefono, password, isAdmin) VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			connection = dmcp.getConnection(); //Ottengo la connection dalla connection pool
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, utente.getEmailUtente());
			ps.setString(2, utente.getNome());
			ps.setString(3, utente.getCognome());
			ps.setString(4, utente.getTelefono());
			ps.setString(5, utente.getPassword());
			ps.setBoolean(6, utente.isAdmin());
			
			ps.executeUpdate();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
	}
}
