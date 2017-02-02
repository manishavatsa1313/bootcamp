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

public class CampaignAPI extends HttpServlet {

	private static final long serialVersionUID = 1031422249396784970L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();
		out.print("Hello World from Servlet");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bootcamp", "root", "root");
			int msg_per_day = Integer.parseInt(req.getParameter("msg_per_day"));
			String api_server_key = req.getParameter("api_server_key");
			long subsequent_push_interval = Long.parseLong(req.getParameter("subsequent_push_interval"));
			res.setContentType("text/html;charset=utf-8");
			Statement stmt = con.createStatement();
			String authorizationKey = "\"key" + String.valueOf(System.currentTimeMillis()) + "\"";
			String query = "insert into campaign(msg_per_day,api_server_key,subsequent_push_interval ,authorization_key) values("
					+ msg_per_day + "," + api_server_key + "," + subsequent_push_interval + ", " + authorizationKey
					+ ")";
			System.out.println(query);

			stmt.executeUpdate(query);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
