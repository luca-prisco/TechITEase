package model;

import java.math.BigDecimal;
import java.sql.Blob;

public class ProdottoBean {
	private int IDProdotto;
	private String nomeProdotto;
	private String brand;
	private String categoria;
	private String descrizione;
	private String dettagli;
	private int IDSpecifiche;
	private String colore;
	private int hdd;
	private int ram;
	private int quantita;
	private BigDecimal prezzo;
	private byte[] image;
	
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

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public int getHdd() {
		return hdd;
	}

	public void setHdd(int hdd) {
		this.hdd = hdd;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	
	public int getIDSpecifiche() {
		return IDSpecifiche;
	}

	public void setIDSpecifiche(int iDSpecifiche) {
		IDSpecifiche = iDSpecifiche;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ProdottoBean [IDProdotto=" + IDProdotto + ", nomeProdotto=" + nomeProdotto + ", brand=" + brand
				+ ", categoria=" + categoria + ", descrizione=" + descrizione + ", dettagli=" + dettagli
				+ ", IDSpecifiche=" + IDSpecifiche + ", colore=" + colore + ", hdd=" + hdd + ", ram=" + ram
				+ ", quantita=" + quantita + ", prezzo=" + prezzo + "]";
	}
	
}
