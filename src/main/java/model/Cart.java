package model;

import java.util.ArrayList;
import java.util.List;

import model.bean.ProdottoBean;

public class Cart {

	private List<ProdottoBean> prodotti;
	
	public Cart() {
		prodotti = new ArrayList<ProdottoBean>();
	}
	
	public void addProduct(ProdottoBean prodotto) {
		prodotti.add(prodotto);
	}
	
	public void deleteProduct(ProdottoBean prodotto) {
		for(ProdottoBean prod : prodotti) {
			if((prod.getIDProdotto() == prodotto.getIDProdotto())&&(prod.getIDSpecifiche() == prodotto.getIDSpecifiche())) {
				prodotti.remove(prod);
				break;
			}
		}
 	}
	
	public List<ProdottoBean> getProducts() {
		return  prodotti;
	}
}
