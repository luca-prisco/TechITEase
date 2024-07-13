package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.bean.ProdottoSpecificheBean;
import model.dao.ProdottoDAO;

/**
 * Servlet implementation class FiltriCatalogo
 */
@WebServlet("/FiltriCatalogo")
public class FiltriCatalogo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FiltriCatalogo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sort = request.getParameter("sort");
        String categoria = request.getParameter("categoria");
        String brand = request.getParameter("brand");
        String hdd = request.getParameter("hdd");
        ProdottoDAO prodottoDao = new ProdottoDAO((DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager"));
        
        List<ProdottoSpecificheBean> prodotti = (List<ProdottoSpecificheBean>) request.getAttribute("prodotti");
        
        try {
            if(categoria!=null) {
                if (categoria.equals("smartphone")||categoria.equals("notebook")||categoria.equals("tablet")||categoria.equals("smartwatch")) {
                    prodotti = (List<ProdottoSpecificheBean>) prodottoDao.doRetrieveByCategoria(categoria);
                }
            }
            if(brand!=null) {
                if (brand.equals("apple")||brand.equals("samsung")||brand.equals("google")||brand.equals("asus")||brand.equals("hp")||brand.equals("msi")) {
                    prodotti = (List<ProdottoSpecificheBean>) prodottoDao.doRetrieveByBrand(brand);
                }
            }
            if(sort!=null) {
                if(sort.equals("crescente")) {
                	prodotti = (List<ProdottoSpecificheBean>) prodottoDao.ordinaPerPrezzo(true);
                } else if(sort.equals("decrescente")) {
                    prodotti = (List<ProdottoSpecificheBean>) prodottoDao.ordinaPerPrezzo(false);
                }
            } 
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String json = new Gson().toJson(prodotti);
            PrintWriter out = response.getWriter();
            out.write(json);
            out.flush();
            
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
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


