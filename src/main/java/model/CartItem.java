package model;

import model.bean.ProdottoBean;
import model.bean.Specifiche;


public class CartItem {
    private ProdottoBean prodotto;
    private Specifiche specifiche;
    private int quantity;

    public CartItem(ProdottoBean prodotto, Specifiche specifica) {
        this.prodotto = prodotto;
        this.specifiche = specifica;
        this.quantity = 1;
    }

    public Specifiche getSpecifiche() {
		return specifiche;
	}

	public void setSpecifiche(Specifiche specifiche) {
		this.specifiche = specifiche;
	}

	public ProdottoBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        this.quantity--;
    }
}
