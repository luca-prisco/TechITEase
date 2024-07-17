package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.bean.ProdottoBean;
import model.bean.Specifiche;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addProduct(ProdottoBean prodotto) {
    	Specifiche specificaProdotto = prodotto.getSpecifiche().get(0);
    	
        for (CartItem item : items) {
        	
        	ProdottoBean prodCart = item.getProdotto();
        	List<Specifiche> specificaCart = prodCart.getSpecifiche();
        	
            if (!specificaCart.isEmpty() && specificaProdotto != null) {
	            if (prodCart.getIDProdotto() == prodotto.getIDProdotto() && 
	                specificaCart.get(0).getIDSpecifiche() == specificaProdotto.getIDSpecifiche()) {
	                item.incrementQuantity();
	                return;
	            }
            }
        }
        items.add(new CartItem(prodotto, specificaProdotto));
    }

    public void removeProduct(ProdottoBean prodotto) {
        for (CartItem item : items) {
            if (item.getProdotto().getIDProdotto() == prodotto.getIDProdotto() && 
                item.getProdotto().getSpecifiche().equals(prodotto.getSpecifiche())) {
                item.decrementQuantity();
                if (item.getQuantity() == 0) {
                    items.remove(item);
                }
                return;
            }
        }
    }
    
    public void updateQuantity(int productId, int specId, int quantity) {
        for (CartItem item : items) {
            if (item.getProdotto().getIDProdotto() == productId && item.getSpecifiche().getIDSpecifiche() == specId) {
                item.setQuantity(quantity);
                return;
            }
        }
    }
    
    public BigDecimal getTotale() {
    	BigDecimal totale = new BigDecimal(0);
        for (CartItem item : items) {
            BigDecimal price = item.getSpecifiche().getPrezzo(); // Prezzo come BigDecimal
            BigDecimal quantity = new BigDecimal(item.getQuantity()); // Quantità come BigDecimal
            
            totale = totale.add(price.multiply(quantity));        }
        
        return totale;
    }

    public List<CartItem> getItems() {
        return items;
    }
    
    public void printItems(List<CartItem> items) {
        for (CartItem item : items) {
        	System.out.println(item);
        }
    }
    
    public void clearCart() {
    	items.clear();
    }
}

