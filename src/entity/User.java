package entity;

import java.io.Serializable;

//Entity class - define user in the CEMS system.
@SuppressWarnings("serial")
public class User implements Serializable {


	private int id;
	private String password;
	private String userName; //for left side screen.(I don't think we need first name and last name)
	private String email;
	private boolean isLogged;
	private String status; //msg from server :{"USER FOUND" / "USER NOT FOUND"}
	private UserType userType;

	
	public User(int id, String password, String userName, String email, UserType userType) {
		super();
		this.id = id;
		this.password = password;
		this.userName = userName;
		this.email = email;
		this.userType = userType;
	}
	
	
	//For Login to CEMS system --> it`s allowed? yoav said only one constructor to each class, but the controller Requires me.. 
	public User() {
		this.userName = userName;
		this.password = password;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String isEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	/*equal method in order to checks if users are equal,
	and checks if a user already connected to system or not.*/
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		//return super.equals(obj);
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		User user=(User) obj;
		//return true if all parameters are equals in order to indicate if this user is already logged into CEMS system..
		return	id== user.id &&
				password== user.password &&
				userName==user.userName && 
				email==user.email &&
				isLogged==true;
	}
	
	//Returns  status of success / failure in connecting to the system
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	//
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getString(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
