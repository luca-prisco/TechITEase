package controller;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/TechITEase");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}		
		
		DriverManagerConnectionPool dm = null;
		
		dm = new DriverManagerConnectionPool();
		context.setAttribute("DriverManager", dm);
		System.out.println("DriverManager creation...."+dm.toString());		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		
		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) context.getAttribute("DriverManager");
		System.out.println("DriverManager deletion...."+dm.toString());		
	}
}
