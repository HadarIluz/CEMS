package entitiy;

import java.io.Serializable;

//Entity class - define Student in the CEMS system.
@SuppressWarnings("serial")
public class Student extends User implements Serializable {

	/*private int id;
	private String password;
	private String StudentName; 
	private String StudentEmail;
	private boolean isLogged;
	*/
	private float studentAvg;
	private int departmentID;

	public Student(int id, String password, String studentName, String StudentEmail, boolean isLogged,float studentAvg, int departmentID) {
		super(id, password, studentName, StudentEmail, isLogged);
		this.setStudentAvg(studentAvg);
		this.setDepartmentID(departmentID);
	}

	public float getStudentAvg() {
		return studentAvg;
	}

	public void setStudentAvg(float studentAvg) {
		this.studentAvg = studentAvg;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	
	@Override
	public String toString() {
		return "Student [studentID=" + this.getId() + ", studentAvg=" + studentAvg + ", departmentID=" + departmentID + "]";
	}

}
