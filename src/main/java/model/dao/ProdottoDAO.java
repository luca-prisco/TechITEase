package model.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import controller.DriverManagerConnectionPool;
import model.dao.ProdottoDAO;
import model.bean.ProdottoBean;

public class ProdottoDAO {
	
	private static final String TABLE_NAME = "Prodotto";
	private static final String TABLE_NAME_SPECIFICHE = "Specifiche";
	private DriverManagerConnectionPool dmcp = null;	

	public ProdottoDAO(DriverManagerConnectionPool dmcp) {
		this.dmcp = dmcp;
		
		System.out.println("DriverManager Product Model creation....");
	}
	
	public synchronized void insertProdotto(ProdottoBean prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		PreparedStatement psProdSpec = null;
		
		int IDProdotto;
		
		String sql = "INSERT INTO" + ProdottoDAO.TABLE_NAME + "(nomeProdotto, brand, categoria, descrizione, dettagli) VALUES  (?, ?, ?, ?, ?)";
		
		
		try {
			connection = dmcp.getConnection(); //Ottengo la connection dal connection pool
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	
			ps.setString(1, prodotto.getNomeProdotto());
			ps.setString(2, prodotto.getBrand());
			ps.setString(3, prodotto.getCategoria());
			ps.setString(4, prodotto.getDescrizione());
			ps.setString(5, prodotto.getDettagli());
	
			ps.executeUpdate(); 
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.first()) {
				IDProdotto = rs.getInt(1);
			}
			else
				throw new RuntimeException();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
		String sql2 = "INSERT INTO" + ProdottoDAO.TABLE_NAME_SPECIFICHE +"(colore, hdd, ram, quantita, prezzo, IDProdotto) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			psProdSpec = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
		
			psProdSpec.setString(1, prodotto.getColore());
			psProdSpec.setInt(2, prodotto.getHdd());
			psProdSpec.setInt(3, prodotto.getRam());
			psProdSpec.setInt(4, prodotto.getQuantita());
			psProdSpec.setBigDecimal(5, prodotto.getPrezzo());
			psProdSpec.setInt(6, IDProdotto);
			
			psProdSpec.executeUpdate();
			
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
	
	}
	
	public synchronized void updateProduct(ProdottoBean prodotto) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		PreparedStatement psProdSpec = null;
		
		String sql = "UPDATE " + ProdottoDAO.TABLE_NAME + " SET nomeProdotto = ?, brand = ?, categoria = ?, descrizione = ?, dettagli = ? WHERE IDProdotto = ?";
	
		try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(sql);
			
			ps.setInt(6, prodotto.getIDProdotto());
			
			ps.setString(1, prodotto.getNomeProdotto());
			ps.setString(2, prodotto.getBrand());
			ps.setString(3, prodotto.getCategoria());
			ps.setString(4, prodotto.getDescrizione());
			ps.setString(5, prodotto.getDettagli());
			
			ps.executeUpdate();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
		
		String sql2 = "UPDATE " + ProdottoDAO.TABLE_NAME_SPECIFICHE +" SET colore = ?, hdd = ?, ram = ?, quantita = ?, prezzo = ? WHERE IDProdotto = ? AND IDSpecifiche = ?";
		
		try {
			psProdSpec = connection.prepareStatement(sql2);
			
			ps.setInt(6, prodotto.getIDProdotto());
			ps.setInt(7, prodotto.getIDSpecifiche());
			
			ps.setString(1, prodotto.getColore());
			ps.setInt(2, prodotto.getHdd());
			ps.setInt(3, prodotto.getRam());
			ps.setInt(4, prodotto.getQuantita());
			ps.setBigDecimal(5, prodotto.getPrezzo());
			
			psProdSpec.executeUpdate();
			
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
	}
	
	
	public synchronized Collection<ProdottoBean> doRetrieveAll() throws SQLException {
		Collection<ProdottoBean> prodotti = new ArrayList<ProdottoBean>();
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		String sql = "SELECT p.*, s.* FROM " + ProdottoDAO.TABLE_NAME + " p JOIN " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " s ON p.IDProdotto = s.IDProdotto";
			
		try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setIDProdotto(rs.getInt(1));
				prodotto.setNomeProdotto(rs.getString(2));
				prodotto.setBrand(rs.getString(3));
				prodotto.setCategoria(rs.getString(4));
				prodotto.setDescrizione(rs.getString(5));
				prodotto.setDettagli(rs.getString(6));
				prodotto.setIDSpecifiche(rs.getInt(7));
				prodotto.setColore(rs.getString(8));
				prodotto.setHdd(rs.getInt(9));
				prodotto.setRam(rs.getInt(10));
				prodotto.setQuantita(rs.getInt(11));
				prodotto.setPrezzo(rs.getBigDecimal(12));
				prodotto.setNumVendite(rs.getInt(13));
				prodotto.setImage(rs.getBytes(14));
				prodotti.add(prodotto);
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
		
		return prodotti;
	}
	
	public ProdottoBean doRetrieveById(int IDProdotto, int IDSpecifiche) throws SQLException {
		
		ProdottoBean prodotto = new ProdottoBean();
		Connection connection = null;
		PreparedStatement ps = null;
		
		String sql = "SELECT p.*, s.* FROM " + ProdottoDAO.TABLE_NAME + " p JOIN " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " s ON p.IDProdotto = s.IDProdotto";
		
		try {
			connection = dmcp.getConnection();
			ps = connection.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				prodotto.setIDProdotto(rs.getInt(1));
				prodotto.setNomeProdotto(rs.getString(2));
				prodotto.setBrand(rs.getString(3));
				prodotto.setCategoria(rs.getString(4));
				prodotto.setDescrizione(rs.getString(5));
				prodotto.setDettagli(rs.getString(6));
				prodotto.setIDSpecifiche(rs.getInt(7));
				prodotto.setColore(rs.getString(8));
				prodotto.setHdd(rs.getInt(9));
				prodotto.setRam(rs.getInt(10));
				prodotto.setQuantita(rs.getInt(11));
				prodotto.setPrezzo(rs.getBigDecimal(12));
				prodotto.setNumVendite(rs.getInt(13));
				prodotto.setImage(rs.getBytes(14));
			} 
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
		
		return prodotto;
	}
	
	public synchronized void deleteProduct(int id1, int id2) throws SQLException {
	    Connection connection = null;
	    PreparedStatement ps = null;
	    PreparedStatement psProdSpec = null;
	    
	    String sql = "DELETE FROM " + ProdottoDAO.TABLE_NAME + " WHERE IDProdotto = ?";
	    String sql2 = "DELETE FROM " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " WHERE IDProdotto = ? AND IDSpecifiche = ?";
	    
	    try {
	        connection = dmcp.getConnection();
	        
	        // Preparo ed eseguo la query per eliminare dalla tabella specifiche
	        psProdSpec = connection.prepareStatement(sql2);
	        psProdSpec.setInt(1, id1);
	        psProdSpec.setInt(2, id2);
	        psProdSpec.executeUpdate();
	        
	        // Prepara ed esegui la query per eliminare dalla tabella prodotto
	        ps = connection.prepareStatement(sql);
	        ps.setInt(1, id1);
	        ps.executeUpdate();
	    
	    } finally {
	        try {
	            if (psProdSpec != null) {
	                psProdSpec.close();
	            }
	            if (ps != null) {
	                ps.close();
	            }
	        } finally {
	            if (connection != null) {
	                dmcp.releaseConnection(connection);
	            }
	        }
	    }
	}
}
