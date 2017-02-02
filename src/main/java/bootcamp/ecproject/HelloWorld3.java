package bootcamp.ecproject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld3 extends HttpServlet {

	private static final long serialVersionUID = 1031422249396784970L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();
		out.print("Hello World from Servlet");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String user = req.getParameter("uname");
		String password = req.getParameter("pass");
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();

		pw.println("<h1>" + req.getHeader("content-type") + "</h1>");
		pw.println("<h1> Welcome " + user + "</h1> <br/>");
		pw.println("<h2> Your Password is " + password + "</h2>");
	}
}