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
import model.dao.UtenteDAO;

@WebServlet("/common/AutenticationControl")
public class AutenticationControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public AutenticationControl() {
		super();
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext()
				.getAttribute("DriverManager");
		UtenteDAO utenteDAO = new UtenteDAO(dm);
		String action = request.getParameter("action");

		if (action.equals("login")) {

			String emailUtente = request.getParameter("emailUtente");
			String password = request.getParameter("password");
			List<String> errors = new ArrayList<>();
			RequestDispatcher dispatcherToLoginPage = request.getRequestDispatcher("login.jsp");

			if (emailUtente == null || emailUtente.trim().isEmpty()) {
				errors.add("Il campo email non può essere vuoto!");
			}
			if (password == null || password.trim().isEmpty()) {
				errors.add("Il campo password non può essere vuoto!");
			}
			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				dispatcherToLoginPage.forward(request, response);
				return;
			}

			emailUtente = emailUtente.trim();
			String hashPassword = toHash(password.trim());

			try {
				UtenteBean u = utenteDAO.loginUser(emailUtente, hashPassword);
				HttpSession session = request.getSession();
				session.setAttribute("utente", u);
				System.out.println(u.isAdmin());
				if (u.isAdmin() && u.getPassword() != null) { // admin
					if (u.getPassword().equals(hashPassword)) {
						request.getSession().setAttribute("isAdmin", Boolean.TRUE);
						response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
					}
				} else if (!u.isAdmin() && u.getPassword() != null) { // user
					if (u.getPassword().equals(hashPassword)) {
						request.getSession().setAttribute("isAdmin", Boolean.FALSE);
						response.sendRedirect(request.getContextPath() + "/common/index.jsp");
					}
				}

				else {
					errors.add("Username o password non validi!");
					request.setAttribute("errors", errors);
					dispatcherToLoginPage.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(action.equals("signup")) {
			String emailUtente = request.getParameter("emailUtente");
			String nome = request.getParameter("firstname");
			String cognome = request.getParameter("lastname");
			String telefono = request.getParameter("phone");
			String password = toHash(request.getParameter("password"));
			Boolean isAdmin = Boolean.FALSE;
			
			UtenteBean utente = new UtenteBean();
			utente.setEmailUtente(emailUtente);
			utente.setNome(nome);
			utente.setCognome(cognome);
			utente.setTelefono(telefono);
			utente.setPassword(password);
			utente.setAdmin(isAdmin);
			
			try {
				utenteDAO.registerUser(utente);
				response.sendRedirect(request.getContextPath() + "/common/login.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(action.equals("logout")) {
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + "/common/login.jsp");
		}
		
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