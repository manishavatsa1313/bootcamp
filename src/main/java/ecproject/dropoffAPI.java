package ecproject;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bootcamp.db.mysql.DataSource;

public class dropoffAPI extends HttpServlet {

	private static final long serialVersionUID = 1031422249396784970L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();
		out.print("Hello World from Servlet");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String URI = req.getRequestURI();
		String command = URI.substring(URI.indexOf("/", 19) + 1);
		String key = req.getHeader("Authorization");
		Integer campaignId;
		try {
			if ((campaignId = authorized(key)) != null) {
			} else {
				res.sendError(403);
				return;
			}
		} catch (Exception e) {
			res.sendError(500);
			return;
		}
		if (command.equalsIgnoreCase("create/push")) {
			String engagement_name = req.getParameter("engagement_name");
			String dropoff_settings = req.getParameter("dropoff_settings");
			res.setContentType("text/html;charset=utf-8");
			create(engagement_name, dropoff_settings, campaignId);
			return;
		}

		if (command.equalsIgnoreCase("update/push")) {
			String engagementName = req.getParameter("engagement_name");
			String dropoff_settings = req.getParameter("dropoff_settings");
			res.setContentType("text/html;charset=utf-8");
			update(engagementName, dropoff_settings);
			return;
		}

		if (command.equalsIgnoreCase("delete/push")) {
			String engagement_name = req.getParameter("engagement_name");
			delete(engagement_name);
		}
	}

	private Integer authorized(String key) throws SQLException, IOException, PropertyVetoException {
		Connection con = DataSource.getInstance().getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("SELECT id FROM campaign where authorization_key = \"" + key + "\"");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	private void update(String engagementName, String dropoff_settings) {
		try {
			Connection con = DataSource.getInstance().getConnection();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE engagement set dropoff_settings = " + dropoff_settings
					+ " WHERE engagement_name = " + engagementName);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void delete(String engagementName) {
		try {
			Connection con = DataSource.getInstance().getConnection();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("delete from engagement WHERE engagement_name = " + engagementName);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void create(String engagement_name, String dropoff_settings, Integer campaignId) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bootcamp", "root", "root");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into engagement(engagement_name,dropoff_settings, campaign_id) values("
					+ engagement_name + "," + dropoff_settings + ", " + campaignId + ")");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}