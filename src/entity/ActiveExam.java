package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ActiveExam implements Serializable{
	
	private Calendar date; // including start time
	private Exam exam;
	private String examCode;
	
	public ActiveExam() {
	}

	public ActiveExam(Calendar date, Exam exam, String examCode) {
		this.date = date;
		this.exam = exam;
		this.examCode = examCode;
	}
	
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public String getExamCode() {
		return examCode;
	}
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}
	
	// maybe should be moved to controller????
	// this method calculate the end time by doing: start time + time of exam
	public Calendar getEndTime() {
		
		Calendar endTime = date.getInstance();
		int hoursOfExam = exam.getTimeOfExam()/60;
		int minutesOfExam = exam.getTimeOfExam()%60;
		endTime.add(Calendar.HOUR, hoursOfExam);
		endTime.add(Calendar.MINUTE, minutesOfExam);
		
		return endTime;
	}
	

}
