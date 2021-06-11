package entity;

import java.io.Serializable;
import java.util.HashMap;

//FIXME: add JAVADOC

@SuppressWarnings("serial")
public class ProfessionCourseName implements Serializable {
	
	String ProfessionID;
	HashMap<String,String> courses= new HashMap<String,String>();
	//	courseID,course name
	
	public String getProfessionID() {
		return ProfessionID;
	}
	public void setProfessionID(String professionID) {
		ProfessionID = professionID;
	}
	public HashMap<String, String> getCourses() {
		return courses;
	}
	public void setCourses(HashMap<String, String> courses) {
		this.courses = courses;
	}
	
}
