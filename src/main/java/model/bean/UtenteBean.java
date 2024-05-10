package model.bean;

import java.io.Serializable;

public class UtenteBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String emailUtente;
	private String nome;
	private String cognome;
	private String telefono;
	private String password;
	private String nomeCarta;
	private String cognomeCarta;
	private String numeroCarta;
	private String dataScadenza;
	private String cvv;
	private boolean isAdmin;
	
	
	public UtenteBean() {
		
	}


	public String getEmailUtente() {
		return emailUtente;
	}


	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNomeCarta() {
		return nomeCarta;
	}


	public void setNomeCarta(String nomeCarta) {
		this.nomeCarta = nomeCarta;
	}


	public String getCognomeCarta() {
		return cognomeCarta;
	}


	public void setCognomeCarta(String cognomeCarta) {
		this.cognomeCarta = cognomeCarta;
	}


	public String getNumeroCarta() {
		return numeroCarta;
	}


	public void setNumeroCarta(String numeroCarta) {
		this.numeroCarta = numeroCarta;
	}


	public String getDataScadenza() {
		return dataScadenza;
	}


	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}


	public String getCvv() {
		return cvv;
	}


	public void setCvv(String cvv) {
		this.cvv = cvv;
	}


	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	@Override
	public String toString() {
		return "UtenteBean [emailUtente=" + emailUtente + ", nome=" + nome + ", cognome=" + cognome + ", telefono="
				+ telefono + ", password=" + password + ", nomeCarta=" + nomeCarta + ", cognomeCarta=" + cognomeCarta
				+ ", numeroCarta=" + numeroCarta + ", dataScadenza=" + dataScadenza + ", cvv=" + cvv + ", isAdmin="
				+ isAdmin + "]";
	}

}
