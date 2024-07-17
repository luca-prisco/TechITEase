package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import controller.DriverManagerConnectionPool;
import model.bean.OrdineBean;
import model.bean.PagamentoBean;

public class OrdineDAO {
    
	private static final String TABLE_NAME = "Ordine";
	private DriverManagerConnectionPool dmcp = null;	
	
	public OrdineDAO(DriverManagerConnectionPool dmcp) {
		this.dmcp = dmcp;
		
		System.out.println("DriverManager Order Model creation....");
	}

    
    public synchronized int insertOrdine(OrdineBean ordine) throws SQLException {
    	Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int ordineID = -1;
        
        String sql = "INSERT INTO " + OrdineDAO.TABLE_NAME + " (dataOrdine, dataConsegna, prezzoTotale, via, civico, cap, citta, emailUtente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
        	connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        
	        ps.setDate(1, (java.sql.Date) ordine.getDataOrdine());
	        ps.setDate(2, (java.sql.Date) ordine.getDataConsegna());
	        
	        ps.setBigDecimal(3, ordine.getPrezzoTotale());
	        ps.setString(4, ordine.getVia());
	        ps.setString(5, ordine.getCivico());
	        ps.setString(6, ordine.getCap());
	        ps.setString(7, ordine.getCitta());
	        ps.setString(8, ordine.getEmailUtente());
	
	        ps.executeUpdate();
	        
			rs = ps.getGeneratedKeys();
			if (rs.first()) {
				ordineID = rs.getInt(1);
			}
			else
				throw new RuntimeException();  
        } finally {
			try {
				if(rs != null) rs.close();
				if (ps != null) ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
        return ordineID;
    }
    
    public synchronized void insertPagamento(PagamentoBean pagamento) throws SQLException {
    	Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "INSERT INTO Pagamento (IDOrdine, nomeCarta, cognomeCarta, numeroCarta, scadenzaCarta, cvv) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
        	connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	     
            ps.setInt(1, pagamento.getIDOrdine());
            ps.setString(2, pagamento.getNomeCarta());
            ps.setString(3, pagamento.getCognomeCarta());
            ps.setString(4, pagamento.getNumeroCarta());
            ps.setString(5, pagamento.getScadenzaCarta());
            ps.setString(6, pagamento.getCvv());
            
	        ps.executeUpdate();
	        
        } finally {
			try {
				if(rs != null) rs.close();
				if (ps != null) ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
    }
    
    
    public synchronized void updateOrdine(OrdineBean ordine) throws SQLException {
    	Connection connection = null;
        PreparedStatement ps = null;
        
        String sql = "UPDATE " + OrdineDAO.TABLE_NAME + " SET dataOrdine = ?, dataConsegna = ?, prezzoTotale = ?, via = ?, cap = ?, citta = ?, emailUtente = ? WHERE IDOrdine = ?";
    
        try {
        	connection = dmcp.getConnection();
        	ps = connection.prepareStatement(sql);
        
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
        } finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
    }
    
    
    public synchronized Collection<OrdineBean> doRetrieveAll() throws SQLException {
        Collection<OrdineBean> ordini = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql = "SELECT * FROM " + OrdineDAO.TABLE_NAME +"";
        
        try {	
        	connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
	        
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
        } finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}
        
        return ordini;
    }
    
    public synchronized Collection<OrdineBean> doRetrieveByEmail(String emailUtente) throws SQLException {
        Collection<OrdineBean> ordini = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql = "SELECT * FROM " + OrdineDAO.TABLE_NAME + " WHERE emailUtente = ?";
        
        try {
        	connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
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
        } finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}   
        return ordini;
    }
    
    public synchronized Collection<OrdineBean> doRetrieveByData(Date data) throws SQLException {
        Collection<OrdineBean> ordini = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql = "SELECT * FROM " + OrdineDAO.TABLE_NAME + " WHERE dataOrdine = ?";
        
        try {
        	connection = dmcp.getConnection();
	        ps = connection.prepareStatement(sql);
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
        } finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				dmcp.releaseConnection(connection);
			}
		}   
        return ordini;
    }
}

