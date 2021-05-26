package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**Entity class - define Student in the CEMS system.
 * @author Hadar Iluz
 *
 */
@SuppressWarnings("serial")
public class Teacher extends User implements Serializable {

	private ArrayList<Profession> professions;
	
	public Teacher(int id, String password, String fullName, String lastName, String email, UserType userType,
			ArrayList<Profession> professions) {
		super(id, password, fullName, lastName, email, userType);
		professions = new ArrayList<Profession>();
	}
	
	public Teacher(User userData, ArrayList<Profession> professions) {
		super(userData.getId(), userData.getPassword(), userData.getFirstName(), userData.getLastName(), userData.getEmail(), userData.getUserType());
		this.professions = professions;
	}

	public ArrayList<Profession> getProfessions() {
		return professions;
	}

	public void setProfessions(ArrayList<Profession> professions) {
		this.professions = professions;
	}
	
	
	
}
