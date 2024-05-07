package model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.bean.AcquistoBean;

public class AcquistoDAO {

	public void insertAcquisto(AcquistoBean acquisto, Connection con) throws SQLException {
		PreparedStatement ps = null;

		String sql = "INSERT INTO Acquisto (nome, colore, hdd, ram, quantita, prezzoUnitario, IDOrdine, IDProdotto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		ps = con.prepareStatement(sql);
		ps.setString(1, acquisto.getNome());
		ps.setString(2, acquisto.getColore());
		ps.setInt(3, acquisto.getHdd());
		ps.setInt(4, acquisto.getRam());
		ps.setInt(5, acquisto.getQuantita());
		ps.setBigDecimal(6, acquisto.getPrezzoUnitario());
		ps.setInt(7, acquisto.getIDOrdine());
		ps.setInt(8, acquisto.getIDProdotto());

		ps.executeUpdate();

	}

	public void updateAcquisto(AcquistoBean acquisto, Connection con) throws SQLException {
		PreparedStatement ps = null;

		String sql = "UPDATE Acquisto SET nome = ?, colore = ?, hdd = ?, ram = ?, quantita = ?, prezzoUnitario = ?, IDOrdine = ?, IDProdotto = ? WHERE IDAcquisto = ?";

		ps = con.prepareStatement(sql);
		ps.setString(1, acquisto.getNome());
		ps.setString(2, acquisto.getColore());
		ps.setInt(3, acquisto.getHdd());
		ps.setInt(4, acquisto.getRam());
		ps.setInt(5, acquisto.getQuantita());
		ps.setBigDecimal(6, acquisto.getPrezzoUnitario());
		ps.setInt(7, acquisto.getIDOrdine());
		ps.setInt(8, acquisto.getIDProdotto());
		ps.setInt(9, acquisto.getIDAcquisto());

		ps.executeUpdate();

	}

	public Collection<AcquistoBean> doRetrieveByIDOrdine(int IDOrdine, Connection con) throws SQLException {
		Collection<AcquistoBean> acquisti = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Acquisto WHERE IDOrdine = ?";

		ps = con.prepareStatement(sql);
		ps.setInt(1, IDOrdine);
		rs = ps.executeQuery();

		while (rs.next()) {
			AcquistoBean acquisto = new AcquistoBean();
			acquisto.setIDAcquisto(rs.getInt("IDAcquisto"));
			acquisto.setNome(rs.getString("nome"));
			acquisto.setColore(rs.getString("colore"));
			acquisto.setHdd(rs.getInt("hdd"));
			acquisto.setRam(rs.getInt("ram"));
			acquisto.setQuantita(rs.getInt("quantita"));
			acquisto.setPrezzoUnitario(rs.getBigDecimal("prezzoUnitario"));
			acquisto.setIDOrdine(IDOrdine);
			acquisto.setIDProdotto(rs.getInt("IDProdotto"));

			acquisti.add(acquisto);
		}

		return acquisti;
	}

	public Collection<AcquistoBean> doRetrieveByIDProdotto(int IDProdotto, Connection con) throws SQLException {
		Collection<AcquistoBean> acquisti = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Acquisto WHERE IDProdotto = ?";

		ps = con.prepareStatement(sql);
		ps.setInt(1, IDProdotto);
		rs = ps.executeQuery();

		while (rs.next()) {
			AcquistoBean acquisto = new AcquistoBean();
			acquisto.setIDAcquisto(rs.getInt("IDAcquisto"));
			acquisto.setNome(rs.getString("nome"));
			acquisto.setColore(rs.getString("colore"));
			acquisto.setHdd(rs.getInt("hdd"));
			acquisto.setRam(rs.getInt("ram"));
			acquisto.setQuantita(rs.getInt("quantita"));
			acquisto.setPrezzoUnitario(rs.getBigDecimal("prezzoUnitario"));
			acquisto.setIDOrdine(rs.getInt("IDOrdine"));
			acquisto.setIDProdotto(IDProdotto);

			acquisti.add(acquisto);
		}

		return acquisti;
	}
}
