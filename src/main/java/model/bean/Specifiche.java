package model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class Specifiche implements Serializable {
	private int IDSpecifiche;
	private String colore;
	private String hdd;
	private int ram;
	private int quantita;
	private BigDecimal prezzo;
	private int numVendite;
	private byte[] image;
	private int IDProdotto;
	
	public Specifiche() {
		
	}

	public int getIDSpecifiche() {
		return IDSpecifiche;
	}

	public void setIDSpecifiche(int iDSpecifiche) {
		IDSpecifiche = iDSpecifiche;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getHdd() {
		return hdd;
	}

	public void setHdd(String hdd) {
		this.hdd = hdd;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

	public int getNumVendite() {
		return numVendite;
	}

	public void setNumVendite(int numVendite) {
		this.numVendite = numVendite;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	public int getIDProdotto() {
		return IDProdotto;
	}

	public void setIDProdotto(int iDProdotto) {
		IDProdotto = iDProdotto;
	}

	@Override
	public String toString() {
		return "Specifiche [IDSpecifiche=" + IDSpecifiche + ", colore=" + colore + ", hdd=" + hdd + ", ram=" + ram
				+ ", quantita=" + quantita + ", prezzo=" + prezzo + ", numVendite=" + numVendite + ", IDProdotto=" + IDProdotto + "]";
	}
	

}
