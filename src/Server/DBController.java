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

import logic.TestRow;
import logic.TestTableRequest;
import logic.UpdateDataRequest;


public class DBController {
	Connection conn;

	public void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sys/?serverTimezone=IST","root","yadin95");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cems_prototype?serverTimezone=IST", "root", "yadin95");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

/*	public void parsingTheData(Object msg) {
		if (msg instanceof ArrayList<?>) {
			List<TestRow> DetailsArr = new ArrayList<TestRow>(Arrays.asList(((String) msg).split(" ")));
			if (DetailsArr.get(0).equals("Update"))
				updateTestTime(DetailsArr);
			// else {
			// getTestTable();
			// }
		}
	}*/
	
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
			System.out.println("Details Of Exam Not Updated!");
			e.printStackTrace();
		}
		return false;///////
	}
	
	public TestTableRequest getTestTable() {
		TestTableRequest testsTable = new TestTableRequest();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM test;");
			while (rs.next()) { // while we have lines in test table in DB
				TestRow newRow= new TestRow();
				newRow.setExamID(rs.getString(1));
				newRow.setProfession(rs.getString(2));
				newRow.setCourse(rs.getString(3));
				newRow.setTimeAllotedForTest(rs.getString(4));
				newRow.setPointsPerQuestion(rs.getString(5));
				testsTable.addRow(newRow); // save the current exam in array list of array lists
			}
			rs.close();
		}

		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		}

		return testsTable;
	}
	
	
	public static void main(String[] args) {
		DBController db = new DBController();
		TestTableRequest tst = new TestTableRequest();
		db.connectDB();
		tst = db.getTestTable();
		System.out.println(tst);
	}


}
