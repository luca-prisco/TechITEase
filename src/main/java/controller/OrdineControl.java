package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;
import model.CartItem;
import model.bean.AcquistoBean;
import model.bean.OrdineBean;
import model.bean.PagamentoBean;
import model.bean.UtenteBean;
import model.dao.AcquistoDAO;
import model.dao.OrdineDAO;
import model.dao.ProdottoDAO;
import utils.CryptoUtils;

/**
 * Servlet implementation class OrdineControl
 */
@WebServlet("/OrdineControl")
public class OrdineControl extends HttpServlet {

    private static final long serialVersionUID = -5333396671467561523L;

	public OrdineControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
		OrdineDAO ordineDAO = new OrdineDAO(dm);
		AcquistoDAO acquistoDAO = new AcquistoDAO(dm);
	
		HttpSession session = request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        Cart carrello = (Cart) session.getAttribute("cart");
        String action = request.getParameter("action");
        String risolvi = request.getParameter("risolvi");

        if(action != null) {
	        if(action.equals("add")) {
		        if(utente == null) {
			        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/login.jsp");
			        dispatcher.forward(request, response);
		        }
		
		        if (carrello == null || carrello.getItems().isEmpty()) {
		            response.sendRedirect(request.getContextPath() + "/common/cart.jsp");
		            return;
		        }
		
		        // Recupera i dettagli di pagamento e indirizzo dal form
		        String nomeCarta = request.getParameter("nomeCarta");
		        String cognomeCarta = request.getParameter("cognomeCarta");
		        String numeroCarta = request.getParameter("numeroCarta");
		        String scadenzaCarta = request.getParameter("scadenzaCarta");
		        String cvv = request.getParameter("cvv");
		        String via = request.getParameter("via");
		        String civico = request.getParameter("civico");
		        String cap = request.getParameter("cap");
		        String citta = request.getParameter("citta");
		        String emailUtente = utente.getEmailUtente();
		        List<CartItem> cartItems = carrello.getItems();
		        
		        //Data ordine e data consegnaßß
		        Date dataOrdine = new Date(System.currentTimeMillis());
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(dataOrdine);
		        cal.add(Calendar.DAY_OF_MONTH, 5); // 5 giorni in più per la consegna
		        Date dataConsegna = new Date(cal.getTimeInMillis());
		        
		        // Calcolo il prezzo totale
		        BigDecimal prezzoTotale = BigDecimal.ZERO;
		        for (CartItem item : cartItems) {
		            prezzoTotale = prezzoTotale.add(item.getSpecifiche().getPrezzo().multiply(new BigDecimal(item.getQuantity())));
		        }
		
		        // Creo l'ordine
		        OrdineBean ordine = new OrdineBean();
		        int ordineID = -1;
		        ordine.setDataOrdine(dataOrdine);
		        ordine.setDataConsegna(dataConsegna); 
		        ordine.setPrezzoTotale(prezzoTotale);
		        ordine.setVia(via);
		        ordine.setCivico(civico);
		        ordine.setCap(cap);
		        ordine.setCitta(citta);
		        ordine.setEmailUtente(emailUtente);
		
		        try {
		        	ordineID = ordineDAO.insertOrdine(ordine);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		        
		        //creo il pagamento 
		        PagamentoBean pagamento = new PagamentoBean();
		        pagamento.setIDOrdine(ordineID);
		        pagamento.setNomeCarta(nomeCarta);
		        pagamento.setCognomeCarta(cognomeCarta);
		        pagamento.setNumeroCarta(CryptoUtils.toHash(numeroCarta));
		        pagamento.setScadenzaCarta(scadenzaCarta);
		        pagamento.setCvv(CryptoUtils.toHash(cvv));
		       
		        
		        try {
		        	ordineDAO.insertPagamento(pagamento);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		        
		        // Creazione degli acquisti per ogni elemento nel carrello
		        for (CartItem item : cartItems) {
		            AcquistoBean acquisto = new AcquistoBean();
		            acquisto.setNome(item.getProdotto().getNomeProdotto());
		            acquisto.setBrand(item.getProdotto().getBrand());
		            acquisto.setColore(item.getSpecifiche().getColore());
		            acquisto.setHdd(item.getSpecifiche().getHdd());
		            acquisto.setRam(item.getSpecifiche().getRam());
		            acquisto.setQuantita(item.getQuantity());
		            acquisto.setPrezzoUnitario(item.getSpecifiche().getPrezzo());
		            acquisto.setIDOrdine(ordineID);
		            acquisto.setIDProdotto(item.getProdotto().getIDProdotto());
		
		
		            try {
		                acquistoDAO.insertAcquisto(acquisto);
		                carrello.clearCart();
		                
		                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/ordineEffettuato.jsp");
		                dispatcher.forward(request, response);
		            } catch (SQLException e) {
		                e.printStackTrace();
		                throw new ServletException("Errore durante l'inserimento dell'acquisto nel database");
		            }
		        }
	        }
	        if(action.equals("all")) {
	        	List<OrdineBean> ordini = new ArrayList<>();
	        	try {
					ordini = (List<OrdineBean>) ordineDAO.doRetrieveAll();
					request.setAttribute("ordini", ordini);
	                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/gestioneOrdini.jsp");
	                dispatcher.forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	        if(action.equals("dettagli")) {
	        	String idOrdine = request.getParameter("idOrdine");
	        	try {
					PagamentoBean pagamento = ordineDAO.pagamentoByOrdine(Integer.parseInt(idOrdine));
					List<AcquistoBean> acquisti = (List<AcquistoBean>) acquistoDAO.doRetrieveByIDOrdine(Integer.parseInt(idOrdine));
			        System.out.println("Acquisti trovati: " + acquisti); // Debug: stampa gli acquisti
					request.setAttribute("pagamento", pagamento);
					request.setAttribute("acquisti", acquisti);
	                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/dettagliOrdine.jsp");
	                dispatcher.forward(request, response);
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
			}
			if (action.equals("risolvi")) {
				String idOrdine = request.getParameter("idOrdine");
				try {
					ordineDAO.resolveOrdine(Integer.parseInt(idOrdine));
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/dashboard.jsp");
					dispatcher.forward(request, response);
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
			}
			if(action.equals("filtraData")) {
				String dataX = request.getParameter("dataX");
				String dataY = request.getParameter("dataY");
				
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		           java.sql.Date sqlDateX = null;
		           java.sql.Date sqlDateY = null;
		        
		        try {
					sqlDateX = new java.sql.Date(sdf.parse(dataX).getTime());
					sqlDateY = new java.sql.Date(sdf.parse(dataY).getTime());
		        	List<OrdineBean> ordini = (List<OrdineBean>) ordineDAO.filterByData(sqlDateX, sqlDateY);
		        	request.setAttribute("ordini", ordini);
	                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/gestioneOrdini.jsp");
	                dispatcher.forward(request, response);
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			if(action.equals("filtraUtente")) {
				String emailUtente = request.getParameter("emailUtente");
				
				try {
					List<OrdineBean> ordini = (List<OrdineBean>) ordineDAO.filterByEmail(emailUtente);
		        	request.setAttribute("ordini", ordini);
	                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/gestioneOrdini.jsp");
	                dispatcher.forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

        
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}



}
