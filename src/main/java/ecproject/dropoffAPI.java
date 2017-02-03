package ecproject;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		if (command.equalsIgnoreCase("notification/show")) {
			try {
				Connection con = DataSource.getInstance().getConnection();
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery("SELECT id FROM campaign");
				List<CampaignData> camps = new ArrayList<CampaignData>();
				while (rs.next()) {
					int campaign_id = rs.getInt("id");
					CampaignData cd = getnotification(campaign_id);
					camps.add(cd);
					System.out.println(cd);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private CampaignData getnotification(int campaign_id) {
		CampaignData cd = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			cd = new CampaignData();
			Connection con1 = DataSource.getInstance().getConnection();
			Statement statement = con1.createStatement();
			ResultSet rs1 = statement.executeQuery("SELECT * FROM campaign where id =" + campaign_id);
			Connection con2 = DataSource.getInstance().getConnection();
			Statement statement2 = con2.createStatement();
			ResultSet rs2 = statement2.executeQuery("SELECT * FROM engagement where campaign_id =" + campaign_id);
			cd.setCampaignId(campaign_id);
			rs1.next();
			cd.setMessage_per_day(rs1.getInt("msg_per_day"));
			cd.setApi_server_key(rs1.getString("api_server_key"));
			cd.setSubsequent_push_interval(rs1.getLong("subsequent_push_interval"));
			List<Notification> notifs = new ArrayList<Notification>();
			while (rs2.next()) {
				Notification notification = objectMapper.readValue(rs2.getString("dropoff_settings"),
						Notification.class);
				notification.setId(rs2.getInt("id"));
				notification.setEnagagementName(rs2.getString("engagement_name"));
				notifs.add(notification);
			}
			cd.setNotifications(notifs);

		} catch (Exception e) {
			System.out.println(e);
		}
		return cd;
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