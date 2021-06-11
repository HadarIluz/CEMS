package entity;

import java.io.Serializable;
import java.util.HashMap;;

/**
 * The class and all of its fields are included in our diagrams except for one
 * field which is: reasonOfSubmit, used to keep record of the reason for
 * submission for a student test in the system
 * 
 * @author Hadar Iluz
 *
 */
@SuppressWarnings("serial")
public class ExamOfStudent implements Serializable {

	private ActiveExam activeExam;
	private Student student;
	private int score;
	private int totalTime;
	private HashMap<QuestionInExam, Integer> questionsAndAnswers;
	private String examType;
	private ReasonOfSubmit reasonOfSubmit;
	/* constructor */
	public ExamOfStudent(ActiveExam exam, Student student, int score) {
		super();
		this.activeExam = exam;
		this.student = student;
		this.score = score;
	}
	/* constructor */
	public ExamOfStudent(ActiveExam exam, Student student) {
		this.activeExam = exam;
		this.student = student;
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
		this.activeExam = exam;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ReasonOfSubmit getReasonOfSubmit() {
		return reasonOfSubmit;
	}

	public void setReasonOfSubmit(ReasonOfSubmit reasonOfSubmit) {
		this.reasonOfSubmit = reasonOfSubmit;
	}
	
	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

}
