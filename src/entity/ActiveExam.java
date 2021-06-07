package entity;

import java.io.Serializable;
import java.sql.Time;

//Entity class - define Active Exam in the CEMS system.
@SuppressWarnings("serial")
public class ActiveExam implements Serializable {

	//private Calendar date; // including start time
	private Exam exam;
	private String examCode;
	private int timeAllotedForTest;
	private String activeExamType; //{manual / computerized}
	private Time startTime; 	
	private Time endTimeToTakeExam;

	public ActiveExam(Exam exam) {
		this.exam = exam;
	}

	public ActiveExam(Time startTime, Exam exam, String examCode) {
		this.startTime = startTime;
		this.exam = exam;
		this.examCode = examCode;
	}
	
	public ActiveExam(Time startTime, Exam exam, String examCode, String activeExamType, int timeAllotedForTest) {
		this.startTime = startTime;
		this.exam = exam;
		this.examCode = examCode;
		this.activeExamType=activeExamType;
		this.timeAllotedForTest= timeAllotedForTest;
	}

	public ActiveExam(Time time, Time endTimeToTakeExam, String examCode) {
		this.startTime = time;
		this.endTimeToTakeExam = endTimeToTakeExam;
		this.examCode = examCode;
	}

	public ActiveExam(Exam exam, Time startTime) {
		this.startTime = startTime;
		this.exam = exam;
	}

	public int getTimeAllotedForTest() {
		return timeAllotedForTest;
	}

	public void setTimeAllotedForTest(String timeOfExam) {
		this.timeAllotedForTest = Integer.parseInt(timeOfExam);
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
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

//	// maybe should be moved to controller????
//	// this method calculate the end time by doing: start time + time of exam
//	public Calendar getEndTime() {
//		Calendar endTime = date.getInstance();
//		int hoursOfExam = exam.getTimeOfExam() / 60;
//		int minutesOfExam = exam.getTimeOfExam() % 60;
//		endTime.add(Calendar.HOUR, hoursOfExam);
//		endTime.add(Calendar.MINUTE, minutesOfExam);
//		return endTime;
//	}
	
	public Time getEndTimeToTakeExam() {
		return endTimeToTakeExam;
	}

	public void setEndTimeToTakeExam(Time endTimeToTakeExam) {
		this.endTimeToTakeExam = endTimeToTakeExam;
	}
}
