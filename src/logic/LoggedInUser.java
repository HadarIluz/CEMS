package logic;

import entity.User;

public class LoggedInUser {
	private static LoggedInUser instance = null;
	private final User user;
	
	private LoggedInUser(User user) {
		this.user = user;
	}
	
	public static LoggedInUser getInstance(User user) {
		if (instance == null) {
			instance = new LoggedInUser(user);
		}
		return instance;
	}
	
	public User getUser() {
		if (instance != null) {
			return user;
		}
		return null;
	}
	
}
