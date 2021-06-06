package entity;

import java.io.Serializable;

public class ExamOfStudent implements Serializable {
	
	private ActiveExam activeExam;
	private Student student;
	private int score;
	
	public ExamOfStudent(ActiveExam activeExam,Student student, int score ) {
		super();
		this.activeExam=activeExam;
		this.student=student;
		this.score=score;
	}
	
	public ExamOfStudent(ActiveExam activeExam,Student student) {
		this.activeExam=activeExam;
		this.student=student;
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
	

	public void setActiveExam( ActiveExam activeExam) {
		this.activeExam=activeExam;
	}
	
	public void setStudent(Student student) {
		this.student=student;
		
	}
	
	public void setScore(int score) { 
		this.score=score;

	}
	
	
}
