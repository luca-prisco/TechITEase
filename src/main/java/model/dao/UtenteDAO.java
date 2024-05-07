package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.bean.UtenteBean;

public class UtenteDAO {

	public static UtenteBean loginUser(String emailUtente, String password, Connection con) throws SQLException {

		PreparedStatement ps = null;
		UtenteBean utente = new UtenteBean();

		String sql = "SELECT * FROM Utente WHERE emailUtente = ? AND password = ?";

		ps = con.prepareStatement(sql);
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
		
		return utente;

	}
	
	public synchronized void registerUser(UtenteBean utente, Connection con) throws SQLException {
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO Utente (emailUtente, nome, cognome, telefono, password, isAdmin) VALUES(?, ?, ?, ?, ?, ?)";
		
		ps = con.prepareStatement(sql);
		
		ps.setString(1, utente.getEmailUtente());
		ps.setString(2, utente.getNome());
		ps.setString(3, utente.getCognome());
		ps.setString(4, utente.getTelefono());
		ps.setString(5, utente.getPassword());
		ps.setBoolean(6, utente.isAdmin());
		
		ps.executeUpdate();
	}

}
