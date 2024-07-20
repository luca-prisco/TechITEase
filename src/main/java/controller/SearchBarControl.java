package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.bean.ProdottoBean;
import model.bean.ProdottoSpecificheBean;
import model.dao.ProdottoDAO;

/**
 * Servlet implementation class searchBarControl
 */
@WebServlet("/common/SearchBarControl")
public class SearchBarControl extends HttpServlet {
     
	private static final long serialVersionUID = 7966620842298809431L;

    public SearchBarControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
		ProdottoDAO prodottoDAO = new ProdottoDAO(dm);
		String action = request.getParameter("action"); 
		
		if(action != null) {
			if(action.equalsIgnoreCase("search")) {
				String query = request.getParameter("query");
				 List<ProdottoSpecificheBean> prodotti = new ArrayList<>();
		            try {
		                prodotti = (List<ProdottoSpecificheBean>) prodottoDAO.searchProdotti(query);
		                System.out.println("Prodotti trovati: " + prodotti); // Debug
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
	
		            // Imposta il tipo di contenuto della risposta
		            response.setContentType("application/json");
		            response.setCharacterEncoding("UTF-8");
	
		            String json = new Gson().toJson(prodotti);
		            PrintWriter out = response.getWriter();
		            out.write(json);
		            out.flush();
		        }
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
