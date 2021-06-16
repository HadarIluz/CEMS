package loginUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Server.DBController;
import entity.Course;
import entity.Profession;
import entity.Student;
import entity.Teacher;
import entity.User;
import entity.UserType;
import gui_server.ServerFrameController;
import logic.ResponseFromServer;

public class Server_LoginTest {

	// prepare objects
	private ServerFrameController serverFrameController;
	private DBController dbController;
	User user;
	Student student;
	Teacher teacher;
	
	@Before
	public void setUp() throws Exception {
		serverFrameController = null;
		dbController = new DBController();
		dbController.connectDB(serverFrameController);
	}

	/* ----------------------------- verifyLoginUser ----------------------------- */
	
	/* testing verifyLoginUser method. return ResponseFromServer saying when user is not found
	 * expected: exception - "USER NOT FOUND IN CEMS SYSTEM"
	 * input: user with wrong id
	 */
	@Test
	public void testSuccess_UserNotExist() {
		user = new User(1111111112, "pass");
		String expected = "USER NOT FOUND IN CEMS SYSTEM";
		
		String actual = dbController.verifyLoginUser(user).getResponseType();
		
		assertEquals(expected, actual);
		
	}
	
	/* testing verifyLoginUser method. return ResponseFromServer saying when user is found
	 * expected: exception - "USER FOUND IN CEMS SYSTEM"
	 * input: user with right id
	 */
	@Test
	public void testSuccess_UserFound() {
		user = new User(111111111, "pass");
		String expected = "USER FOUND IN CEMS SYSTEM";
		
		String actual = dbController.verifyLoginUser(user).getResponseType();
		
		assertEquals(expected, actual);
		
	}
	
	/* ----------------------------- getStudentData_Logged ----------------------------- */
	
	/* testing getStudentData_Logged method. return student with avg
	 * expected: avg = 66.7
	 * input: student
	 */
	@Test
	public void testSuccess_StudentAvgFound() {
		student = new Student(111111111, UserType.Student);
		float expected = (float) 66.7;
		
		float actual = dbController.getStudentData_Logged(student).getStudentAvg();
		
		assertEquals(expected, actual, 0.1);
		
	}
	
	/* ----------------------------- getStudentCourses_Logged ----------------------------- */
	
	/* testing getStudentCourses_Logged method. return student with courses
	 * expected: size of array list = 1
	 * input: student
	 */
	@Test
	public void testSuccess_StudentCoursesFound() {
		student = new Student(111111111, UserType.Student);
		int expected = 1;
		
		ArrayList<Course> actual = dbController.getStudentCourses_Logged(student).getCourses();
		assertEquals(expected, actual.size());
				
	}
	
	/* ----------------------------- getTeacherProfessionIDs ----------------------------- */
	
	/* testing getTeacherProfessionIDs method. return array list with profession id
	 * expected: professions = [] 
	 * input: teacher
	 */
	@Test
	public void testSuccess_TeacherProfessionsFound() {
		teacher = new Teacher(222222222, UserType.Teacher);
		ArrayList<String> expected = new ArrayList<>();
		expected.add("01");
		expected.add("03");

		ArrayList<String> actual = dbController.getTeacherProfessionIDs(teacher);
		
		assertArrayEquals(expected.toArray(), actual.toArray());
		
	}
	
	/* ----------------------------- getProfessionByID ----------------------------- */
	
	/* testing getProfessionByID method. return profession by profession ID
	 * expected: profession with name: "Mathematics"
	 * input: String profession id
	 */
	@Test
	public void testSuccess_ProfessionNameFound() {
		Profession expected = new Profession("01", "Mathematics");


		Profession actual = dbController.getProfessionByID("01");
		
		assertEquals(expected.getProfessionName(), actual.getProfessionName());
	}

}
