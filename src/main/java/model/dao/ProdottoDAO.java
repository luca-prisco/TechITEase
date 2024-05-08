package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.ProdottoBean;

public class ProdottoDAO {
	
	public ProdottoDAO() {
		
	}
	
	public void insertProdotto(ProdottoBean prodotto, Connection con) throws SQLException {
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO Prodotto (nomeProdotto, brand, categoria, descrizione, dettagli) VALUES  (?, ?, ?, ?, ?)";
		
		ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, prodotto.getNomeProdotto());
		ps.setString(2, prodotto.getBrand());
		ps.setString(3, prodotto.getCategoria());
		ps.setString(4, prodotto.getDescrizione());
		ps.setString(5, prodotto.getDettagli());

		ps.executeUpdate(); 
		
		int IDProdotto;
		
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.first()) {
			IDProdotto = rs.getInt(1);
		}
		else
			throw new RuntimeException();
		
		String sql2 = "INSERT INTO Specifiche (colore, hdd, ram, quantita, prezzo, IDProdotto) VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement psProdSpec = null;
		psProdSpec = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
		
		psProdSpec.setString(1, prodotto.getColore());
		psProdSpec.setInt(2, prodotto.getHdd());
		psProdSpec.setInt(3, prodotto.getRam());
		psProdSpec.setInt(4, prodotto.getQuantita());
		psProdSpec.setBigDecimal(5, prodotto.getPrezzo());
		psProdSpec.setInt(6, IDProdotto);
		
		psProdSpec.executeUpdate();
	
	}
	
	public synchronized void updateProduct(ProdottoBean prodotto, Connection con) throws SQLException {
		PreparedStatement ps = null;
		
		String sql = "UPDATE Prodotto SET nomeProdotto = ?, brand = ?, categoria = ?, descrizione = ?, dettagli = ? WHERE IDProdotto = ?";
	
		ps = con.prepareStatement(sql);
		
		ps.setInt(6, prodotto.getIDProdotto());
		
		ps.setString(1, prodotto.getNomeProdotto());
		ps.setString(2, prodotto.getBrand());
		ps.setString(3, prodotto.getCategoria());
		ps.setString(4, prodotto.getDescrizione());
		ps.setString(5, prodotto.getDettagli());
		
		ps.executeUpdate();
		
		PreparedStatement psProdSpec = null;
		
		String sql2 = "UPDATE Specifiche SET colore = ?, hdd = ?, ram = ?, quantita = ?, prezzo = ? WHERE IDProdotto = ? AND IDSpecifiche = ?";
		
		psProdSpec = con.prepareStatement(sql2);
		
		ps.setInt(6, prodotto.getIDProdotto());
		ps.setInt(7, prodotto.getIDSpecifiche());
		
		ps.setString(1, prodotto.getColore());
		ps.setInt(2, prodotto.getHdd());
		ps.setInt(3, prodotto.getRam());
		ps.setInt(4, prodotto.getQuantita());
		ps.setBigDecimal(5, prodotto.getPrezzo());
		
		psProdSpec.executeUpdate();
	}
	
	
	public Collection<ProdottoBean> doRetrieveAll(Connection con) throws SQLException {
		Collection<ProdottoBean> prodotti = new ArrayList<ProdottoBean>();
		
		PreparedStatement ps = null;
		
		String sql = "SELECT p.*, s.* FROM Prodotto p JOIN Specifiche s ON p.IDProdotto = s.IDProdotto";
			
		ps = con.prepareStatement(sql);
		
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
		
		return prodotti;
	}
	
	public ProdottoBean doRetrieveById(int IDProdotto, int IDSpecifiche, Connection con) throws SQLException {
		
		ProdottoBean prodotto = new ProdottoBean();
		PreparedStatement ps = null;
		
		String sql = "SELECT p.*, s.* FROM Prodotto p JOIN Specifiche s ON p.IDProdotto = s.IDProdotto";
		
		ps = con.prepareStatement(sql);
		
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
		
		return prodotto;
	}
	
	

}
