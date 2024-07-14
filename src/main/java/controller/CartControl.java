package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ProdottoDAO;
import model.Cart;
import model.CartItem;
import model.bean.ProdottoBean;
import model.bean.Specifiche;

/**
 * Servlet implementation class AddToCartControl
 */
@WebServlet("/common/CartControl")
public class CartControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String id1 = request.getParameter("idProdCart");
		String id2 = request.getParameter("idSpecCart");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
		
        if (cart == null) {
            cart = new Cart();
            System.out.println("Carrello creato");
            session.setAttribute("cart", cart);
        }
	
		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
		ProdottoDAO prodottoDAO = new ProdottoDAO(dm);
		try {
			ProdottoBean prodotto = prodottoDAO.doRetrieveById(Integer.parseInt(id1));
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		
		if(action.equals("add")) {
			try {
				ProdottoBean prodotto = prodottoDAO.doRetrieveById(Integer.parseInt(id1));	//Prelevo il prodotto dal db
				Specifiche specifica = prodotto.getSpecifiche().stream()	//stream per filtrare la lista di specifiche del prodotto e trovare quella corrispondente all'ID specificato.
                        .filter(spec -> spec.getIDSpecifiche() == Integer.parseInt(id2))
                        .findFirst()
                        .orElse(null);
				
		        if (prodotto != null && specifica != null) {
		        	ProdottoBean prodSelezionato = new ProdottoBean();
		            prodSelezionato.setIDProdotto(prodotto.getIDProdotto());
		            prodSelezionato.setNomeProdotto(prodotto.getNomeProdotto());
		            prodSelezionato.setBrand(prodotto.getBrand());
		            prodSelezionato.setCategoria(prodotto.getCategoria());
		            prodSelezionato.setDescrizione(prodotto.getDescrizione());
		            prodSelezionato.setDettagli(prodotto.getDettagli());
		            prodSelezionato.getSpecifiche().add(specifica);

		            cart.addProduct(prodSelezionato);

			        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/cart.jsp");
			        dispatcher.forward(request, response);
		        }
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(action.equals("delete")) {
			try {
				String idProdotto = request.getParameter("del1");
				String idSpecifiche = request.getParameter("del2");
				
		        List<CartItem> listaCart = cart.getItems();
		        // Trova l'elemento usando stream
		        CartItem itemToRemove = listaCart.stream()
		                .filter(prod -> prod.getProdotto().getIDProdotto() == Integer.parseInt(idProdotto) && prod.getSpecifiche().getIDSpecifiche() == Integer.parseInt(idSpecifiche))
		                .findFirst()
		                .orElse(null);

		        if (itemToRemove != null) {
		            listaCart.remove(itemToRemove);
		            System.out.println("Item removed: " + itemToRemove);
		        } else {
		            System.out.println("Item not found");
		        }
				
			    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/cart.jsp");
			    dispatcher.forward(request, response);
		       }
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}	
		
		if(action.equals("updateQuantity")) {
			int productId = Integer.parseInt(request.getParameter("productId"));
	        int specId = Integer.parseInt(request.getParameter("specId"));
	        int quantity = Integer.parseInt(request.getParameter("quantity"));

	        if (cart != null) {
	            cart.updateQuantity(productId, specId, quantity);
	        }
		}
        if (action.equals("getTotal")) { 
            if (cart != null) {
                BigDecimal total = cart.getTotale();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(total.toString());
            } 
        }
	}
}
