package model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrdineBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int IDOrdine;
	private Date dataOrdine;
	private Date dataConsegna;
	private BigDecimal prezzoTotale;
	private String via, cap, citta, civico;
	private String emailUtente;
	private Boolean isResolved;
	
	public OrdineBean() {
		
	}

	public int getIDOrdine() {
		return IDOrdine;
	}

	public void setIDOrdine(int iDOrdine) {
		IDOrdine = iDOrdine;
	}

	public Date getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public Date getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public BigDecimal getPrezzoTotale() {
		return prezzoTotale;
	}

	public void setPrezzoTotale(BigDecimal prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getEmailUtente() {
		return emailUtente;
	}

	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}
	
	

	public Boolean getIsResolved() {
		return isResolved;
	}

	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}

	@Override
	public String toString() {
		return "OrdineBean [IDOrdine=" + IDOrdine + ", dataOrdine=" + dataOrdine + ", dataConsegna=" + dataConsegna
				+ ", prezzoTotale=" + prezzoTotale + ", via=" + via + ", cap=" + cap + ", citta=" + citta + ", civico="
				+ civico + ", emailUtente=" + emailUtente + ", isResolved=" + isResolved + "]";
	}
	
}
