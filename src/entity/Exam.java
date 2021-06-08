package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Exam implements Serializable {

	private String examID;
	private Profession profession;
	private Course course;
	private int timeOfExam;
	private ArrayList<Question> questions;
	private HashMap<String, Integer> questionScores; // mapping the questionID(string) to score(integer)
	private String commentForTeacher;
	private String commentForStudents;
	private Teacher author; 
	private String ProfessionName;

	
	
	

	private ArrayList<QuestionInExam> examQuestionsWithScores;
	private ExamStatus examStatus;
	//private String CourseName; //FIXME: display col in examBankController.
	
	public Exam() {
		super();
	}
	
	public ArrayList<QuestionInExam> getExamQuestionsWithScores() {
		return examQuestionsWithScores;
	}

	public void setExamQuestionsWithScores(ArrayList<QuestionInExam> examQuestionsWithScores) {
		this.examQuestionsWithScores = examQuestionsWithScores;
	}

	public Exam(String examID) {
		this.examID = examID;
	}

	public Exam(String examID, Profession profession, int timeOfExam) {
		this.examID = examID;
		this.profession = profession;
		this.timeOfExam = timeOfExam;
	}

	//FIXME: why we need this? 
	//the constructor includes variable (Course) which and don`t do nothing with them, Can I delete?
	public Exam(Profession profession, Course course, int timeOfExam) {
		this.examID = "";
		this.profession = profession;
		this.timeOfExam = timeOfExam;
		this.course = course;
	}
	
	
	
	public Exam(String examID, Profession profession, Course course, int timeOfExam) {
		super();
		this.examID = examID;
		this.profession = profession;
		this.course = course;
		this.timeOfExam = timeOfExam;
		this.course = course;
	}
	
	public Exam(Course course) {
		this.course = course;
	}

	public String getProfName() {
		return ProfessionName;
	}

	

	public String getProfessionName() {
		return profession.getProfessionID();
	}
	
//	public String getCourseName() {
//		return course.getCourseID();
//	}

	public Exam(String examID, Profession profession, Course course, int timeOfExam, ArrayList<Question> questions,
		HashMap<String, Integer> questionScores, String commentForTeacher, String commentForStudents,
		Teacher author) {
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
	
	public ExamStatus getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(ExamStatus examStatus) {
		this.examStatus = examStatus;
	}

	public String getCourseID() {
		return this.getCourse().getCourseID();
	}



}
