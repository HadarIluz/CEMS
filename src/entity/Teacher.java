package entity;

import java.io.Serializable;
import java.util.ArrayList;

//Entity class - define Student in the CEMS system.
@SuppressWarnings("serial")
public class Teacher extends User implements Serializable {

	private ArrayList<Profession> professions;
	
	public Teacher(int id, String password, String userName, String email, UserType userType) {
		super(id, password, userName, email, userType);
		// TODO Auto-generated constructor stub
		professions = new ArrayList<Profession>();
	}

	public ArrayList<Profession> getProfessions() {
		return professions;
	}

	public void setProfessions(ArrayList<Profession> professions) {
		this.professions = professions;
	}
	
	
	
}
