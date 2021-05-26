package entity;

import java.io.Serializable;

/**Entity class - define user in the CEMS system.
 * @author Hadar Iluz
 *
 */
@SuppressWarnings("serial")
public class User implements Serializable {


	private int id;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int isLogged=0;
	private UserType userType;
	
	public User(int id, String password, String firstName, String lastName, String email, UserType userType) {
		super();
		this.id = id;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userType = userType;
	}
	
	public User(int id, String password) {
		this.id = id;
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
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int isLogged() {
		return isLogged;
	}
	public void setLogged(int isLogged) {
		this.isLogged = isLogged;
	}

	@Override
	public String toString() {
		return "User: " + userType + " isLogged=" + isLogged ;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
//	/*equal method in order to checks if users are equal,
//	and checks if a user already connected to system or not.*/
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) return true;
//		if (obj == null || getClass() != obj.getClass()) return false;
//		User user=(User) obj;
//		//return true if all parameters are equals in order to indicate if this user is already logged into CEMS system..
//		return	id== user.id &&
//				password== user.password &&
//				firstName==user.firstName && 
//				lastName==user.lastName && 
//				email==user.email &&
//				isLogged==1;
//	}

		
}
