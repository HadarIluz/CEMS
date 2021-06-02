package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UpdateScoreRequest implements Serializable {
	
	String ExamID;
	String StudentID;
	int updatedScore;
	String ReasonOfUpdate;
	
	
	public String getExamID() {
		return ExamID;
	}
	public void setExamID(String examID) {
		ExamID = examID;
	}
	public String getStudentID() {
		return StudentID;
	}
	public void setStudentID(String studentID) {
		StudentID = studentID;
	}
	public int getUpdatedScore() {
		return updatedScore;
	}
	public void setUpdatedScore(int updatedScore) {
		this.updatedScore = updatedScore;
	}
	public String getReasonOfUpdate() {
		return ReasonOfUpdate;
	}
	public void setReasonOfUpdate(String reasonOfUpdate) {
		ReasonOfUpdate = reasonOfUpdate;
	}

}
