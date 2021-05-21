package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//Entity class - define Active Exam in the CEMS system.
public class ActiveExam implements Serializable {

	private Calendar date; // including start time
	private Exam exam;
	private String examCode;
<<<<<<< HEAD
	
	//ASK: why not from logic class? this is entity
	private String status; // msg from server :{"ACTIVE EXAM FOUND" / "ACTIVE EXAM NOT FOUND"}
	private String activeExamType=null; //{manual / computerized}
=======
	private int timeOfExam;
	private String activeExamType = null; //{manual / computerized}

>>>>>>> refs/heads/Approval_Time_Extention_Matar
	
	public ActiveExam(Exam exam) {
		this.exam = exam;
		timeOfExam = exam.getTimeOfExam();
	}

	public ActiveExam(Calendar date, Exam exam, String examCode) {
		this.date = date;
		this.exam = exam;
		this.examCode = examCode;
		timeOfExam = exam.getTimeOfExam();
	}
	
	public ActiveExam(Calendar date, String examCode) {
		this.date = date;
		this.examCode = examCode;
	}
	
	public int getTimeOfExam() {
		return timeOfExam;
	}

	public void setTimeOfExam(String timeOfExam) {
		this.timeOfExam = Integer.parseInt(timeOfExam);
	}
	
	public ActiveExam(Calendar date, String examCode) {
		this.date = date;
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
	
	public String getActiveExamType() {
		return activeExamType;
	}

	public void setActiveExamType(String activeExamType) {
		this.activeExamType = activeExamType;
	}

	// maybe should be moved to controller????
	// this method calculate the end time by doing: start time + time of exam
	public Calendar getEndTime() {
		Calendar endTime = date.getInstance();
		int hoursOfExam = exam.getTimeOfExam() / 60;
		int minutesOfExam = exam.getTimeOfExam() % 60;
		endTime.add(Calendar.HOUR, hoursOfExam);
		endTime.add(Calendar.MINUTE, minutesOfExam);
		return endTime;
	}
	
	public String getActiveExamStartTime() {
		Calendar startTime = date.getInstance();
		int startH= date.HOUR;
		int startM= this.date.MINUTE;
		startTime.add(startH, 0);
		startTime.add(startM, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm"); //For example 12:08 
		String formattedTime = sdf.format(startTime);
		System.out.println(formattedTime);
		return formattedTime;
	}

<<<<<<< HEAD
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getActiveExamType() {
		return activeExamType;
	}
=======
>>>>>>> refs/heads/Approval_Time_Extention_Matar

	public void setActiveExamType(String activeExamType) {
		this.activeExamType = activeExamType;
	}
	
	public String getActiveExamStartTime() {
		Calendar startTime = date.getInstance();
		int startH= this.date.HOUR;
		int startM= this.date.MINUTE;
		startTime.add(startH, 0);
		startTime.add(startM, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm"); //For example 12:08 
		String formattedTime = sdf.format(startTime);
		System.out.println(formattedTime);
		return formattedTime;
	}
	
}
