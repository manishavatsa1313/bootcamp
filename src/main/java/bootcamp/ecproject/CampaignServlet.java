package bootcamp.ecproject;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bootcamp.db.mysql.DataSource;

public class CampaignServlet extends HttpServlet {

	private static final long serialVersionUID = 1031422249396784970L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject = new JSONObject(req.getParameter("json"));
		Iterator<String> it = jsonObject.keys();
		String query = "INSERT INTO campaign(msg_per_day, api_server_key, subsequent_push_interval) VALUES (?, ?, ?)";
		DataSource ds = null;
		try {
			ds = DataSource.getInstance();
			Connection con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			while (it.hasNext()) {
				String key = it.next();
				ps.setString(i++, jsonObject.get(key).toString());
			}
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String user = req.getParameter("uname");
		String password = req.getParameter("pass");
		res.setContentType("text/html;charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.println("<h1>" + req.getHeader("content-type") + "</h1>");
		pw.println("<h1> Welcome " + user + "</h1> <br/>");
		pw.println("<h2> Your Password is " + password + "</h2>");
	}

	private void sqlconnect() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bootcamp", "root", "root");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from test");
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
