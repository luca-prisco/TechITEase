package model.bean;

public class ProdottoSpecificheBean {
    private ProdottoBean prodotto;
    private SpecificheRidotte specRidotte;

    public ProdottoBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }

    public SpecificheRidotte getSpecRidotte() {
        return specRidotte;
    }

    public void setSpecRidotte(SpecificheRidotte specRidotte) {
        this.specRidotte = specRidotte;
    }
}