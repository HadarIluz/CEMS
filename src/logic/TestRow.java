package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TestRow implements Serializable{
	private String examID;
	private String profession;
	private String course;
	private String timeAllotedForTest;
	private String pointsPerQuestion;
	
	public String getExamID() {
		return examID;
	}
	public void setExamID(String examID) {
		this.examID = examID;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getTimeAllotedForTest() {
		return timeAllotedForTest;
	}
	public void setTimeAllotedForTest(String timeAllotedForTest) {
		this.timeAllotedForTest = timeAllotedForTest;
	}
	public String getPointsPerQuestion() {
		return pointsPerQuestion;
	}
	public void setPointsPerQuestion(String pointsPerQuestion) {
		this.pointsPerQuestion = pointsPerQuestion;
	}
	
	public String toString() {
		return getExamID()+" "+getProfession()+" "+getCourse()+" "+getTimeAllotedForTest()+" "+getPointsPerQuestion() + "\n";
	}

}
