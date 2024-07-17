package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.UtenteBean;

/**
 * Servlet implementation class AcquistoControl
 */
@WebServlet("/common/Pagamento")
public class Pagamento extends HttpServlet {
	
       
    private static final long serialVersionUID = 77535847075304357L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Pagamento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	    
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

		if(utente == null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/login.jsp");
		    dispatcher.forward(request, response);
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/pagamento.jsp");
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
