package ecproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HelloWorld2 extends HttpServlet {
	
	private static final long serialVersionUID = 1031422249396784970L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		out.print("Hello World from Servlet");
		sqlconnect();
		out.flush();
		out.close();
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	 {
	 String user=req.getParameter("uname");
	 String password=req.getParameter("pass");
	 res.setContentType("text/html;charset=utf-8");
	 PrintWriter pw=res.getWriter();
	 pw.println("<h1>"+req.getHeader("content-type")+"</h1>");
	 pw.println("<h1> Welcome "+user+"</h1> <br/>");
	 pw.println("<h2> Your Password is "+password+"</h2>");
	 }
	public void sqlconnect(){
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/bootcamp","root","root");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from test");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2));  
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
}
 