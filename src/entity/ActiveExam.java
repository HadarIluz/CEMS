package entity;

import java.io.Serializable;
import java.sql.Time; 
import java.util.Calendar;

//Entity class - define Active Exam in the CEMS system.
@SuppressWarnings("serial")
public class ActiveExam implements Serializable {

	//private Calendar date; // including start time
	private Exam exam;
	private String examCode;
	private int timeOfExam;
	private String activeExamType = null; //{manual / computerized}
	private Time startTime; 	
	
	public ActiveExam(Exam exam) {
		this.exam = exam;
		timeOfExam = exam.getTimeOfExam();
	}

	public ActiveExam(Time startTime, Exam exam, String examCode) {
		this.startTime = startTime;
		this.exam = exam;
		this.examCode = examCode;
		timeOfExam = exam.getTimeOfExam();
	}
	
	public ActiveExam(Time startTime, String examCode) { 
		this.startTime = startTime;
		this.examCode = examCode;
	}
	
	public int getTimeOfExam() {
		return timeOfExam;
	}

	public void setTimeOfExam(String timeOfExam) {
		this.timeOfExam = Integer.parseInt(timeOfExam);
	}

	public Time getTime() {
		return startTime;
	}

	public void setTime(Time startTime) {
		this.startTime = startTime;
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
		Calendar endTime = Calendar.getInstance();
		int hoursOfExam = exam.getTimeOfExam() / 60;
		int minutesOfExam = exam.getTimeOfExam() % 60;
		endTime.add(Calendar.HOUR, hoursOfExam);
		endTime.add(Calendar.MINUTE, minutesOfExam);
		return endTime;
	}
	
	//public String getActiveExamStartTime() {
	//	Calendar startTime = Calendar.getInstance();
	//	int startH= Calendar.HOUR;
	//	int startM= Calendar.MINUTE;
	//	startTime.add(startH, 0);
	//	startTime.add(startM, 0);
	//	SimpleDateFormat sdf = new SimpleDateFormat("h:mm"); //For example 12:08 
	//	String formattedTime = sdf.format(startTime);
	//	System.out.println(formattedTime);
	//	return formattedTime;
	//}
}
