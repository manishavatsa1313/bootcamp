package ecproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class dropoffAPI3 extends HttpServlet {

	private static final long serialVersionUID = 1031422249396784970L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();
		out.print("Hello World from Servlet");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String engagement_name = req.getParameter("engagement_name");
		String dropoff_settings = req.getParameter("dropoff_settings");
		res.setContentType("text/html;charset=utf-8");
		create(engagement_name, dropoff_settings);
	}
	public void create(String engagement_name, String dropoff_settings){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bootcamp", "root", "root");
			Statement stmt = con.createStatement();
		    stmt.executeUpdate("insert into engagement(engagement_name,dropoff_settings) values("+engagement_name+","+dropoff_settings+")");
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}