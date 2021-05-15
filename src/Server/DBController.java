package Server;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Question;
import entity.User;
import entity.UserType;
import gui_server.ServerFrameController;
import logic.TestRow;
import logic.UpdateDataRequest;

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

	public boolean updateTestTime(UpdateDataRequest UpdateExam) {
		PreparedStatement pstmt;
		int check = 0;
		try {
			pstmt = conn.prepareStatement("UPDATE test SET timeAllotedForTest=? WHERE examID=? ;");
			pstmt.setString(1, UpdateExam.getTimeAllotedForTest());
			pstmt.setString(2, UpdateExam.getExamID());
			check = pstmt.executeUpdate();
			if (check == 1) {
				// System.out.println("Details Of Exam Updated!");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public TestRow getTestRow(String examID) {
		TestRow newRow = new TestRow();

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM test WHERE examID=?;");
			pstmt.setString(1, examID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				newRow.setExamID(rs.getString(1));
				newRow.setProfession(rs.getString(2));
				newRow.setCourse(rs.getString(3));
				newRow.setTimeAllotedForTest(rs.getString(4));
				newRow.setPointsPerQuestion(rs.getString(5));
				rs.close();
			}
			if (newRow.getExamID() == null)
				newRow.setExamID("DoesntExist");
		}

		catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			newRow.setExamID("ERROR");
		}
		return newRow;
	}

	/* checks if the user that try to login exists in the DB. */
	public User verifyLoginUser(Object obj) {

		//if (obj instanceof User) { // Still needed here? Because we changed that we would certainly get User
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

		//} else
			//return null;

	}

	public boolean setLoginUserLogged(int userID, int num) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		int check = 0;
		int flag; //The flag checks what is the current status of this user and updates to the reverse mode
		
		if (num==1)
			flag = 0;
		else
			flag = 1;
		
		try {
			//UPDATE tblName 
			//SET column=value 
			//WHERE condition(s)
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
	 * @param question
	 * inserts new question to DB
	 */
	public void createNewQuestion(Question question) {
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


			int status = pstmt.executeUpdate();
			// to do something with status
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
