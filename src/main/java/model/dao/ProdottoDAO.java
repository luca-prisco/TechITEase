package model.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
				psProdSpec.setString(2, specifica.getHdd());
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
	        // Altrimenti un prodotto verrebbe creato nuovo per ogni specifica 
	        // In questo modo se l'id è già presente nella mappa la specifica viene aggiunta a quel prodotto e non viene creato un altro
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
	                prodotto.setSpecifiche(new ArrayList<>()); // Inizializza la lista delle specifiche

	                mapProdotti.put(IDProdotto, prodotto);
	            }

	            // Aggiunge le specifiche al prodotto
	            Specifiche specifiche = new Specifiche();
	            specifiche.setIDSpecifiche(rs.getInt("IDSpecifiche"));
	            specifiche.setColore(rs.getString("colore"));
	            specifiche.setHdd(rs.getString("hdd"));
	            specifiche.setRam(rs.getInt("ram"));
	            specifiche.setQuantita(rs.getInt("quantita"));
	            specifiche.setPrezzo(rs.getBigDecimal("prezzo"));
	            specifiche.setNumVendite(rs.getInt("numVendite"));
	            specifiche.setImage(rs.getBytes("photo"));
	            
	            mapProdotti.get(IDProdotto).getSpecifiche().add(specifiche);
	        }

	        // Aggiunge i prodotti dalla mappa alla collezione finale
	        prodotti.addAll(mapProdotti.values());
	    } catch (SQLException e) {
	        e.printStackTrace(); // Gestisci l'eccezione in modo appropriato
	        throw e; // Rilancia l'eccezione per permettere la gestione a livello superiore
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	        } finally {
	            if (connection != null) dmcp.releaseConnection(connection);
	        }
	    }

	    return prodotti;
	}




	public synchronized void deleteProduct(int id1, int id2) throws SQLException {
	    Connection connection = null;
	    PreparedStatement ps = null;
	    PreparedStatement psProdSpec = null;
	    PreparedStatement psCheck = null;
	    
	    String sqlCheck = "SELECT COUNT(*) FROM " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " WHERE IDProdotto = ?";
	    String sqlDeleteSpecifica = "DELETE FROM " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " WHERE IDProdotto = ? AND IDSpecifiche = ?";
	    String sqlDeleteProdotto = "DELETE FROM " + ProdottoDAO.TABLE_NAME + " WHERE IDProdotto = ?";
	    
	    try {
	        connection = dmcp.getConnection();
	        
	        //Controlla il numero di specifiche associate al prodotto
	        psCheck = connection.prepareStatement(sqlCheck);
	        psCheck.setInt(1, id1);
	        ResultSet rsCheck = psCheck.executeQuery();
	        
	        int numSpecifiche = 0;
	        if (rsCheck.next()) { //muove il curosore alla prima riga del ResultSet
	            numSpecifiche = rsCheck.getInt(1); //Ottiene il valore della prima colonna della riga corrente (che è il conteggio delle specifiche).
	        }
	        
	        //Elimina la specifica selezionata
	        psProdSpec = connection.prepareStatement(sqlDeleteSpecifica);
	        psProdSpec.setInt(1, id1);
	        psProdSpec.setInt(2, id2);
	        psProdSpec.executeUpdate();
	        
	        //Se il numero di specifiche è uno elimino anche il prodotto
	        if(numSpecifiche == 1) {
		        ps = connection.prepareStatement(sqlDeleteProdotto);
		        ps.setInt(1, id1);
		        ps.executeUpdate();
	        }
	    
	    } finally {
	        try {
	        	if (psCheck != null) {
	                psCheck.close();
	            }
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
	
	public synchronized ProdottoBean doRetrieveById(int idProdotto) throws SQLException {
	    ProdottoBean prodotto = null;
	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String sql = "SELECT p.*, s.* FROM " + ProdottoDAO.TABLE_NAME + " p " +
	                 "JOIN " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " s " +
	                 "ON p.IDProdotto = s.IDProdotto " +
	                 "WHERE p.IDProdotto = ?";

	    try {
	        connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	        ps.setInt(1, idProdotto);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            if (prodotto == null) {
	                prodotto = new ProdottoBean();
	                prodotto.setIDProdotto(rs.getInt("IDProdotto"));
	                prodotto.setNomeProdotto(rs.getString("nomeProdotto"));
	                prodotto.setBrand(rs.getString("brand"));
	                prodotto.setCategoria(rs.getString("categoria"));
	                prodotto.setDescrizione(rs.getString("descrizione"));
	                prodotto.setDettagli(rs.getString("dettagli"));
	            }

	            Specifiche specifiche = new Specifiche();
	            specifiche.setIDProdotto(rs.getInt("IDProdotto"));
	            specifiche.setIDSpecifiche(rs.getInt("IDSpecifiche"));
	            specifiche.setColore(rs.getString("colore"));
	            specifiche.setHdd(rs.getString("hdd"));
	            specifiche.setRam(rs.getInt("ram"));
	            specifiche.setQuantita(rs.getInt("quantita"));
	            specifiche.setPrezzo(rs.getBigDecimal("prezzo"));
	            specifiche.setNumVendite(rs.getInt("numVendite"));
	            specifiche.setImage(rs.getBytes("photo"));

	            prodotto.getSpecifiche().add(specifiche);
	        }
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	        } finally {
	            if (connection != null) dmcp.releaseConnection(connection);
	        }
	    }

	    return prodotto;
	}

	public synchronized Specifiche recuperaSpecifica(int idProdotto, String colore, int hdd, int ram) throws SQLException {
		Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Specifiche specifica = null;
	    
	    String sql = "SELECT * FROM " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " WHERE IDProdotto = ? AND colore LIKE ? AND hdd = ? AND ram = ?";
	    

	    try {
	        connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	        ps.setInt(1, idProdotto);
	        ps.setString(2, colore);
	        ps.setInt(3, hdd);
	        ps.setInt(4, ram);
	        
	        System.out.println("Executing query: " + ps.toString());
	        
	        rs = ps.executeQuery();
	        
	        while(rs.next()) {
	            specifica = new Specifiche();
	            specifica.setIDSpecifiche(rs.getInt("IDSpecifiche"));
	            specifica.setColore(rs.getString("colore"));
	            specifica.setHdd(rs.getString("hdd"));
	            specifica.setRam(rs.getInt("ram"));
	            specifica.setQuantita(rs.getInt("quantita"));
	            specifica.setPrezzo(rs.getBigDecimal("prezzo"));
	            specifica.setNumVendite(rs.getInt("numVendite"));
	            specifica.setImage(rs.getBytes("photo")); 
	            specifica.setIDProdotto(rs.getInt("IDProdotto"));
	        }
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} finally {
				if (connection != null)
					dmcp.releaseConnection(connection);
			}
		}
	    System.out.println("Recuperata specifica: " + specifica); // Aggiungi questo log

	    return specifica;
	}
	
	//Restituisce i prodotti per numero di vendite
	
	public synchronized Collection<ProdottoSpecificheBean> doRetrieveByVendite() throws SQLException {
	    Collection<ProdottoSpecificheBean> prodotti = new ArrayList<>();

	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String sql = "SELECT p.*, s.* FROM " + ProdottoDAO.TABLE_NAME + " p JOIN " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " s ON p.IDProdotto = s.IDProdotto ORDER BY s.numVendite DESC";

	    try {
	        connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            ProdottoSpecificheBean prodottoSpec = new ProdottoSpecificheBean();
	            
	            ProdottoBean prodotto = new ProdottoBean();
	            prodotto.setIDProdotto(rs.getInt("IDProdotto"));
	            prodotto.setNomeProdotto(rs.getString("nomeProdotto"));
	            prodotto.setBrand(rs.getString("brand"));
	            prodotto.setCategoria(rs.getString("categoria"));
	            prodotto.setDescrizione(rs.getString("descrizione"));
	            prodotto.setDettagli(rs.getString("dettagli"));
	            
	            SpecificheRidotte specRidotte = new SpecificheRidotte();
	            specRidotte.setIDSpecifiche(rs.getInt("IDSpecifiche"));
	            specRidotte.setColore(rs.getString("colore"));
	            specRidotte.setHdd(rs.getString("hdd"));
	            specRidotte.setRam(rs.getInt("ram"));
	            specRidotte.setQuantita(rs.getInt("quantita"));
	            specRidotte.setPrezzo(rs.getBigDecimal("prezzo"));
	            specRidotte.setNumVendite(rs.getInt("numVendite"));

	            prodottoSpec.setProdotto(prodotto);
	            prodottoSpec.setSpecRidotte(specRidotte);

	            prodotti.add(prodottoSpec);
	        }
	    } finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} finally {
				if (connection != null)
					dmcp.releaseConnection(connection);
			}
		}

	    return prodotti;
	}
	
	public synchronized Collection<ProdottoSpecificheBean> doRetrieveByBrand(String brand) throws SQLException {
	    Collection<ProdottoSpecificheBean> prodotti = new ArrayList<>();

	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String sql = "SELECT p.*, s.* FROM " + ProdottoDAO.TABLE_NAME + " p JOIN " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " s ON p.IDProdotto = s.IDProdotto WHERE p.brand = ?";

	    try {
	        connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	        ps.setString(1, brand);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            ProdottoSpecificheBean prodottoSpec = new ProdottoSpecificheBean();
	            
	            ProdottoBean prodotto = new ProdottoBean();
	            prodotto.setIDProdotto(rs.getInt("IDProdotto"));
	            prodotto.setNomeProdotto(rs.getString("nomeProdotto"));
	            prodotto.setBrand(rs.getString("brand"));
	            prodotto.setCategoria(rs.getString("categoria"));
	            prodotto.setDescrizione(rs.getString("descrizione"));
	            prodotto.setDettagli(rs.getString("dettagli"));
	            
	            SpecificheRidotte specRidotte = new SpecificheRidotte();
	            specRidotte.setIDSpecifiche(rs.getInt("IDSpecifiche"));
	            specRidotte.setColore(rs.getString("colore"));
	            specRidotte.setHdd(rs.getString("hdd"));
	            specRidotte.setRam(rs.getInt("ram"));
	            specRidotte.setQuantita(rs.getInt("quantita"));
	            specRidotte.setPrezzo(rs.getBigDecimal("prezzo"));
	            specRidotte.setNumVendite(rs.getInt("numVendite"));

	            prodottoSpec.setProdotto(prodotto);
	            prodottoSpec.setSpecRidotte(specRidotte);

	            prodotti.add(prodottoSpec);
	        }
	    } finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} finally {
				if (connection != null)
					dmcp.releaseConnection(connection);
			}
		}

	    return prodotti;
	}
	
    
    public synchronized Collection<ProdottoSpecificheBean> ordinaPerPrezzo(boolean mod) throws SQLException {
	    Collection<ProdottoSpecificheBean> prodotti = new ArrayList<>();

    	Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String ordine = mod ? "ASC" : "DESC";
	    String sql = "SELECT p.*, s.* FROM " + ProdottoDAO.TABLE_NAME + " p "
	               + "JOIN " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " s ON p.IDProdotto = s.IDProdotto "
	               + "ORDER BY s.prezzo " + ordine;
	    try {
	        connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            ProdottoSpecificheBean prodottoSpec = new ProdottoSpecificheBean();
	            
	            ProdottoBean prodotto = new ProdottoBean();
	            prodotto.setIDProdotto(rs.getInt("IDProdotto"));
	            prodotto.setNomeProdotto(rs.getString("nomeProdotto"));
	            prodotto.setBrand(rs.getString("brand"));
	            prodotto.setCategoria(rs.getString("categoria"));
	            prodotto.setDescrizione(rs.getString("descrizione"));
	            prodotto.setDettagli(rs.getString("dettagli"));
	            
	            SpecificheRidotte specRidotte = new SpecificheRidotte();
	            specRidotte.setIDSpecifiche(rs.getInt("IDSpecifiche"));
	            specRidotte.setColore(rs.getString("colore"));
	            specRidotte.setHdd(rs.getString("hdd"));
	            specRidotte.setRam(rs.getInt("ram"));
	            specRidotte.setQuantita(rs.getInt("quantita"));
	            specRidotte.setPrezzo(rs.getBigDecimal("prezzo"));
	            specRidotte.setNumVendite(rs.getInt("numVendite"));

	            prodottoSpec.setProdotto(prodotto);
	            prodottoSpec.setSpecRidotte(specRidotte);

	            prodotti.add(prodottoSpec);
	        }
	    } finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} finally {
				if (connection != null)
					dmcp.releaseConnection(connection);
			}
		}

	    return prodotti;
    }
    
    public synchronized Collection<ProdottoSpecificheBean> doRetrieveByCategoria(String categoria) throws SQLException {
	    Collection<ProdottoSpecificheBean> prodotti = new ArrayList<>();

	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String sql = "SELECT p.*, s.* FROM " + ProdottoDAO.TABLE_NAME + " p JOIN " + ProdottoDAO.TABLE_NAME_SPECIFICHE + " s ON p.IDProdotto = s.IDProdotto WHERE p.categoria = ?";

	    try {
	        connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	        ps.setString(1, categoria);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            ProdottoSpecificheBean prodottoSpec = new ProdottoSpecificheBean();
	            
	            ProdottoBean prodotto = new ProdottoBean();
	            prodotto.setIDProdotto(rs.getInt("IDProdotto"));
	            prodotto.setNomeProdotto(rs.getString("nomeProdotto"));
	            prodotto.setBrand(rs.getString("brand"));
	            prodotto.setCategoria(rs.getString("categoria"));
	            prodotto.setDescrizione(rs.getString("descrizione"));
	            prodotto.setDettagli(rs.getString("dettagli"));
	            
	            SpecificheRidotte specRidotte = new SpecificheRidotte();
	            specRidotte.setIDSpecifiche(rs.getInt("IDSpecifiche"));
	            specRidotte.setColore(rs.getString("colore"));
	            specRidotte.setHdd(rs.getString("hdd"));
	            specRidotte.setRam(rs.getInt("ram"));
	            specRidotte.setQuantita(rs.getInt("quantita"));
	            specRidotte.setPrezzo(rs.getBigDecimal("prezzo"));
	            specRidotte.setNumVendite(rs.getInt("numVendite"));

	            prodottoSpec.setProdotto(prodotto);
	            prodottoSpec.setSpecRidotte(specRidotte);

	            prodotti.add(prodottoSpec);
	        }
	    } finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} finally {
				if (connection != null)
					dmcp.releaseConnection(connection);
			}
		}

	    return prodotti;
    }

	
	
	public List<SpecificheRidotte> convertToSpecificheRidotte(List<Specifiche> specificheList) {
	    List<SpecificheRidotte> ridotteList = new ArrayList<>();
	    for (Specifiche specifiche : specificheList) {
	        SpecificheRidotte ridotte = new SpecificheRidotte();
	        ridotte.setIDProdotto(specifiche.getIDProdotto());
	        ridotte.setIDSpecifiche(specifiche.getIDSpecifiche());
	        ridotte.setColore(specifiche.getColore());
	        ridotte.setHdd(specifiche.getHdd());
	        ridotte.setRam(specifiche.getRam());
	        ridotteList.add(ridotte);
	    }
	    return ridotteList;
	}
}
