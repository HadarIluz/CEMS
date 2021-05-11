package entity;

import java.io.Serializable;

//Entity class - define Student in the CEMS system.
@SuppressWarnings("serial")
public class Student extends User implements Serializable {

	/* private int id; 
	 * private String password; 
	 * private String StudentName; 
	 * private
	 * String StudentEmail; 
	 * private boolean isLogged;
	 */
	private float studentAvg;
	private int professionID;  //In the story: Math==(02)

	public Student(int id, String password, String userName, String email, UserType userType, float studentAvg,
			int professionID) {
		super(id, password, userName, email, userType);
		this.studentAvg = studentAvg;
		this.professionID = professionID;
	}

	public float getStudentAvg() {
		return studentAvg;
	}

	public void setStudentAvg(float studentAvg) {
		this.studentAvg = studentAvg;
	}

	public int getDepartmentID() {
		return professionID;
	}

	public void setDepartmentID(int professionID) {
		this.professionID = professionID;
	}

	@Override
	public String toString() {
		return "Student [studentID=" + this.getId() + ", studentAvg=" + studentAvg + ", departmentID=" + professionID+ "]";
	}

}
