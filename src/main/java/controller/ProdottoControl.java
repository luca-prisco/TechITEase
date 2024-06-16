package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Cart;
import model.bean.*;
import model.dao.*;

/**
 * Servlet implementation class Catalogo
 */
@WebServlet("/ProdottoControl")
public class ProdottoControl extends HttpServlet {
	private static final long serialVersionUID = -2613117693794319659L;

	public ProdottoControl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
		ProdottoDAO prodottoDAO = new ProdottoDAO(dm);

		String action = request.getParameter("action");
		
		try {
			if(action != null) {
				if(action.equalsIgnoreCase("insert")) { 
					int numSpecifiche = 0;
					System.out.println(request.getParameter("numeroSpecifiche"));
					
					if(request.getParameter("numeroSpecifiche")!=null)
						numSpecifiche = Integer.parseInt(request.getParameter("numeroSpecifiche"));

					String nomeProdotto = request.getParameter("nomeProdotto");
					String brand = request.getParameter("brand");
					String categoria = request.getParameter("categoria");
					String descrizione = request.getParameter("descrizione");
					String dettagli = request.getParameter("dettagli");

			        ProdottoBean p = new ProdottoBean();
			        p.setNomeProdotto(nomeProdotto);
			        p.setBrand(brand);
			        p.setCategoria(categoria);
			        p.setDescrizione(descrizione);
			        p.setDettagli(dettagli);
			        
			        String[] colori = request.getParameterValues("colore");
			        String[] hdds = request.getParameterValues("hdd");
			        String[] rams = request.getParameterValues("ram");
			        String[] quantita = request.getParameterValues("quantita");
			        String[] prezzi = request.getParameterValues("prezzo");
 
			        List<Specifiche> specificheList = new ArrayList<>();
			        System.out.println(colori.length);
			        System.out.println(hdds.length);
			        System.out.println(prezzi.length);
			        
			        for(int i=0; i < colori.length; i++) {
						Specifiche specifica = new Specifiche();
						specifica.setColore(colori[i]);
						specifica.setHdd(Integer.parseInt(hdds[i]));
						specifica.setRam(Integer.parseInt(rams[i]));
						specifica.setQuantita(Integer.parseInt(quantita[i]));
						specifica.setPrezzo(new BigDecimal(prezzi[i]));
						specificheList.add(specifica);
			        }

					p.setSpecifiche(specificheList);
					
					prodottoDAO.insertProdotto(p);
					response.sendRedirect(request.getContextPath() + "/admin/gestioneProdotti.jsp");
			        return;
				}
				if(action.equalsIgnoreCase("delete")) {
					String id1 = request.getParameter("del1");
					String id2 = request.getParameter("del2");
					prodottoDAO.deleteProduct(Integer.parseInt(id1), Integer.parseInt(id2));
					response.sendRedirect(request.getContextPath() + "/admin/gestioneProdotti.jsp");
			        return;
				}
				if(action.equalsIgnoreCase("toPage")) {
					String id1 = request.getParameter("id1");
					String id2 = request.getParameter("id2");
					request.setAttribute("id1", id1);
					request.setAttribute("id2", id2);
				    try {
				        ProdottoBean prodotto = prodottoDAO.doRetrieveById(Integer.parseInt(id1));
				        Gson gson = new Gson();
				        String specificheJson = gson.toJson(prodotto.getSpecifiche());
				        request.setAttribute("specificheJson", specificheJson);
				        request.setAttribute("prodotto", prodotto);
				        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/productPage.jsp");
				        dispatcher.forward(request, response);
				    } catch (SQLException e) {
				        e.printStackTrace();
				    }
				    return;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			request.removeAttribute("prodotti");
			request.setAttribute("prodotti", prodottoDAO.doRetrieveAll());
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
		
		if(isAdmin) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/gestioneProdotti.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/index.jsp");
			dispatcher.forward(request, response);			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
