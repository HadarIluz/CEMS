package entity;

import java.io.Serializable;

//Entity class - define Student in the CEMS system.
@SuppressWarnings("serial")
public class Teacher extends User implements Serializable {

	public Teacher(int id, String password, String userName, String email, UserType userType) {
		super(id, password, userName, email, userType);
		// TODO Auto-generated constructor stub
	}
	
}
