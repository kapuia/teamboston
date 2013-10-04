package hu.boston.tomorrow.model;

public class User {

	private String userId;

	private String userName;

	private String displayName;

	private ImageModel avatar;

	public User() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public ImageModel getAvatar() {
		return avatar;
	}

	public void setAvatar(ImageModel avatar) {
		this.avatar = avatar;
	}
}
