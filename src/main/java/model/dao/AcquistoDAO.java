package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import controller.DriverManagerConnectionPool;
import model.bean.AcquistoBean;

public class AcquistoDAO {
	
	private static final String TABLE_NAME = "Acquisto";
	private DriverManagerConnectionPool dmcp = null;	
	
	public AcquistoDAO(DriverManagerConnectionPool dmcp) {
		this.dmcp = dmcp;
		
		System.out.println("DriverManager Acquisto Model creation....");
	}


	public synchronized void insertAcquisto(AcquistoBean acquisto) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO " + AcquistoDAO.TABLE_NAME + " (nomeProdotto, brand, colore, hdd, ram, quantita, prezzoUnitario, IDOrdine, IDProdotto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = dmcp.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, acquisto.getNome());
			ps.setString(2, acquisto.getBrand());
			ps.setString(3, acquisto.getColore());
			ps.setString(4, acquisto.getHdd());
			ps.setInt(5, acquisto.getRam());
			ps.setInt(6, acquisto.getQuantita());
			ps.setBigDecimal(7, acquisto.getPrezzoUnitario());
			ps.setInt(8, acquisto.getIDOrdine());
			ps.setInt(9, acquisto.getIDProdotto());
	
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

	public synchronized Collection<AcquistoBean> doRetrieveByIDOrdine(int IDOrdine) throws SQLException {
		Collection<AcquistoBean> acquisti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + AcquistoDAO.TABLE_NAME + " WHERE IDOrdine = ?";

		try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IDOrdine);
			rs = ps.executeQuery();
	
			while (rs.next()) {
				AcquistoBean acquisto = new AcquistoBean();
				acquisto.setIDAcquisto(rs.getInt("IDAcquisto"));
				acquisto.setNome(rs.getString("nomeProdotto"));
				acquisto.setBrand(rs.getString("brand"));
				acquisto.setColore(rs.getString("colore"));
				acquisto.setHdd(rs.getString("hdd"));
				acquisto.setRam(rs.getInt("ram"));
				acquisto.setQuantita(rs.getInt("quantita"));
				acquisto.setPrezzoUnitario(rs.getBigDecimal("prezzoUnitario"));
				acquisto.setIDOrdine(IDOrdine);
				acquisto.setIDProdotto(rs.getInt("IDProdotto"));
	
				acquisti.add(acquisto);
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
		
		return acquisti;
	}

	public synchronized Collection<AcquistoBean> doRetrieveByIDProdotto(int IDProdotto) throws SQLException {
		Collection<AcquistoBean> acquisti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM " + AcquistoDAO.TABLE_NAME + " WHERE IDProdotto = ?";
		
		try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, IDProdotto);
			rs = ps.executeQuery();
	
			while (rs.next()) {
				AcquistoBean acquisto = new AcquistoBean();
				acquisto.setIDAcquisto(rs.getInt("IDAcquisto"));
				acquisto.setNome(rs.getString("nome"));
				acquisto.setBrand(rs.getString("brand"));
				acquisto.setColore(rs.getString("colore"));
				acquisto.setHdd(rs.getString("hdd"));
				acquisto.setRam(rs.getInt("ram"));
				acquisto.setQuantita(rs.getInt("quantita"));
				acquisto.setPrezzoUnitario(rs.getBigDecimal("prezzoUnitario"));
				acquisto.setIDOrdine(rs.getInt("IDOrdine"));
				acquisto.setIDProdotto(IDProdotto);
	
				acquisti.add(acquisto);
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}

		return acquisti;
	}
}
