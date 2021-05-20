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

/**
 * @author yuval
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

	/* checks if the user that try to login exists in the DB. */
	public User verifyLoginUser(Object obj) {

		// if (obj instanceof User) { // Still needed here? Because we changed that we
		// would certainly get User
		// ObjecTtype
		User existUser;
		existUser = (User) obj;
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			pstmt.setString(1, (String) obj);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existUser.setId(Integer.parseInt((String) obj));
				existUser.setPassword(rs.getString(2));
				existUser.setFirstName(rs.getString(3));
				existUser.setLastName(rs.getString(4));
				existUser.setEmail(rs.getString(5));
				existUser.setUserType(UserType.valueOf(rs.getString(6)));
				rs.close();
			}
			if (existUser.getPassword() == null) // ASK: i want to verify if (existUser.getId() == null) buy it is
													// int and not string, i used password indent
				existUser.setStatus("DoesntExist");
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			existUser.setStatus("ERROR");
		}
		return existUser;

	}

	public boolean setLoginUserLogged(int userID, int num) {
		// TODO Auto-generated method stub
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
	/**
	 * @param exam
	 * @return
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
	public ActiveExam verifyActiveExam(ActiveExam activeExam) {
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

		ArrayList<TestRow> examsOfTeacher =new ArrayList<TestRow>();


		teacher = (Teacher) obj;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM exam WHERE author=?");

			pstmt.setString(1,""+teacher.getId());

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
			teacher.setStatus("ERROR");
		}
		return examsOfTeacher;//return null if no exsiting tests

}
	
	
	public ArrayList<QuestionRow> GetTeacherQuestions(Object obj) {

		Teacher teacher;

		ArrayList<QuestionRow> examsOfTeacher =new ArrayList<QuestionRow>();

		teacher = (Teacher) obj;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM question WHERE teacher=?");

			pstmt.setString(1,""+teacher.getId());

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
			teacher.setStatus("ERROR");
		}
		return examsOfTeacher;//return null if no exsiting tests

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
