package model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProdottoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int IDProdotto;
	private String nomeProdotto;
	private String brand;
	private String categoria;
	private String descrizione;
	private String dettagli;
	private List<Specifiche> specifiche = new ArrayList<>();
	
	public ProdottoBean() {
		
	}

	public int getIDProdotto() {
		return IDProdotto;
	}

	public void setIDProdotto(int iDProdotto) {
		IDProdotto = iDProdotto;
	}

	public String getNomeProdotto() {
		return nomeProdotto;
	}

	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDettagli() {
		return dettagli;
	}

	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}

	public List<Specifiche> getSpecifiche() {
		return specifiche;
	}

	public void setSpecifiche(List<Specifiche> specifiche) {
		this.specifiche = specifiche;
	}

	@Override
	public String toString() {
		return "ProdottoBean [IDProdotto=" + IDProdotto + ", nomeProdotto=" + nomeProdotto + ", brand=" + brand
				+ ", categoria=" + categoria + ", descrizione=" + descrizione + ", dettagli=" + dettagli
				+ ", specifiche=" + specifiche + "]";
	}
}
