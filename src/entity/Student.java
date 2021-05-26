package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**Entity class - define Student in the CEMS system.
 * @author Hadar Iluz
 *
 */
@SuppressWarnings("serial")
public class Student extends User implements Serializable {

	private float studentAvg;
	private ArrayList<Course> courses;
	
	public Student(int id, String password, String firstName, String lastName, String email, UserType userType,
			float studentAvg) {
		super(id, password, firstName, lastName, email, userType);
		this.studentAvg = studentAvg;
		courses = new ArrayList<Course>();
	}
	
	public Student(User userData, float studentAvg, ArrayList<Course> courses) {
		super(userData.getId(), userData.getPassword(), userData.getFirstName(), userData.getLastName(), userData.getEmail(), userData.getUserType());
		this.studentAvg = studentAvg;
		this.courses = courses;
	}
	
	public float getStudentAvg() {
		return studentAvg;
	}

	public void setStudentAvg(float studentAvg) {
		this.studentAvg = studentAvg;
	}


	@Override
	public String toString() {
		return "Student [studentID=" + this.getId() + ", studentAvg=" + studentAvg + "]";
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

}
