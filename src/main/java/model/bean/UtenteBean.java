package model.bean;

import java.io.Serializable;

public class UtenteBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String emailUtente;
	private String nome;
	private String cognome;
	private String telefono;
	private String password;
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

	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	@Override
	public String toString() {
		return "UtenteBean [emailUtente=" + emailUtente + ", nome=" + nome + ", cognome=" + cognome + ", telefono="
				+ telefono + ", password=" + password + ", isAdmin=" + isAdmin + "]";
	}


}
