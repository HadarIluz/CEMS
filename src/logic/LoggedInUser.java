package logic;

import entity.User;

/**
 * FIXME: ADD MORE JAVADOC TO FUNCTION
 * The class ensures the creation of a single user object in the system using
 * Singleton. In addition ensures that a user will log in at any given time from
 * a single account.
 * 
 * @author Yuval Hayam
 *
 */
public class LoggedInUser {
	private static LoggedInUser instance = null;
	private final User user;
	
	/*constructor*/
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

	public void logOff() {
		instance = null;
	}

}
