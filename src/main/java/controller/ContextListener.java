package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;

/**
 * Servlet implementation class ContextListener
 */
@WebServlet("/ContextListener")
public class ContextListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent event) {
        try {
            // Creazione della connessione al database
            Connection con = ConnectionPool.getConnection();
            
            // Impostazione della connessione come attributo del contesto della servlet
            ServletContext sc = event.getServletContext();
            sc.setAttribute("connection", con);
        } catch (SQLException e) {
            // Gestione degli errori
            e.printStackTrace();
        }
		
	}
	
	public void contextDestroyed(ServletContextEvent event) {
	    ServletContext sc = event.getServletContext();
	    Connection con = (Connection) sc.getAttribute("connection");
	    if (con != null) {
	        try {
	            con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


}
