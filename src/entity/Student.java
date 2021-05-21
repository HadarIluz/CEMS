package entity;

import java.io.Serializable;
import java.util.ArrayList;

//Entity class - define Student in the CEMS system.
@SuppressWarnings("serial")
public class Student extends User implements Serializable {

	private float studentAvg;
	private ArrayList<Course> courses;
	
	public Student(int id, String password, String fullName, String lastName, String email, UserType userType,
			float studentAvg) {
		super(id, password, fullName, lastName, email, userType);
		this.studentAvg = studentAvg;
		courses = new ArrayList<Course>();
	}
	
	//TODO: add constructor that gets User info
	
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
