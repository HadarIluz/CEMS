package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.User;
import entity.UserType;
import gui_prototype.ServerFrameController;
import logic.TestRow;
import logic.UpdateDataRequest;

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
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cems_prototype?serverTimezone=IST", "root",
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


	/*checks if the user that try to login exists in the DB.*/
	public User verifyLoginUser(Object obj) {
		User existUser = new User();

		try {
			PreparedStatement pstmt;
			//pstmt = conn.prepareStatement("SELECT * FROM users WHERE id=? fullName=? and password=?;");
			pstmt = conn.prepareStatement("SELECT * FROM users WHERE id=?"); //ASK: only id currect?
			pstmt.setString(1, (String)obj);  //ASK: why set?
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existUser.setId(Integer.parseInt((String)obj));
				existUser.setPassword(rs.getString(2));
				existUser.setUsername(rs.getString(3));
				existUser.setEmail(rs.getString(4));
				existUser.setUserType(UserType.valueOf(rs.getString(5)));
				rs.close();
			}
			if (existUser.getPassword() == null)  //ASK: i want to verify if (existUser.getId() == null) buy it is int and not string, what should i do?
				existUser.setStatus("DoesntExist");
		}
		catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			existUser.setStatus("ERROR");
		}
		return existUser;
		
		
	}



}
