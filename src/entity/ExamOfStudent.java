package entity;

import java.io.Serializable;
import java.util.HashMap;;

public class ExamOfStudent implements Serializable {
	
	private ActiveExam activeExam;
	private Student student;
	private int score;
	private int totalTime;
	private HashMap<QuestionInExam, Integer> questionsAndAnswers;
	private String examType;
	
	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public ExamOfStudent(ActiveExam exam,Student student, int score ) {
		super();
		this.activeExam=exam;
		this.student=student;
		this.score=score;
	}
	
	public ExamOfStudent(ActiveExam exam,Student student) {
		this.activeExam=exam;
		this.student=student;
	}	
	
	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public HashMap<QuestionInExam, Integer> getQuestionsAndAnswers() {
		return questionsAndAnswers;
	}

	public void setQuestionsAndAnswers(HashMap<QuestionInExam, Integer> questionsAndAnswers) {
		this.questionsAndAnswers = questionsAndAnswers;
	}

	public ActiveExam getActiveExam() {
		return activeExam;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public int getScore() { 
		return score;
	}
	
	public void setActiveExam(ActiveExam exam) {
		this.activeExam=exam;
	}
	
	public void setStudent(Student student) {
		this.student=student;
		
	}
	
	public void setScore(int score) { 
		this.score=score;

	}
	
	
}
