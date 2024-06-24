package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ProdottoDAO;
import model.Cart;
import model.bean.ProdottoBean;
import model.bean.Specifiche;

/**
 * Servlet implementation class AddToCartControl
 */
@WebServlet("/common/AddToCart")
public class AddToCartControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartControl() {
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
		System.out.println("Azione: " + action);
		System.out.println("ID Prodotto: " + id1);
		System.out.println("ID Specifica: " + id2);

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
		        	
		            HttpSession session = request.getSession();
		            Cart cart = (Cart) session.getAttribute("cart");

		            if (cart == null) {
		                cart = new Cart();
		                System.out.println("Carrello creato");
		                session.setAttribute("cart", cart);
		            }

		            cart.addProduct(prodSelezionato);

			        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/cart.jsp");
			        dispatcher.forward(request, response);
		        }
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(action.equals("remove")) {
			
		}
	}

}
