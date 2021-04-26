package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chatclient?serverTimezone=IST", "root", "yadin95");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public boolean parsingTheData(Object msg) {
		if (msg instanceof ArrayList<?>) {
			return addUserToDB((ArrayList<String>) msg);
		}
		return false;
	}

	public boolean addUserToDB(ArrayList<String> UserInfo) {
		PreparedStatement pstmt;
		int check=0;
		try {
			pstmt=conn.prepareStatement("INSERT INTO users (username,id,Department,Tel) VALUES(?,?,?,?);");
			pstmt.setString(1, UserInfo.get(0));
			pstmt.setString(2, UserInfo.get(1));
			pstmt.setString(3, UserInfo.get(2));
			pstmt.setString(4, UserInfo.get(3));
			check=pstmt.executeUpdate();
			if(check==1) {
				System.out.println("User Inserted!");
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println("User Not Inserted!");
			e.printStackTrace();
		}
		return false;
	}

}
