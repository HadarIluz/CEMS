package entity;

import java.io.Serializable;

//Entity class - define Course in the CEMS system.
@SuppressWarnings("serial")
public class Course implements Serializable {
	private String courseID;
	private String courseName;
	private Profession profession;

	public Course(String courseID, Profession profession) {
		super();
		this.courseID = courseID;
		this.profession = profession;
	}

	public Course(String courseID, String courseName, Profession profession) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.profession = profession;
	}

	public String getCourseID() {
		return courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public Profession getProfessionID() {
		return profession;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}