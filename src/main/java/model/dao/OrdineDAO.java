package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import model.bean.OrdineBean;

public class OrdineDAO {
    
    public OrdineDAO() {
        
    }
    
    public void insertOrdine(OrdineBean ordine, Connection con) throws SQLException {
        PreparedStatement ps = null;
        
        String sql = "INSERT INTO Ordine (dataOrdine, dataConsegna, prezzoTotale, via, cap, citta, emailUtente) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        ps = con.prepareStatement(sql);
        
        // Imposto la data di ordine
        Date dataOrdine = new Date();
        ps.setDate(1, new java.sql.Date(dataOrdine.getTime()));
        
        // Imposto la data di consegna indicativamente 5 giorni successivi alla data dell'ordine
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataOrdine);
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date dataConsegna = cal.getTime();
        ps.setDate(2, new java.sql.Date(dataConsegna.getTime()));
        
        ps.setBigDecimal(3, ordine.getPrezzoTotale());
        ps.setString(4, ordine.getVia());
        ps.setString(5, ordine.getCap());
        ps.setString(6, ordine.getCitta());
        ps.setString(7, ordine.getEmailUtente());

        ps.executeUpdate(); 
    }
    
    public void updateOrdine(OrdineBean ordine, Connection con) throws SQLException {
        PreparedStatement ps = null;
        
        String sql = "UPDATE Ordine SET dataOrdine = ?, dataConsegna = ?, prezzoTotale = ?, via = ?, cap = ?, citta = ?, emailUtente = ? WHERE IDOrdine = ?";
    
        ps = con.prepareStatement(sql);
        
        // Imposto la data di ordine
        Date dataOrdine = new Date();
        ps.setDate(1, new java.sql.Date(dataOrdine.getTime()));
        
        // Imposto la data di consegna indicativamente 5 giorni successivi alla data dell'ordine
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataOrdine);
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date dataConsegna = cal.getTime();
        ps.setDate(2, new java.sql.Date(dataConsegna.getTime()));
        
        ps.setBigDecimal(3, ordine.getPrezzoTotale());
        ps.setString(4, ordine.getVia());
        ps.setString(5, ordine.getCap());
        ps.setString(6, ordine.getCitta());
        ps.setString(7, ordine.getEmailUtente());
        ps.setInt(8, ordine.getIDOrdine());
        
        ps.executeUpdate();
    }
    
    
    public Collection<OrdineBean> doRetrieveAll(Connection con) throws SQLException {
        Collection<OrdineBean> ordini = new ArrayList<>();
        
        PreparedStatement ps = null;
        
        String sql = "SELECT * FROM Ordine";
        
        ps = con.prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            OrdineBean ordine = new OrdineBean();
            ordine.setIDOrdine(rs.getInt("IDOrdine"));
            ordine.setDataOrdine(rs.getDate("dataOrdine"));
            ordine.setDataConsegna(rs.getDate("dataConsegna"));
            ordine.setPrezzoTotale(rs.getBigDecimal("prezzoTotale"));
            ordine.setVia(rs.getString("via"));
            ordine.setCap(rs.getString("cap"));
            ordine.setCitta(rs.getString("citta"));
            ordine.setEmailUtente(rs.getString("emailUtente"));
            ordini.add(ordine);
        }
        
        return ordini;
    }
    
    public Collection<OrdineBean> doRetrieveByEmail(String emailUtente, Connection con) throws SQLException {
        Collection<OrdineBean> ordini = new ArrayList<>();
        
        PreparedStatement ps = null;
        
        String sql = "SELECT * FROM Ordine WHERE emailUtente = ?";
        
        ps = con.prepareStatement(sql);
        ps.setString(1, emailUtente);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            OrdineBean ordine = new OrdineBean();
            ordine.setIDOrdine(rs.getInt("IDOrdine"));
            ordine.setDataOrdine(rs.getDate("dataOrdine"));
            ordine.setDataConsegna(rs.getDate("dataConsegna"));
            ordine.setPrezzoTotale(rs.getBigDecimal("prezzoTotale"));
            ordine.setVia(rs.getString("via"));
            ordine.setCap(rs.getString("cap"));
            ordine.setCitta(rs.getString("citta"));
            ordine.setEmailUtente(rs.getString("emailUtente"));
            ordini.add(ordine);
        }
        
        return ordini;
    }
    
    public Collection<OrdineBean> doRetrieveByData(Date data, Connection con) throws SQLException {
        Collection<OrdineBean> ordini = new ArrayList<>();
        
        PreparedStatement ps = null;
        
        String sql = "SELECT * FROM Ordine WHERE dataOrdine = ?";
        
        ps = con.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(data.getTime()));
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            OrdineBean ordine = new OrdineBean();
            ordine.setIDOrdine(rs.getInt("IDOrdine"));
            ordine.setDataOrdine(rs.getDate("dataOrdine"));
            ordine.setDataConsegna(rs.getDate("dataConsegna"));
            ordine.setPrezzoTotale(rs.getBigDecimal("prezzoTotale"));
            ordine.setVia(rs.getString("via"));
            ordine.setCap(rs.getString("cap"));
            ordine.setCitta(rs.getString("citta"));
            ordine.setEmailUtente(rs.getString("emailUtente"));
            ordini.add(ordine);
        }
        
        return ordini;
    }
}
