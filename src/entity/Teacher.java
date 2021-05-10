package entity;

import java.io.Serializable;

//Entity class - define Student in the CEMS system.
@SuppressWarnings("serial")
public class Teacher extends User implements Serializable {

	public Teacher(int id, String password, String userName, String email, boolean isLogged) {
		super(id, password, userName, email, isLogged);
		// TODO Auto-generated constructor stub
	}

}
