package entity;

import java.io.Serializable;

public class QuestionInExam implements Serializable {
	
	private int score;
	private Question question;
	private Exam exam;
	
	public QuestionInExam(int score,Question question,Exam exam ) {
		super();
		this.score=score;
		this.question=question;
		this.exam=exam;
	}
	
	//for editExam
	public QuestionInExam(int score,Question question) {
		super();
		this.score=score;
		this.question=question;
	}
	
	public int getScore() { 
		return score;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public Exam getExam() {
		return exam;
	}
	
	public void setScore(int score) { 
		this.score=score;
	}
	
	public void setQuestion(Question question) {
		this.question=question;
	}
	
	public void setExam( Exam exam) {
		this.exam=exam;
	}
	
	
	
	
	
	
	
	
	

}
