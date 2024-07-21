package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.UtenteBean;
import model.dao.ProdottoDAO;
import model.dao.UtenteDAO;
import utils.CryptoUtils;

/**
 * Servlet implementation class UtenteControl
 */
@WebServlet("/UtenteControl")
public class UtenteControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UtenteControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
		UtenteDAO utenteDAO = new UtenteDAO(dm);
		
		if(action!=null) {
			if(action.equals("update")) {
				String nome = request.getParameter("nome");
				String cognome = request.getParameter("cognome");
				String emailUtente = request.getParameter("email");
				String emailOld = request.getParameter("emailOld");
				String telefono = request.getParameter("telefono");
				
				UtenteBean utente = new UtenteBean();
				
				utente.setNome(nome);
				utente.setCognome(cognome);
				utente.setEmailUtente(emailUtente);
				utente.setTelefono(telefono);
				
				try {
					utenteDAO.updateUser(utente, emailOld);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Home");
					dispatcher.forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(action.equals("updateP")) {
				String password = request.getParameter("password");
				String emailUtente = request.getParameter("email");
				
				try {
					utenteDAO.updatePassword(CryptoUtils.toHash(password), emailUtente);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Home");
					dispatcher.forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}



}
