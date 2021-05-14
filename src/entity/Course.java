package entity;

import java.io.Serializable;

//Entity class - define Course in the CEMS system.
@SuppressWarnings("serial")
public class Course implements Serializable {
	private String courseID;
	private String courseName;
	private String professionID;

	public Course(String courseID, String professionID) {
		super();
		this.courseID = courseID;
		this.professionID = professionID;
	}

	public Course(String courseID, String courseName, String professionID) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.professionID = professionID;
	}

	public String getCourseID() {
		return courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getProfessionID() {
		return professionID;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}