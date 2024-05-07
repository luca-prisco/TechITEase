package model.bean;

import java.math.BigDecimal;

public class AcquistoBean {
	private int IDAcquisto;
	private String nome;
	private String colore;
	private int hdd;
	private int ram;
	private int quantita;
	private BigDecimal prezzoUnitario;
	private int IDOrdine;
	private int IDProdotto;
	
	public AcquistoBean() {
		
	}

	public int getIDAcquisto() {
		return IDAcquisto;
	}

	public void setIDAcquisto(int iDAcquisto) {
		IDAcquisto = iDAcquisto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public BigDecimal getPrezzoUnitario() {
		return prezzoUnitario;
	}

	public void setPrezzoUnitario(BigDecimal prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}

	public int getIDOrdine() {
		return IDOrdine;
	}

	public void setIDOrdine(int iDOrdine) {
		IDOrdine = iDOrdine;
	}

	public int getIDProdotto() {
		return IDProdotto;
	}

	public void setIDProdotto(int iDProdotto) {
		IDProdotto = iDProdotto;
	}

	@Override
	public String toString() {
		return "AcquistoBean [IDAcquisto=" + IDAcquisto + ", nome=" + nome + ", colore=" + colore + ", hdd=" + hdd
				+ ", ram=" + ram + ", quantita=" + quantita + ", prezzoUnitario=" + prezzoUnitario + ", IDOrdine="
				+ IDOrdine + ", IDProdotto=" + IDProdotto + "]";
	}

}
