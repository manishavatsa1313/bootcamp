package ecproject;

import java.util.List;

public class CampaignData {
	private int campaignId;
	private Integer message_per_day;
	private String api_server_key;
	private Long subsequent_push_interval;
	private List<Notification> notifications;

	public CampaignData() {
	}

	public CampaignData(String analyzeData) {
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public Integer getMessage_per_day() {
		return message_per_day;
	}

	public void setMessage_per_day(Integer message_per_day) {
		this.message_per_day = message_per_day;
	}

	public String getApi_server_key() {
		return api_server_key;
	}

	public void setApi_server_key(String api_server_key) {
		this.api_server_key = api_server_key;
	}

	public Long getSubsequent_push_interval() {
		return subsequent_push_interval;
	}

	public void setSubsequent_push_interval(Long subsequent_push_interval) {
		this.subsequent_push_interval = subsequent_push_interval;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CampaignData [campaignId=").append(campaignId).append(", message_per_day=")
				.append(message_per_day).append(", api_server_key=").append(api_server_key)
				.append(", subsequent_push_interval=").append(subsequent_push_interval).append(", notifications=")
				.append(notifications).append("]");
		return builder.toString();
	}
}