package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;

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
	
	List<String> errors = new ArrayList<>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
		ProdottoDAO prodottoDAO = new ProdottoDAO(dm);

		String action = request.getParameter("action");
		
		try {
			if(action != null) {
				if(action.equalsIgnoreCase("insert")) { 
					int numSpecifiche = 0;
					ProdottoBean p = new ProdottoBean();
					
					if(request.getParameter("numeroSpecifiche")!=null)
						numSpecifiche = Integer.parseInt(request.getParameter("numeroSpecifiche"));
					
					
					String nomeProdotto = request.getParameter("nomeProdotto");
					String brand = request.getParameter("brand");
					String categoria = request.getParameter("categoria");
					String descrizione = request.getParameter("descrizione");
					String dettagli = request.getParameter("dettagli");
			        
			        String[] colori = request.getParameterValues("colore");
			        String[] hdds = request.getParameterValues("hdd");
			        String[] rams = request.getParameterValues("ram");
			        String[] quantita = request.getParameterValues("quantita");
			        String[] prezzi = request.getParameterValues("prezzo");
 
					
				    errors.clear();  

			      
					if (!isValidProdottoForm(nomeProdotto, brand, categoria, descrizione, dettagli, colori[0], hdds[0],
							rams[0], quantita[0], prezzi[0])) {
						request.setAttribute("errors", errors);
			            request.getRequestDispatcher("/admin/aggiungiProdotto.jsp").forward(request, response);
			            return;
					}
			     
							
				    p.setNomeProdotto(nomeProdotto);
				    p.setBrand(brand);
				    p.setCategoria(categoria);
				    p.setDescrizione(descrizione);
				    p.setDettagli(dettagli);
					

			        List<Specifiche> specificheList = new ArrayList<>();
			        
			        for(int i=0; i < colori.length; i++) {
							Specifiche specifica = new Specifiche();
							specifica.setColore(colori[i]);
							specifica.setHdd(hdds[i]);
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
				if(action.equalsIgnoreCase("update")) {
					String IDProdotto = request.getParameter("idProdotto");
					String IDSpecifiche = request.getParameter("idSpecifiche");
					String nomeProdotto = request.getParameter("nomeProdotto");
					String brand = request.getParameter("brand");
					String categoria = request.getParameter("categoria");
					String descrizione = request.getParameter("descrizione");
					String dettagli = request.getParameter("dettagli");
					String colore = request.getParameter("colore");
					String hdd = request.getParameter("hdd");
					String ram = request.getParameter("ram");
					String quantita = request.getParameter("quantita");
					String prezzo = request.getParameter("prezzo");

				    errors.clear();  
 
					if (!isValidProdottoForm(nomeProdotto, brand, categoria, descrizione, dettagli, colore, hdd,
							ram, quantita, prezzo)) {
						request.setAttribute("errors", errors);
			            request.getRequestDispatcher("/admin/modificaProdotto.jsp").forward(request, response);
			            return;
					}
					
					ProdottoBean prodotto = new ProdottoBean();
					List<SpecificheRidotte> specifiche = new ArrayList<>();
					SpecificheRidotte specifica = new SpecificheRidotte();
					prodotto.setIDProdotto(Integer.parseInt(IDProdotto));
					prodotto.setNomeProdotto(nomeProdotto);
					prodotto.setBrand(brand);
					prodotto.setCategoria(categoria);
					prodotto.setDescrizione(descrizione);
					prodotto.setDettagli(dettagli);
					specifica.setIDSpecifiche(Integer.parseInt(IDSpecifiche));
					specifica.setColore(colore);
					specifica.setHdd(hdd);
					specifica.setRam(Integer.parseInt(ram));
					specifica.setQuantita(Integer.parseInt(quantita));
					specifica.setPrezzo(new BigDecimal(prezzo));
					specifiche.add(specifica);
					prodotto.setSpecificheRidotte(specifiche);
					
					try {
					prodottoDAO.updateProdotto(prodotto);
					response.sendRedirect(request.getContextPath() + "/admin/gestioneProdotti.jsp");
					} catch (SQLException e)  {
						e.printStackTrace();
					}
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
				        	
				        Set<String> colorsSet = new HashSet<>();
				        Set<Integer> ramsSet = new HashSet<>();
				        Set<String> hddsSet = new HashSet<>();
				        for(Specifiche s : prodotto.getSpecifiche()) {
				        	colorsSet.add(s.getColore());
				        	ramsSet.add(s.getRam());
				        	hddsSet.add(s.getHdd());
				        }
				  
				        List<SpecificheRidotte> specificheRid = prodottoDAO.convertToSpecificheRidotte(prodotto.getSpecifiche());
				        String specificheJson = gson.toJson(specificheRid);
 				        request.setAttribute("colorsSet", colorsSet);
 				        request.setAttribute("ramsSet", ramsSet);
 				        request.setAttribute("hddsSet", hddsSet);

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

		Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
		
		if(isAdmin) {
			try {
				List<ProdottoBean> prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveAll();
				request.setAttribute("prodotti", prodotti);
				System.out.println("Prodotti trovati: " + prodotti.size());
				for (ProdottoBean prodotto : prodotti) {
				    System.out.println(prodotto.getNomeProdotto());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/gestioneProdotti.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/index.jsp");
			dispatcher.forward(request, response);			
		}
		
	}
	
	
	private boolean isValidProdottoForm(String nomeProdotto, String brand, String categoria, String descrizione, String dettagli, String colore, String hdd, String ram, String quantita, String prezzo) {
		
		String nomeProdottoPattern = "^[a-zA-Z\\s]{2,}$";
		String brandPattern = "^[a-zA-Z\\s]{2,}$";
		String categoriaPattern = "^[a-zA-Z\\s]+$";
		String descrizionePattern = "^.{1,70}$";
		String dettagliPattern = "^.{1,45}$";
		String colorePattern = "^[a-zA-Z\\s]{3,}$";
		String hddPattern = "^\\d{1,4}$";
		String ramPattern = "^\\d{1,4}$";
		String quantitaPattern = "^\\d+$";
		String prezzoPattern = "^\\d+(\\.\\d{1,2})?$";

		
		if(nomeProdotto == null || nomeProdotto.trim().isEmpty()) 
			errors.add("Il nome del prodotto è obbligatoria");
		else if(!nomeProdotto.matches(nomeProdottoPattern)) 
		        errors.add("Formato del nome prodotto non valido");

		if (brand == null || brand.trim().isEmpty()) 
			errors.add("Il nome del brand è obbligatoria");
		else if (!brand.matches(brandPattern)) 
				errors.add("Il formato del brand non è valido");
		
		if (categoria == null || categoria.trim().isEmpty()) 
			errors.add("La categoria è obbligatorio");
		else if (!categoria.matches(categoriaPattern)) 
				errors.add("Il formato della categoria non è valido");
		
		if (descrizione == null || descrizione.trim().isEmpty()) 
			errors.add("La descrizione è obbligatoria");
		else if (!descrizione.matches(descrizionePattern)) 
				errors.add("Il formato della descrizione non è valido");
		
		if (dettagli == null || dettagli.trim().isEmpty()) 
			errors.add("I dettagli sono obbligatori");
		else if (!dettagli.matches(dettagliPattern)) 
				errors.add("Il formato dei dettagli non è valido");

		if (colore == null || colore.trim().isEmpty()) 
			errors.add("Il colore è obbligatorio");
		else if (!colore.matches(colorePattern)) 
				errors.add("Il formato del colore non è valido");
		
		if (hdd == null || hdd.trim().isEmpty()) 
			errors.add("L'hdd è obbligatorio");
		else if (!hdd.matches(hddPattern)) 
				errors.add("Il formato dell'hdd non è valido");
		
		if (ram == null || ram.trim().isEmpty()) 
			errors.add("La ram è obbligatoria");
		else if (!ram.matches(ramPattern)) 
				errors.add("Il formato della ram non è valido");
		else {
		    try {
		        int ramValue = Integer.parseInt(ram);
		        if (ramValue < 0 || ramValue > 9999) 
		            errors.add("Il valore della ram deve essere tra 0 e 9999");
		    } catch (NumberFormatException e) {
		        errors.add("Il formato della ram non è valido");
		    }
		}

		if (quantita == null || quantita.trim().isEmpty()) 
		    errors.add("La quantità è obbligatoria");
		else if (!quantita.matches(quantitaPattern)) 
		    errors.add("Il formato della quantità non è valido");
		else {
		    try {
		        int quantitaValue = Integer.parseInt(quantita);
		        if (quantitaValue < 0) 
		            errors.add("La quantità deve essere un numero positivo");
		    } catch (NumberFormatException e) {
		        errors.add("Il formato della quantità non è valido");
		    }
		}
		
		if (prezzo == null || prezzo.trim().isEmpty()) 
			errors.add("Il prezzo è obbligatorio");
		else if (!prezzo.matches(prezzoPattern)) 
				errors.add("Il formato del prezzo non è valido");
		
		return errors.isEmpty();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
