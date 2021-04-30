package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gui.ServerFrameController;
import logic.StatusMsg;
import logic.TestRow;
import logic.TestTableRequest;
import logic.UpdateDataRequest;

public class DBController {
	public Connection conn;
public ServerFrameController serverFrame;
	public void connectDB(ServerFrameController serverFrame) {
		try {
			this.serverFrame= serverFrame;
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			serverFrame.printToTextArea("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			serverFrame.printToTextArea("Driver definition failed");
		}

		try {// Connection conn =DriverManager.getConnection("jdbc:mysql://localhost/sys/?serverTimezone=IST","root","yadin95");
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
			//	System.out.println("Details Of Exam Updated!");
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
			if(newRow.getExamID() == null)
				newRow.setExamID("DoesntExist");
		}

		catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			newRow.setExamID("ERROR");
		}
		return newRow;
	}

//	public static void main(String[] args) {
//		DBController db = new DBController();
//		TestRow tst = new TestRow();
//		db.connectDB();
//		tst = db.getTestRow("010203");
//		System.out.println(tst);
//	}

}
