package model.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.DriverManagerConnectionPool;
import model.bean.*;
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
		
		String sql = "INSERT INTO " + ProdottoDAO.TABLE_NAME + " (nomeProdotto, brand, categoria, descrizione, dettagli) VALUES  (?, ?, ?, ?, ?)";
		
		
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
		String sql2 = "INSERT INTO " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " (colore, hdd, ram, quantita, prezzo, IDProdotto) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			psProdSpec = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
		
			List<Specifiche> specifiche = prodotto.getSpecifiche();
			for(Specifiche specifica : specifiche) {
				psProdSpec.setString(1, specifica.getColore());
				psProdSpec.setInt(2, specifica.getHdd());
				psProdSpec.setInt(3, specifica.getRam());
				psProdSpec.setInt(4, specifica.getQuantita());
				psProdSpec.setBigDecimal(5, specifica.getPrezzo());
				psProdSpec.setInt(6, IDProdotto);
				psProdSpec.addBatch(); // Aggiunge al batch
			}
			
			psProdSpec.executeBatch(); //Esegue il batch
			
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
	    Collection<ProdottoBean> prodotti = new ArrayList<>();

	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String sql = "SELECT p.*, s.* FROM " + ProdottoDAO.TABLE_NAME + " p JOIN " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " s ON p.IDProdotto = s.IDProdotto";

	    try {
	        connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	        rs = ps.executeQuery();

	        // Mappa per tenere traccia dei prodotti mentre si scansionano le righe del ResultSet
	        Map<Integer, ProdottoBean> mapProdotti = new HashMap<>();

	        while (rs.next()) {
	            int IDProdotto = rs.getInt("IDProdotto");

	            // Se il prodotto non è stato ancora creato, lo crea e lo aggiunge alla mappa
	            if (!mapProdotti.containsKey(IDProdotto)) {
	                ProdottoBean prodotto = new ProdottoBean();
	                prodotto.setIDProdotto(IDProdotto);
	                prodotto.setNomeProdotto(rs.getString("nomeProdotto"));
	                prodotto.setBrand(rs.getString("brand"));
	                prodotto.setCategoria(rs.getString("categoria"));
	                prodotto.setDescrizione(rs.getString("descrizione"));
	                prodotto.setDettagli(rs.getString("dettagli"));

	                mapProdotti.put(IDProdotto, prodotto);
	            }

	            // Aggiunge le specifiche al prodotto
	            Specifiche specifiche = new Specifiche();
	            specifiche.setIDSpecifiche(rs.getInt("IDSpecifiche"));
	            specifiche.setColore(rs.getString("colore"));
	            specifiche.setHdd(rs.getInt("hdd"));
	            specifiche.setRam(rs.getInt("ram"));
	            specifiche.setQuantita(rs.getInt("quantita"));
	            specifiche.setPrezzo(rs.getBigDecimal("prezzo"));
	            specifiche.setNumVendite(rs.getInt("numVendite"));
	            specifiche.setImage(rs.getBytes("photo"));

	            mapProdotti.get(IDProdotto).getSpecifiche().add(specifiche);
	        }

	        // Aggiunge i prodotti dalla mappa alla collezione finale
	        prodotti.addAll(mapProdotti.values());
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
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

	    return prodotti;
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
