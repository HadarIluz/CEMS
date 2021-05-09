package entitiy;

import java.io.Serializable;

//Entity class - define user in the CEMS system.
@SuppressWarnings("serial")
public class User implements Serializable {
	public User(String id, String password, String userName, boolean isLogged) {
		this.id = id;
		this.password = password;
		this.userName = userName;
		this.isLogged = isLogged;
	}
	private String id;
	private String password;
	private String userName; //for left side screen.
	private boolean isLogged;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		this.userName = username;
	}
	public boolean isLogged() {
		return isLogged;
	}
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
	//to string method to serverLog(ASK: server use it from this class??)...or.. console debugging
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", userName=" + userName + ", isLogged=" + isLogged + "]";
	}

}
