package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.PhotoControl;

@WebServlet("/getPicture")
public class GetPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetPictureServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{

		DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext().getAttribute("DriverManager");
		PhotoControl photoControl = new PhotoControl(dm);

		String id1 = request.getParameter("id1");
		String id2 = request.getParameter("id2");
	
		if (id1 != null && id2 != null) {
			byte[] bt = photoControl.load(Integer.parseInt(id1), Integer.parseInt(id2));

			ServletOutputStream out = response.getOutputStream();
			if (bt != null) {
				out.write(bt);
				response.setContentType("image/png");
			}
			out.close();
		}
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
