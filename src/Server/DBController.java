package Server;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import entity.ActiveExam;
import entity.Exam;
import entity.ExtensionRequest;
import entity.Question;
import entity.QuestionRow;
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

//github.com/yuval96/CEMS.git
	/*checks if the user that try to login exists in the DB.
	 * @param obj of user which include student id to verify if exists.
	 */
	/*
	 * public void verifyLoginUser(Object obj) { User existUser = (User) obj;
	 * ResponseFromServer respond = null;
	 * 
	 * }
	 */

	public void verifyLoginUser(Object obj) {

		User existUser = (User) obj;
		ResponseFromServer respond = null;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			pstmt.setInt(1, existUser.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existUser.setId(Integer.parseInt((String) rs.getString(1)));
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
		//in case not found any user match..
		if (existUser.getPassword() == null) {
			respond = new ResponseFromServer("USER NOT FOUND");
		}
		else{
			respond = new ResponseFromServer("USER FOUND");	
		}
		// ResponseFromServer class ready to client with StatusMsg and  
		//'Object responseData', in case user found existUser include all data, other null.
		respond.setResponseData(existUser);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stdScore;

	}

// updated ScoreApproval
	public boolean setLoginUserLogged(int userID, int num) {
		PreparedStatement pstmt;
		int check = 0;
		int flag; // The flag checks what is the current status of this user and updates to the
					// reverse mode

		if (num == 1)
			flag = 0;
		else
			flag = 1;

		try {
			// UPDATE tblName
			// SET column=value
			// WHERE condition(s)
			pstmt = conn.prepareStatement("UPDATE user SET isLogged=? WHERE id=" + userID + ";");
			pstmt.setInt(5, flag);
			check = pstmt.executeUpdate();
			if (check == 1) {
				System.out.println("Details Of user Logged Updated!");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * if the activeExam exist in the DB and update existActiveExam.status
	 * accordingly
	 * 
	 * @param activeExam
	 * @return return existActiveExam.
	 */
	public ActiveExam verifyActiveExam_byExamID(ActiveExam activeExam) {
		ActiveExam existActiveExam = activeExam;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM active_exam WHERE exam=? ;");
			pstmt.setString(1, existActiveExam.getExam().getExamID());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existActiveExam.setExam(activeExam.getExam());
				existActiveExam.setDate(activeExam.getDate());
				existActiveExam.setExamCode(rs.getString(3));
				rs.close();
			}
			if (existActiveExam.getExam() == null) {
				existActiveExam.setStatus("ACTIVE EXAM FOUND"); // status
			} else
				existActiveExam.setStatus("ACTIVE EXAM NOT FOUND"); // status
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			existActiveExam.setStatus("ERROR");
		}
		return existActiveExam;
	}

	/**
	 * 
	 * @param extensionRequest
	 * @return true if creating a new extension request in DB succeeded, else return
	 *         false
	 */
	public boolean createNewExtensionRequest(ExtensionRequest extensionRequest) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("INSERT INTO extension_request VALUES(?, ?, ?);");
			pstmt.setString(1, extensionRequest.getExam().getExam().getExamID());
			pstmt.setString(2, extensionRequest.getAdditionalTime());
			pstmt.setString(3, extensionRequest.getsetReason());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return false;

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

		ArrayList<QuestionRow> examsOfTeacher = new ArrayList<QuestionRow>();

		teacher = (Teacher) obj;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM question WHERE teacher=?");

			pstmt.setString(1, "" + teacher.getId());

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
	 * @param activeExam object which include 2 parameters of date and examcode for Query.
	 */
	public void verifyActiveExam_byDate_and_Code(ActiveExam activeExam) {
		ActiveExam existActiveExam = activeExam;
		Exam exam = null;
		ResponseFromServer respond = null;
		/***EnterToExam***/
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM active_exam WHERE date=? examcode=?;");
			pstmt.setString(2, existActiveExam.getActiveExamStartTime());
			pstmt.setString(3, existActiveExam.getExamCode());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				exam.setExamID(rs.getString(1));
				existActiveExam.setExam(exam);

				existActiveExam.setDate(activeExam.getDate());
				existActiveExam.setExamCode(activeExam.getExamCode());
				existActiveExam.setActiveExamType(rs.getString(4));
				rs.close();
			}
			if (existActiveExam.getExam().getExamID() == null) {
				respond = new ResponseFromServer("ACTIVE EXAM EXIST");		// StatusMsg.statusMsg
				respond.setResponseData(existActiveExam);
			} else {
				respond = new ResponseFromServer("ACTIVE EXAM_NOT_EXIST");	// StatusMsg.statusMsg
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
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
