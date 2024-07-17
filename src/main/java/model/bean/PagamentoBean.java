package model.bean;

public class PagamentoBean {
    private int IDPagamento;
    private int IDOrdine;
    private String nomeCarta;
    private String cognomeCarta;
    private String numeroCarta;
    private String scadenzaCarta;
    private String cvv;

    // Costruttore vuoto
    public PagamentoBean() {}

    // Getter e Setter
    public int getIDPagamento() {
        return IDPagamento;
    }

    public void setIDPagamento(int IDPagamento) {
        this.IDPagamento = IDPagamento;
    }

    public int getIDOrdine() {
        return IDOrdine;
    }

    public void setIDOrdine(int IDOrdine) {
        this.IDOrdine = IDOrdine;
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

    public String getScadenzaCarta() {
        return scadenzaCarta;
    }

    public void setScadenzaCarta(String scadenzaCarta) {
        this.scadenzaCarta = scadenzaCarta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
