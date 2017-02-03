package ecproject;

public class Notification {
	private Integer id;
	private String enagagementName;
	private String message;
	private String title;
	private Long dropOffInterval;
	private String stage;
	private String deeplink;
	private String imageUrl;

	public Notification() {
	}

	public Notification(Integer id, String enagagementName, String message, String title, Long dropOffInterval,
			String stage, String deeplink, String imageUrl) {
		super();
		this.message = message;
		this.title = title;
		this.dropOffInterval = dropOffInterval;
		this.stage = stage;
		this.deeplink = deeplink;
		this.imageUrl = imageUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnagagementName() {
		return enagagementName;
	}

	public void setEnagagementName(String enagagementName) {
		this.enagagementName = enagagementName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getDropOffInterval() {
		return dropOffInterval;
	}

	public void setDropOffInterval(Long dropOffInterval) {
		this.dropOffInterval = dropOffInterval;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getDeeplink() {
		return deeplink;
	}

	public void setDeeplink(String deeplink) {
		this.deeplink = deeplink;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notification [id=").append(id).append(", enagagementName=").append(enagagementName)
				.append(", message=").append(message).append(", title=").append(title).append(", dropOffInterval=")
				.append(dropOffInterval).append(", stage=").append(stage).append(", deeplink=").append(deeplink)
				.append(", imageUrl=").append(imageUrl).append("]");
		return builder.toString();
	}
}