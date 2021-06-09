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
	//TODO:
	/* We need to set score in ArryList in exam?
	if we do- we need to change two array list from private to protected in Exam class.
	the code:
	int indexOfQuestion=exam.questions.indexOf(question); //find this.question in Array List questions in exam
	exam.questionScores.set(indexOfQuestion,score);*/
	
	}
	
	public void setQuestion(Question question) {
		this.question=question;
	
	}
	
	public void setExam( Exam exam) {
		this.exam=exam;
	}
	
	
	
	
	
	
	
	
	

}
