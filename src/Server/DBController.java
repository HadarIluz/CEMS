package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Profession;
import entity.Teacher;
import entity.User;
import entity.UserType;
import gui_server.ServerFrameController;
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
				 System.out.println("Details Of Exam Updated!");
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

		if (obj instanceof User) { // Still needed here? Because we changed that we would certainly get User
									// ObjecTtype
			User existUser;
			existUser = (User) obj;
			try {
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement("SELECT * FROM users WHERE id=?");
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

		} else
			return null;

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
	
	
	
	public static void main(String[] args) {
		
		DBController db = new DBController();
	db.connectDB(new ServerFrameController());
	
	Teacher t= new Teacher(222222222, "PASS123", "avi", "Cohen", "asdsadsadsa", UserType.Teacher,new ArrayList<Profession>());
	
	ArrayList<TestRow> tst=db.GetTeacherExams(t);
	
		System.out.println(tst);
		
		
		
	}

}
