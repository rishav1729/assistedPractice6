package com.business;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckServlet
 */
@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean found = false;
		boolean invalid = false;
		int productId = 0;
		float price = 0;
		int quantity = 0;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = null;
		try {
			 productId = Integer.parseInt(request.getParameter("productId"));
		}catch(NumberFormatException e){
			rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
			invalid = true;
			out.print("Invalid product ID" + "<br/>");
		}
		String productName = "";
		String sql = "select * from product where productid=?";
	
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productrecord","root","Rishav@!999");
			pst = conn.prepareStatement(sql);
			pst.setString(1, request.getParameter("productId"));
			ResultSet rs = pst.executeQuery();
			
			rs.last();
			if (rs.getRow() > 0) {
				productName = rs.getString(2);
				found = true;
				price = rs.getInt(3);
				found = true;
				quantity = rs.getInt(4);
				found = true;
			}
			if(found) {
				rd = request.getRequestDispatcher("index.html");
				rd.include(request, response);
				out.print("Product Found!" + "<br/>");
				out.print("ProductID:" + productId + "<br/>" + "Product Name: " + productName + "<br/>" 
						+"Price: "+ price +"<br/>"+"Quantity:"+ quantity+"<br/>");
			}else if(invalid == false){
				rd = request.getRequestDispatcher("index.html");
				rd.include(request, response);
				out.print("ProductID:" + productId + " is not found" + "<br/>");
			}
			conn.close();
		}catch(ClassNotFoundException e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch(SQLException e) {
//			try {
//				conn.close();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}	}

}
