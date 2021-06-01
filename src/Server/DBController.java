package Server;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import entity.ActiveExam;
import entity.Course;
import entity.Exam;
import entity.ExtensionRequest;
import entity.Profession;
import entity.Question;
import entity.QuestionRow;
import entity.Student;
import entity.Teacher;
import entity.TestRow;
import entity.User;
import entity.UserType;
import gui_server.ServerFrameController;
import logic.ResponseFromServer;

/**
 * @author CEMS_Team
 *
 */
public class DBController {
	public Connection conn;
	public ServerFrameController serverFrame;

	public void connectDB(ServerFrameController serverFrame) {
		try {
			this.serverFrame = serverFrame;
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			serverFrame.printToTextArea("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			serverFrame.printToTextArea("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cems?serverTimezone=IST", "root",
					"Aa123456");
			serverFrame.printToTextArea("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			serverFrame.printToTextArea("SQLState: " + ex.getSQLState());
			serverFrame.printToTextArea("VendorError: " + ex.getErrorCode());
		}
	}

	/*checks if the user that try to login exists in the DB.
	 * @param obj of user which include student id to verify if exists.
	 */
	public ResponseFromServer verifyLoginUser(User obj) {

		User existUser = obj;
		ResponseFromServer response = null;
		existUser.setPassword(null); //put null in order to check at the end if user found or not by this id.
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			pstmt.setInt(1, existUser.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existUser.setPassword(rs.getString(2));
				existUser.setFirstName(rs.getString(3));
				existUser.setLastName(rs.getString(4));
				existUser.setEmail(rs.getString(5));
				existUser.setUserType(UserType.valueOf(rs.getString(6)));
				rs.close();
			}

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		//in case not found any user match ..
		if (existUser.getPassword() ==null) {
			response = new ResponseFromServer("USER NOT FOUND");
		}
		else{
			response = new ResponseFromServer("USER FOUND");	
		}
		// ResponseFromServer class ready to client with StatusMsg and  
		//'Object responseData', in case user found existUser include all data, otherwise null.
		response.setResponseData(existUser);
		return response;
	}
	
	
	/**We know that this student exists and we bring the additional data he has in addition to the user.
	 * @param student include all data of student that hold by User object.
	 */
	public Student getStudentData_Logged(Student student) {
		
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT AVG FROM student WHERE id=?;");
			pstmt.setInt(1, student.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				student.setStudentAvg(rs.getFloat(1));
				rs.close();
			}

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}

		return student;
		
	}

	/**
	 * @param student include all data of this logged student.
	 */
	public Student getStudentCourses_Logged(Student student) {
		
		ArrayList<Course> coursesOfStudent = new ArrayList<Course>();
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM student_in_course WHERE student=?;");
			pstmt.setInt(1, student.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course newCourse = new Course(rs.getString(2));
				coursesOfStudent.add(newCourse); //add to list.
			}
			rs.close();

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		student.setCourses(coursesOfStudent);
		return student;		
	}
		


	/**
	 * @param teacherObj include all data of this logged teacher.
	 */
	public ArrayList<String> getTeacherProfessionIDs(Teacher teacher) {
		
		ArrayList<String> professionIDs = new ArrayList<String>();

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT profession FROM teacher_in_profession WHERE teacher=?;");
			pstmt.setInt(1, teacher.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				professionIDs.add(rs.getString(1)); //add to list
			}
			rs.close();

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return professionIDs;
	}



	public HashMap<String, Integer> SetDetailsForScoreApprovel(String examID) {
		HashMap<String, Integer> stdScore = new HashMap<>();

		PreparedStatement pstmt;
		try {

			pstmt = conn.prepareStatement("SELECT * FROM students_score  WHERE examID=?");
			pstmt.setString(1, examID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				stdScore.put(rs.getString(2), rs.getInt(3));
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return stdScore;

	}

	/**
	 * @param question inserts new question to DB
	 * @return
	 */
	public boolean createNewQuestion(Question question) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("INSERT INTO question VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			pstmt.setInt(1, question.getTeacher().getId());
			pstmt.setString(2, question.getQuestionID());
			pstmt.setString(3, question.getProfession().getProfessionID());
			pstmt.setString(4, question.getQuestion());
			pstmt.setString(5, question.getAnswers()[0]);
			pstmt.setString(6, question.getAnswers()[1]);
			pstmt.setString(7, question.getAnswers()[2]);
			pstmt.setString(8, question.getAnswers()[3]);
			pstmt.setInt(9, question.getCorrectAnswerIndex());
			pstmt.setString(10, question.getDescription());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
			// to do something with status
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * @param professionID
	 * @return int, the number of questions in this profession
	 */
	public int getNumOfQuestionsInProfession(String professionID) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("SELECT SUM(profession=?) as sum FROM question;");
			pstmt.setString(1, professionID);
			ResultSet rs = pstmt.executeQuery();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @param courseID
	 * @return the number of exams with this courseID
	 */
	public int getNumOfExamsInCourse(String courseID) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("SELECT SUM(course=?) as sum FROM exam;");
			pstmt.setString(1, courseID);
			ResultSet rs = pstmt.executeQuery();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @param exam
	 * @return true/false if creating a new exam in DB succeeded
	 */
	public boolean createNewExam(Exam exam) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("INSERT INTO exam VALUES(?, ?, ?, ?, ?, ?, ?);");
			pstmt.setString(1, exam.getExamID());
			pstmt.setString(2, exam.getProfession().getProfessionID());
			pstmt.setString(3, exam.getCourse().getCourseID());
			pstmt.setInt(4, exam.getTimeOfExam());
			pstmt.setString(5, exam.getCommentForTeacher());
			pstmt.setString(6, exam.getCommentForStudents());
			pstmt.setInt(7, exam.getAuthor().getId());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

	/**
	 * @param examID
	 * @param questionScores
	 * @return true/false if inserting all questions and scores of exam with examID
	 *         into table question_in_exam succeeded
	 */
	public boolean addQuestionsInExam(String examID, HashMap<String, Integer> questionScores) {
		PreparedStatement pstmt;
		try {
			for (String questionID : questionScores.keySet()) {
				pstmt = conn.prepareStatement("INSERT INTO question_in_exam VALUES(?, ?, ?);");
				pstmt.setString(1, questionID);
				pstmt.setInt(2, questionScores.get(questionID));
				pstmt.setString(3, examID);
				if (pstmt.executeUpdate() != 1) {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * check if the activeExam exist in the DB
	 * @param obj of ActiveExam which include exam to verify if exists.
	 */
	public ResponseFromServer verifyActiveExam(Object obj) {
		ActiveExam existActiveExam = (ActiveExam) obj;
		ResponseFromServer response = null;
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM active_exam WHERE exam=?");
			pstmt.setString(1, existActiveExam.getExam().getExamID());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existActiveExam.setTime(rs.getTime(2));
				existActiveExam.setTimeOfExam(rs.getString(3));
				existActiveExam.setExamCode(rs.getString(4));
				rs.close();
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		// in case not found any active exam match.
		if (existActiveExam.getExamCode() == null) {
			response = new ResponseFromServer("ACTIVE EXAM NOT FOUND");
		} else
			response = new ResponseFromServer("ACTIVE EXAM FOUND");
		// ResponseFromServer class ready to client with StatusMsg and
		// 'Object responseData', in case active exam found existActiveExam include all
		// data, other null.
		response.setResponseData(existActiveExam);
		return response;	
	}

	/**
	 * 
	 * @param extensionRequest
	 * @return true if creating a new extension request in DB succeeded, else return
	 *         false
	 */
	public ResponseFromServer createNewExtensionRequest(ExtensionRequest extensionRequest) {
		PreparedStatement pstmt;
		ResponseFromServer response = null;

		try {
			pstmt = conn.prepareStatement("INSERT INTO extension_request VALUES(?, ?, ?);");
			pstmt.setString(1, extensionRequest.getActiveExam().getExam().getExamID());
			pstmt.setString(2, extensionRequest.getAdditionalTime());
			pstmt.setString(3, extensionRequest.getReason());
			if (pstmt.executeUpdate() == 1) {
				response = new ResponseFromServer("EXTENSION REQUEST CREATED");
			}
			else {
				response = new ResponseFromServer("EXTENSION REQUEST DIDNT CREATED");
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return response;
	}

	public ArrayList<TestRow> GetTeacherExams(Object obj) {

		Teacher teacher;

		ArrayList<TestRow> examsOfTeacher = new ArrayList<TestRow>();

		teacher = (Teacher) obj;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM exam WHERE author=?");

			pstmt.setString(1, "" + teacher.getId());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TestRow newRow = new TestRow();
				newRow.setExamID(rs.getString(1));
				newRow.setProfession(rs.getString(2));
				newRow.setTimeAllotedForTest(rs.getString(4));
				examsOfTeacher.add(newRow);

			}
			rs.close();

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			//teacher.setStatus("ERROR"); //message from Hadar: need to use ResponseFromServer class not?
			//i put this as a note because it marks an error now. 
		}
		return examsOfTeacher;// return null if no exsiting tests

	}

	public Boolean DeleteQuestion(String QuestionID) {
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("DELETE FROM question WHERE questionID=?");
			pstmt.setString(1, QuestionID);
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			return false;
		}
		return true;

	}

	public Boolean ExamQuestion(String ExamID) {
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("DELETE FROM exam WHERE examID=?");
			pstmt.setString(1, ExamID);
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			return false;
		}
		return true;

	}

	public ArrayList<QuestionRow> GetTeacherQuestions(Object obj) {

		Teacher teacher;
		int id;

		ArrayList<QuestionRow> examsOfTeacher = new ArrayList<QuestionRow>();

		id = (Integer) obj;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM question WHERE teacher=?");

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				QuestionRow newQuestion = new QuestionRow();
				newQuestion.setQuestionID(rs.getString(2));
				newQuestion.setProfession(rs.getString(3));
				newQuestion.setQuestion(rs.getString(4));
				examsOfTeacher.add(newQuestion);
			}
			rs.close();

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			//teacher.setStatus("ERROR");
			//message from Hadar: need to use ResponseFromServer class not?
			//i put this as a note because it marks an error now. 
		}
		return examsOfTeacher;// return null if no exsiting tests

	}

	/**
	 * @param activeExam
	 * @param additionalTime
	 * @return true if the additional Time for activeExam has been updated at
	 * 		   table active_exam in DB.
	 *         else, return false
	 */
	public boolean setTimeForActiveTest(ActiveExam activeExam, String additionalTime) {
		PreparedStatement pstmt;
		int check = 0;

		try {
			pstmt = conn.prepareStatement(
					"UPDATE active_exam SET timeAllotedForTest=? WHERE exam=" + activeExam.getExam().getExamID() + ";");
			pstmt.setString(3, additionalTime);
			check = pstmt.executeUpdate();
			if (check == 1) {
				System.out.println("Time for active exam Updated!");
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param activeExam
	 * @return true if deleting request for activeExam from table active_exam in DB
	 *         succeeded, else return false
	 */
	public Boolean DeleteExtenxtionRequest(ActiveExam activeExam) {
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("DELETE FROM extension_request WHERE exam=?");
			pstmt.setString(1, activeExam.getExam().getExamID());
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @param activeExam object which include 2 parameters of date and examcode for Query.
	 */
	public ResponseFromServer verifyActiveExam_byDate_and_Code(ActiveExam activeExam) {
		Exam exam = new Exam(null);
		ResponseFromServer response = null;
		/***EnterToExam***/
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT exam, timeAllotedForTest, examType FROM active_exam WHERE examCode=? and startTime>=? and startTime<?;");
			pstmt.setString(1, activeExam.getExamCode());
			pstmt.setTime(2, activeExam.getTime());
			pstmt.setTime(3, activeExam.getEndTimeToTakeExam());
			//Time Range for start the exam:
			System.out.println(activeExam.getTime() + " - " + activeExam.getEndTimeToTakeExam());
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				exam.setExamID(rs.getString(1));
				activeExam.setExam(exam);
				activeExam.setTimeOfExam(rs.getString(2));
				activeExam.setActiveExamType(rs.getString(3));
				rs.close();
			}
			
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		
		if (activeExam.getExam()== null) {
			response = new ResponseFromServer("ACTIVE EXAM_NOT_EXIST");
			
		} else {
			response = new ResponseFromServer("ACTIVE EXAM EXIST");	
			response.setResponseData(activeExam);
		}
		return response;
		
	}
	
	
	/**
	 * @param EditExam
	 * @return return true if succeed to edit exist exam, in any other case false.
	 */
	public boolean editExam(Exam editedExam) {
		Exam existExam = editedExam; //remove, it is the same.
		
		
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM exam ;"); //TODO: add WHERE with exam id "WHERE id=?;"
			pstmt.setString(1, existExam.getExamID());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existExam.setCommentForTeacher(editedExam.getCommentForTeacher());
				existExam.setCommentForStudents(editedExam.getCommentForStudents());
				existExam.setTimeOfExam(editedExam.getTimeOfExam());
				existExam.setQuestions(editedExam.getQuestions());
				rs.close();
			}
			
			//TODO: remove boolean return Exam object.
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public Profession getProfessionByID(String id) {
		Profession p = new Profession(id);

		
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT professionName FROM profession WHERE professionID=?;"); //TODO: add WHERE with exam id "WHERE id=?;"
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				p.setProfessionName(rs.getString(1));
				rs.close();
			}
			
			//TODO: remove boolean return Exam object.
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return p;
	}

	public ArrayList<ExtensionRequest> getExtensionsRequests() {
		ArrayList<ExtensionRequest> extensionRequestsList = new ArrayList<ExtensionRequest>();
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM extension_request ;");
			ResultSet rs = pstmt.executeQuery(); 
			while (rs.next()) {
				Exam exam = new Exam(null);
				ActiveExam activeExam = new ActiveExam(exam);
				ExtensionRequest extensionRequest = new ExtensionRequest(activeExam);
				exam.setExamID(rs.getString(1));
				activeExam.setExam(exam);
				extensionRequest.setActiveExam(activeExam);
				extensionRequest.setAdditionalTime(rs.getString(2));
				extensionRequest.setReason(rs.getString(3));
				extensionRequestsList.add(extensionRequest);
			}		
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}	
	return extensionRequestsList;	
	}















	
	
	

//public static void main(String[] args) {
//
//	DBController db = new DBController();
//db.connectDB(new ServerFrameController());
//
//Teacher t= new Teacher(222222222, "PASS123", "avi", "Cohen", "asdsadsadsa", UserType.Teacher,new ArrayList<Profession>());
//
//ArrayList<TestRow> tst=db.GetTeacherExams(t);
//
//	System.out.println(tst);
//
//
//
//}
}
