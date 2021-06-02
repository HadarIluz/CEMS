package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Exam implements Serializable{

	private String examID;
	private Profession profession;
	private Course course;
	private int timeOfExam;
	private ArrayList<Question> questions;
	private HashMap<String,Integer> questionScores; // mapping the questionID(string) to score(integer)
	private String commentForTeacher;
	private String commentForStudents;
	private Teacher author; // is this relevant or to delete?
<<<<<<< HEAD
=======
	private String ProfessionName;

>>>>>>> b1dbfdba17a99e995bdc1efabf09813ef1ab7c18
	
	
	public Exam(String examID) {
		super();
		this.examID = examID;
	}
	
	public Exam(String examID, Profession profession,int timeOfExam)
	{
		super();
		this.examID = examID;
		this.profession = profession;
		this.timeOfExam = timeOfExam;
	}
<<<<<<< HEAD
	
	public Exam(Profession profession, Course course, int timeOfExam)
	{
		super();
		this.examID = "";
		this.profession = profession;
		this.timeOfExam = timeOfExam;
	}
=======

		
		public String getProfName() {return ProfessionName; }
	
	  public String getProfessionName() { return profession.getProfessionID(); }
	 
>>>>>>> b1dbfdba17a99e995bdc1efabf09813ef1ab7c18


	public Exam(String examID, Profession profession, Course course, int timeOfExam, ArrayList<Question> questions,
			HashMap<String, Integer> questionScores, String commentForTeacher, String commentForStudents, Teacher author) {
		super();
		this.examID = examID;
		this.profession = profession;
		this.course = course;
		this.timeOfExam = timeOfExam;
		this.questions = questions;
		this.questionScores = questionScores;
		this.commentForTeacher = commentForTeacher;
		this.commentForStudents = commentForStudents;
		this.author = author;
	}



	public Profession getProfession() {
		return profession;
	}



	public void setProfession(Profession profession) {
		this.profession = profession;
	}
	




	public Course getCourse() {
		return course;
	}



	public void setCourse(Course course) {
		this.course = course;
	}



	public int getTimeOfExam() {
		return timeOfExam;
	}



	public void setTimeOfExam(int timeOfExam) {
		this.timeOfExam = timeOfExam;
	}



	public ArrayList<Question> getQuestions() {
		return questions;
	}



	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}



	public HashMap<String, Integer> getQuestionScores() {
		return questionScores;
	}



	public void setQuestionScores(HashMap<String, Integer> questionScores) {
		this.questionScores = questionScores;
	}



	public String getCommentForTeacher() {
		return commentForTeacher;
	}



	public void setCommentForTeacher(String commentForTeacher) {
		this.commentForTeacher = commentForTeacher;
	}



	public String getCommentForStudents() {
		return commentForStudents;
	}



	public void setCommentForStudents(String commentForStudents) {
		this.commentForStudents = commentForStudents;
	}



	public Teacher getAuthor() {
		return author;
	}



	public void setAuthor(Teacher author) {
		this.author = author;
	}



	public String getExamID() {
		return examID;
	}



	public void setExamID(String examID) {
		this.examID = examID;
	}
	
	
	
	
	
}
