package controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.SQLException;

import model.bean.*;
import model.dao.*;

@WebServlet("/common/AutenticationControl")
public class AutenticationControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public AutenticationControl() {
		super();
	}
	List<String> errors = new ArrayList<>();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
		UtenteDAO utenteDAO = new UtenteDAO(dm);
		String action = request.getParameter("action");
		errors.clear();
		 
		if(action.equalsIgnoreCase("login")) {
			try {
				handleLogin(request, response, utenteDAO);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(action.equalsIgnoreCase("signup")) {
			//handleSignup(request, response, utenteDAO);
		}
		if(action.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + "/common/login.jsp");
		}

	}	
	
	private void handleLogin(HttpServletRequest request, HttpServletResponse response, UtenteDAO utenteDAO) throws SQLException, IOException, ServletException {
		String emailUtente = request.getParameter("emailUtente");
		String password = request.getParameter("password");

		
		if(isValidLoginForm(emailUtente, password)) {	//se il campi del form sono validi chiamo il metodo loginUser
			String hashPassword = toHash(password.trim());
			UtenteBean u = utenteDAO.loginUser(emailUtente, hashPassword);
			
			if(u != null) {	//se l'utente è riuscito a loggare imposto l'attributo utente nella sessione
				System.out.println("riuscito a loggare");
				request.getSession().setAttribute("utente", u);
				errors.clear();
				if (u.isAdmin()) {	//Se l'utente è un admin setto l'informazione e indirizzo l'utente alla dashboard
					request.getSession().setAttribute("isAdmin", Boolean.TRUE);
					response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
					return;
				} else if (!u.isAdmin() && u.getPassword() != null) { //USER
					request.getSession().setAttribute("isAdmin", Boolean.FALSE);
		 			response.sendRedirect(request.getContextPath() + "/common/index.jsp");
		 			return;
				}	
			} else {	//credenziali non valide
				System.out.println("no valid credenziali");
				errors.add("Email o password non corretti");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("/common/login.jsp").forward(request, response);
			}
		} else {	//campi del form non validi 
			System.out.println("no valid field");
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/common/login.jsp").forward(request, response);
		}
	}
		
	


	private boolean isValidLoginForm(String emailUtente, String password) {
		//CONTROLLO PASSWORD 
		String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{8,}$";
		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		if(password == null || password.trim().isEmpty()) {
			
		}            
		else {
		if(!password.matches(passwordPattern)) {
	        errors.add("La password deve essere lunga almeno 8 caratteri, " +
	                   "contenere almeno una lettera maiuscola, una lettera minuscola, " +
	                   "un numero e un carattere speciale");
	    	}
		}
		
		if (emailUtente == null || emailUtente.trim().isEmpty()) {
	
		} else {
			if (!emailUtente.matches(emailPattern)) {
				errors.add("Il formato della mail non è corretto");
			}
		}
		return errors.isEmpty();
	}


	
	
	private String toHash(String password) {
		String hashString = null;
		try {
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			hashString = "";
			for (int i = 0; i < hash.length; i++) {
				hashString += Integer.toHexString((hash[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3);
			}
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return hashString;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}